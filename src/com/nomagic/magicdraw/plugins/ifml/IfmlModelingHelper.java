package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlFrame;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlTreeNode;
import com.nomagic.magicdraw.ui.ImageMap16;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.ClassView;
import com.nomagic.magicdraw.uml.symbols.shapes.ShapeElement;
import com.nomagic.uml2.ext.jmi.helpers.ElementImageHelper;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.lang.Class;
import java.util.*;
import java.util.List;

public class IfmlModelingHelper
{
    static final Map<String, Integer> TAB_POSITIONS = new HashMap<String, Integer>()
    {
        {
            put(IfmlProperties.BOTTOM, SwingConstants.BOTTOM);
            put(IfmlProperties.LEFT, SwingConstants.LEFT);
            put(IfmlProperties.RIGHT, SwingConstants.RIGHT);
            put(IfmlProperties.TOP, SwingConstants.TOP);
        }
    };
    static final Map<String, Border> BORDERS = new HashMap<String, Border>()
    {
        {
            put(IfmlProperties.RAISED_ETCHED, BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            put(IfmlProperties.LINE, BorderFactory.createLineBorder(Color.GRAY));
            put(IfmlProperties.LOWERED_ETCHED, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            put(IfmlProperties.RAISED_BEVEL, BorderFactory.createRaisedBevelBorder());
            put(IfmlProperties.LOWERED_BEVEL, BorderFactory.createLoweredBevelBorder());
        }
    };
    static final Map<String, Integer> VERTICAL_SCROLLBAR_POLICY = new HashMap<String, Integer>()
    {
        {
            put(IfmlProperties.ALWAYS, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            put(IfmlProperties.AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            put(IfmlProperties.NEVER, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        }
    };
    static final Map<String, Integer> HORIZONTAL_SCROLLBAR_POLICY = new HashMap<String, Integer>()
    {
        {
            put(IfmlProperties.ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            put(IfmlProperties.AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            put(IfmlProperties.NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        }
    };
    private static final Map<String, Icon> ICONS = new HashMap<String, Icon>()
    {
        {
            put(IfmlProperties.CLOSE_ICON, ImageMap16.CLOSE_PROJECT);
            put(IfmlProperties.COPY_ICON, ImageMap16.COPY);
            put(IfmlProperties.CUT_ICON, ImageMap16.CUT);
            put(IfmlProperties.DELETE_ICON, ImageMap16.DELETE_MENU);
            put(IfmlProperties.NEW_ICON, ImageMap16.NEW);
            put(IfmlProperties.OPEN_ICON, ImageMap16.OPEN);
            put(IfmlProperties.PASTE_ICON, ImageMap16.PASTE);
            put(IfmlProperties.PRINT_ICON, ImageMap16.PRINT_MENU);
            put(IfmlProperties.SAVE_ICON, ImageMap16.SAVE);
            put(IfmlProperties.SEARCH_ICON, ImageMap16.FIND);
            put(IfmlProperties.UNDO_ICON, ImageMap16.UNDO);
            put(IfmlProperties.REDO_ICON, ImageMap16.REDO);
            put(IfmlProperties.ZOOM_IN_ICON, ImageMap16.ZOOM_IN);
            put(IfmlProperties.ZOOM_OUT_ICON, ImageMap16.ZOOM_OUT);
        }
    };

    /**
     * Gets model element from the base element.
     * If base element is a symbol, retrieves its model element.
     *
     * @param element element for which model element should be retrieved.
     * @return retrieved element or null if no element was found.
     */
    public static Element getElement (BaseElement element)
    {
        if (element instanceof Element)
        {
            return (Element) element;
        } else if (element instanceof PresentationElement)
        {
            return ((PresentationElement) element).getElement();
        }

        return null;
    }

    public static boolean isAvailableIcon (String iconType)
    {
        return ICONS.containsKey(iconType);
    }

    public static void setComponentEnabled (Element element, JComponent component)
    {
        boolean enable = !isInactive(element);
        if (isFrame(element))
        {
            ((IfmlFrame) component).setShowing(enable);
        } else if (component instanceof JScrollPane)
        {
            IfmlModelingHelper.getScrollPaneViewComponent(component).setEnabled(enable);
        } else
        {
            component.setEnabled(enable);
        }
    }

    public static void setButtonSelected (Element element, AbstractButton button)
    {
        button.setSelected(isSelected(element));
    }

    public static void setComponentIcon (Element element, JComponent component)
    {
        Icon icon = getIconValue(element);

        if (isButton(element))
        {
            ((JButton) component).setIcon(icon);
        } else if (isFrame(element))
        {
            ((IfmlFrame) component).setFrameIcon(icon);
        } else if (isLabel(element) || isHyperlink(element))
        {
            ((JLabel) component).setIcon(icon);
        }
    }

    public static Icon getIconValue (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getIcon(), IfmlWidgetsProfile.ICON_ICON_PROPERTY);
        return value instanceof EnumerationLiteral ? IfmlModelingHelper.getIcon(((EnumerationLiteral) value).getName
                (), element) : null;
    }

    public static Component getScrollPaneViewComponent (JComponent component)
    {
        if (component instanceof JScrollPane)
        {
            return ((JScrollPane) component).getViewport().getView();
        }
        return null;
    }

    public static Border getBorder (ShapeElement pElement)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(pElement.getElement(),
                IfmlWidgetsProfile.getInstance(Project.getProject(pElement)).getGroupBox(),
                IfmlWidgetsProfile.GROUPBOX_BORDER_TYPE_PROPERTY);
        String borderName = value instanceof EnumerationLiteral ? ((EnumerationLiteral) value).getName() : "";
        Border border;

        if (borderName.equals(IfmlProperties.LINE))
        {
            border = BorderFactory.createLineBorder(pElement.getLineColor());
        } else
        {
            border = BORDERS.get(borderName);
        }
        return border;
    }

    public static int getOrientation (Boolean vertical)
    {
        return vertical == null || vertical ? Adjustable.VERTICAL : Adjustable.HORIZONTAL;
    }

    public static Icon getIcon (String iconName, Element element)
    {
        Icon icon;

        if (IfmlProperties.CUSTOM_ICON.equals(iconName))
        {
            icon = ElementImageHelper.getIconFromCustomImageProperty(element);
        } else
        {
            icon = ICONS.get(iconName);
        }

        return icon;
    }

    public static void setTableValues (Element tableElement, List<Element> columns, DefaultTableModel tableModel,
                                       JTable table)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(Project.getProject(tableElement));
        int row = 0;

        for (Element tableElementChild : tableElement.getOwnedElement())
        {
            if (isRow(tableElementChild))
            {
                for (Element cell : tableElementChild.getOwnedElement())
                {
                    if (isCell(cell))
                    {
                        Object relatedColumn = StereotypesHelper.getStereotypePropertyFirst(cell, profile.getCell(),
                                IfmlWidgetsProfile.CELL_RELATEDCOLUMN_PROPERTY);

                        if (relatedColumn != null && columns.contains(relatedColumn))
                        {
                            int columnIndex = columns.indexOf(relatedColumn);
                            tableModel.setValueAt(IfmlModelingHelper.getText(cell), row, columnIndex);
                            if (IfmlModelingHelper.isSelected(cell))
                            {
                                table.changeSelection(row, columnIndex, false, false);
                            }
                        }
                    }
                }

                row++;
            }
        }
    }

    public static List<IfmlTreeNode> getIfmlTreeNodes (IfmlTreeNode parentNode, List<IfmlTreeNode> nodes,
                                                       DefaultTreeModel treeModel)
    {
        nodes.add(parentNode);
        for (int i = 0; i < treeModel.getChildCount(parentNode); i++)
        {
            nodes.add((IfmlTreeNode) treeModel.getChild(parentNode, i));
            if (treeModel.getChildCount(treeModel.getChild(parentNode, i)) != 0)
            {
                getIfmlTreeNodes((IfmlTreeNode) treeModel.getChild(parentNode, i), nodes, treeModel);
            }
        }
        return nodes;
    }

    public static List<Element> getNodeElements (Element parentElement, List<Element> nodes, boolean includeLeafs)
    {
        for (Element child : parentElement.getOwnedElement())
        {
            if (IfmlModelingHelper.isNode(child) || includeLeafs && IfmlModelingHelper.isLeaf(child))
            {
                nodes.add(child);

                if (child.hasOwnedElement())
                {
                    getNodeElements(child, nodes, includeLeafs);
                }
            }
        }

        return nodes;
    }

    public static boolean isButton (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getButton());
    }

    public static boolean isCell (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element) && StereotypesHelper
                .hasStereotype(element, IfmlWidgetsProfile.getInstance(Project.getProject(element)).getCell());
    }

    public static boolean isCheckBox (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element) && StereotypesHelper
                .hasStereotype(element, IfmlWidgetsProfile.getInstance(Project.getProject(element)).getCheckBox());
    }

    public static boolean isColumn (Element element)
    {
        return element instanceof Classifier && StereotypesHelper
                .hasStereotype(element) && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getColumn());
    }

    public static boolean isComboBox (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element) && StereotypesHelper
                .hasStereotype(element, IfmlWidgetsProfile.getInstance(Project.getProject(element)).getComboBox());
    }

    public static boolean isFrame (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getFrame());
    }

    public static boolean isGroupBox (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getGroupBox());
    }

    public static boolean isHyperlink (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getHyperlink());
    }

    public static boolean isLabel (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getLabel());
    }

    public static boolean isLeaf (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element) && StereotypesHelper
                .hasStereotype(element, IfmlWidgetsProfile.getInstance(Project.getProject(element)).getLeaf());
    }

    public static boolean isList (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getList());
    }

    public static boolean isMenuBar (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getMenuBar());
    }

    public static boolean isNode (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element) && StereotypesHelper
                .hasStereotype(element, IfmlWidgetsProfile.getInstance(Project.getProject(element)).getNode());
    }

    public static boolean isPanel (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getPanel());
    }

    public static boolean isPasswordField (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getPasswordField());
    }

    public static boolean isProgressBar (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getProgressBar());
    }

    public static boolean isRadioButton (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getRadioButton());
    }

    public static boolean isRow (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getRow());
    }

    public static boolean isScrollBar (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getScrollBar());
    }

    public static boolean isScrollPane (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getScrollPane());
    }

    public static boolean isSeparator (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getSeparator());
    }

    public static boolean isSlider (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getSlider());
    }

    public static boolean isSpinner (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getSpinner());
    }

    public static boolean isTabbedPane (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getTabbedPane());
    }

    public static boolean isTable (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getTable());
    }

    public static boolean isTextArea (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getTextArea());
    }

    public static boolean isTextField (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getTextField());
    }

    public static boolean isToolBar (Element element)
    {
        return element instanceof Classifier && StereotypesHelper.hasStereotype(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getToolBar());
    }

    public static boolean isTree (Element element)
    {
        return element instanceof com.nomagic.uml2.ext.magicdraw.components.mdbasiccomponents.Component &&
                StereotypesHelper.hasStereotype(element, IfmlWidgetsProfile.getInstance(Project.getProject(element)).getTree
                        ());
    }

    public static String getTabbedPaneActiveTab (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getTabbedPane(), IfmlWidgetsProfile.TABBEDPANE_ACTIVE_TAB_PROPERTY);
        return value != null ? value.toString() : "";
    }

    public static String getTabbedPanePlacement (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getTabbedPane(), IfmlWidgetsProfile.TABBEDPANE_TAB_POSITION_PROPERTY);
        return value instanceof EnumerationLiteral ? ((EnumerationLiteral) value).getName() : "";
    }

    public static String getListSelectedValue (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getList(), IfmlWidgetsProfile.LIST_SELECTED_VALUE_PROPERTY);
        return value != null ? value.toString() : "";
    }

    public static List<String> getMenuBarMenus (Element element)
    {
        return getStrings(StereotypesHelper.getStereotypePropertyValue(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getMenuBar(), IfmlWidgetsProfile.MENUBAR_MENUS_PROPERTY));
    }

    public static List<String> getTabbedPaneTabs (Element element)
    {
        return getStrings(StereotypesHelper.getStereotypePropertyValue(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getTabbedPane(),
                IfmlWidgetsProfile.TABBEDPANE_TABS_PROPERTY));
    }

    public static List<String> getSliderValues (Element element)
    {
        return getStrings(StereotypesHelper.getStereotypePropertyValue(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getSlider(), IfmlWidgetsProfile.SLIDER_VALUES_PROPERTY));
    }

    public static List<String> getListValues (Element element)
    {
        return getStrings(StereotypesHelper.getStereotypePropertyValue(element,
                IfmlWidgetsProfile.getInstance(Project.getProject(element)).getList(), IfmlWidgetsProfile.LIST_VALUES_PROPERTY));
    }

    private static List<String> getStrings (Object value)
    {
        ArrayList<String> res = new ArrayList<String>();
        if (value instanceof List)
        {
            for (Object o : (List) value)
            {
                if (o instanceof String)
                {
                    res.add((String) o);
                }
            }
        }
        return res;
    }

    public static boolean isSliderInverted (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getSlider(), IfmlWidgetsProfile.SLIDER_INVERT_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static int getSliderMajorTickSpacing (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getSlider(), IfmlWidgetsProfile.SLIDER_SPACING_PROPERTY);
        return value instanceof Integer ? (Integer) value : 0;
    }

    public static int getSliderValue (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getSlider(), IfmlWidgetsProfile.SLIDER_KNOB_POSITION_PROPERTY);
        return value instanceof Integer ? (Integer) value : 0;
    }

    public static int getProgressBarValue (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getProgressBar(), IfmlWidgetsProfile.PROGRESSBAR_VALUE_PROPERTY);
        return value instanceof Integer ? (Integer) value : 0;
    }

    public static int getMinValue (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getMinimumValue(), IfmlWidgetsProfile.MINIMUM_VALUE_MINIMUM_VALUE_PROPERTY);
        return value instanceof Integer ? (Integer) value : 0;
    }

    public static int getMaxValue (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getMaximumValue(), IfmlWidgetsProfile.MAXIMUM_VALUE_MAXIMUM_VALUE_PROPERTY);
        return value instanceof Integer ? (Integer) value : 0;
    }

    public static boolean isVertical (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getVertical(), IfmlWidgetsProfile.VERTICAL_VERTICAL_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isPasswordFieldHidden (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getPasswordField(), IfmlWidgetsProfile.PASSWORDFIELD_HIDDEN_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isFrameMinimize (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getFrame(), IfmlWidgetsProfile.FRAME_MINIMIZE_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isFrameMaximize (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getFrame(), IfmlWidgetsProfile.FRAME_MAXIMIZE_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isGroupBoxNamed (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getGroupBox(), IfmlWidgetsProfile.GROUPBOX_TITLED_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static String getHorizontalScrollbarPolicy (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getHorizontalScrollBar(),
                IfmlWidgetsProfile.HORIZONTAL_SCROLL_BAR_HORIZONTAL_SCROLL_BAR_PROPERTY);
        return value instanceof EnumerationLiteral ? ((EnumerationLiteral) value).getName() : IfmlProperties.ALWAYS;
    }

    public static String getVerticalScrollbarPolicy (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getVerticalScrollBar(),
                IfmlWidgetsProfile.VERTICAL_SCROLL_BAR_VERTICAL_SCROLL_BAR_PROPERTY);
        return value instanceof EnumerationLiteral ? ((EnumerationLiteral) value).getName() : IfmlProperties.ALWAYS;
    }

    public static String getScrollPaneHorizontalScrollbarPolicy (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getScrollPane(), IfmlWidgetsProfile.SCROLLPANE_HORIZONTAL_SCROLL_BAR_PROPERTY);
        return value instanceof EnumerationLiteral ? ((EnumerationLiteral) value).getName() : IfmlProperties.ALWAYS;
    }

    public static String getTitle (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getTitle(), IfmlWidgetsProfile.TITLE_TITLE_PROPERTY);
        return value instanceof String ? (String) value : "";
    }

    public static String getText (Element element)
    {
        if (isTitled(element))
        {
            return getTitle(element);
        } else
        {
            Object value = StereotypesHelper.getStereotypePropertyFirst(element,
                    IfmlWidgetsProfile.getInstance(Project.getProject(element)).getText(), IfmlWidgetsProfile.TEXT_TEXT_PROPERTY);
            return value instanceof String ? (String) value : "";
        }
    }

    public static boolean isTitled (Element element)
    {
        return isFrame(element) || isGroupBox(element) || isRow(element) || isColumn(element);
    }

    public static void setText (Element element, String text)
    {
        if (isTitled(element))
        {
            setTitle(element, text);
        } else
        {
            StereotypesHelper.setStereotypePropertyValue(element, IfmlWidgetsProfile.getInstance(Project.getProject
                    (element)).getText(), IfmlWidgetsProfile.TEXT_TEXT_PROPERTY, text);
        }
    }

    public static void setTitle (Element element, String text)
    {
        StereotypesHelper.setStereotypePropertyValue(element, IfmlWidgetsProfile.getInstance(Project.getProject(element))
                .getTitle(), IfmlWidgetsProfile.TITLE_TITLE_PROPERTY, text);
    }

    public static String getScrollPanelVerticalScrollbarPolicy (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getScrollPane(), IfmlWidgetsProfile.SCROLLPANE_VERTICAL_SCROLL_BAR_PROPERTY);
        return value instanceof EnumerationLiteral ? ((EnumerationLiteral) value).getName() : IfmlProperties.ALWAYS;
    }

    public static boolean isTreeExpanded (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getTree(), IfmlWidgetsProfile.TREE_EXPAND_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isTreeNodeExpanded (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getNode(), IfmlWidgetsProfile.NODE_EXPAND_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isTableColumnHeaderVisible (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getTable(), IfmlWidgetsProfile.TABLE_COLUMN_HEADER_PROPERTY);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static int getComponentOrientation (JComponent component, Element element)
    {
        int orientation = 0;
        if (isScrollBar(element))
        {
            orientation = ((JScrollBar) component).getOrientation();
        } else if (isSeparator(element))
        {
            orientation = ((JSeparator) component).getOrientation();
        } else if (isProgressBar(element))
        {
            orientation = ((JProgressBar) component).getOrientation();
        } else if (isToolBar(element))
        {
            orientation = ((JToolBar) component).getOrientation();
        } else if (isSlider(element))
        {
            orientation = ((JSlider) component).getOrientation();
        }
        return orientation;
    }

    public static boolean isInactive (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getInactive(), IfmlWidgetsProfile.INACTIVE_INACTIVE_PROPERTY);

        return value instanceof Boolean ? (Boolean) value : false;
    }

    public static boolean isSelected (Element element)
    {
        Object value = StereotypesHelper.getStereotypePropertyFirst(element, IfmlWidgetsProfile.getInstance(Project
                .getProject(element)).getSelected(), IfmlWidgetsProfile.SELECTED_SELECTED_PROPERTY);

        return value instanceof Boolean ? (Boolean) value : false;
    }

    /**
     * @deprecated buttons are no longer configured through comment, this is done in profile.
     */
    @Deprecated
    public static String getPredefinedButtonType (Element element)
    {
        if (element.hasOwnedComment())
        {
            Collection<Comment> ownedComment = element.getOwnedComment();
            for (Comment comment : ownedComment)
            {
                String buttonType = comment.getBody();
                if (!"".equals(buttonType))
                {
                    return buttonType;
                }
            }
        }

        return null;
    }

    public static Element getTableComponent (Element element)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(element);
        return getComponent(element, profile.getTable());
    }

    public static Element getTreeComponent (Element element)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(element);
        return getComponent(element, profile.getTree());
    }

    private static Element getComponent (Element element, Stereotype stereotype)
    {
        while (element.getOwner() != null && !(element instanceof com.nomagic.uml2.ext.magicdraw.components
                .mdbasiccomponents.Component) && !StereotypesHelper.hasStereotype(element, stereotype))
        {
            element = element.getOwner();
        }

        return element;
    }

    /**
     * Check if given symbol is used for representing some UI component.
     *
     * @param pElement
     * @return
     */
    public static boolean isUISymbol (PresentationElement pElement)
    {
        if ( pElement instanceof ClassView )
        {
            final Element mdElement = pElement.getElement();

            return isUISymbol(mdElement);
        }

        return false;
    }

    /**
     * Check if given mdElement is used for representing some UI component.
     *
     * @param mdElement
     * @return
     */
    public static boolean isUISymbol (Element mdElement)
    {
        if (mdElement instanceof Classifier)
        {
            if (StereotypesHelper.hasStereotype( mdElement ))
            {
                return StereotypesHelper.hasStereotype(mdElement,
                        IfmlWidgetsProfile.getInstance(mdElement).getAllUIComponents());
            }

        }

        return false;
    }

    /**
     * Check if given element is used in UI diagram somehow - it can be UI symbol or inner
     *
     * @param element
     * @return
     */
    public static boolean isUIElement (Element element)
    {
        return element instanceof Classifier &&
                StereotypesHelper.hasStereotype(element) &&
                StereotypesHelper.hasStereotype(element, IfmlWidgetsProfile.getInstance(element).getAllStereotypes());
    }

    /**
     * Check if given symbol is used for representing some UI component.
     *
     * @param element
     * @return
     */
    public static boolean isUISymbolWithText (Element element)
    {
        return StereotypesHelper.hasStereotype(element) && (isUISymbol(element) && (StereotypesHelper
                .hasStereotypeOrDerived(element, IfmlWidgetsProfile.getInstance(element).getText()) || StereotypesHelper
                .hasStereotypeOrDerived(element, IfmlWidgetsProfile.getInstance(element).getTitle())) ||
                isCell(element) ||
                isRow(element) ||
                isColumn(element) ||
                isNode(element) ||
                isLeaf(element));
    }

    /**
     * Indicates if element has the UI Modeling "Text" property.
     *
     * @param element element to check.
     * @return true if elements has the UI Modeling "Text" property, false otherwise.
     */
    public static boolean hasTextProperty (Element element)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(element);
        return StereotypesHelper.hasStereotypeOrDerived(element, profile.getText());
    }

    /**
     * Indicates if element has the UI Modeling "Title" property.
     *
     * @param element element to check.
     * @return true if elements has the UI Modeling "Title" property, false otherwise.
     */
    public static boolean hasTitleProperty (Element element)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(element);
        return StereotypesHelper.hasStereotypeOrDerived(element, profile.getTitle());
    }

    /**
     * Gets all UI Modeling elements found in the project.
     *
     * @param project project from which all UI Modeling symbols should be retreived.
     * @return all UI Modeling symbols found in a project.
     */
    public static List<Element> getAllUIElements (Project project)
    {
        Model model = project.getModel();

        List<Class> classes = new ArrayList<Class>();

        classes.add(com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class.class);
        classes.add(Property.class);
        classes.add(com.nomagic.uml2.ext.magicdraw.components.mdbasiccomponents.Component.class);

        Collection<? extends Element> elements = ModelHelper.getElementsOfType(model, classes.toArray(new Class[]{}), false, true);

        List<Element> uiElments = new ArrayList<Element>();

        for (Element element : elements)
        {
            if (isUISymbol(element))
            {
                uiElments.add(element);
            }
        }

        return uiElments;
    }
}
