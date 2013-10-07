package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.actions.PresentationElementConfigurator;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mindaugas Genutis
 */
public class JComponentElementConfigurator implements PresentationElementConfigurator, JComponentHandler
{
	static final int DEFAULT_MAX_VALUE = 100;

	static final int DEFAULT_MIN_VALUE = 0;

	private static final List<String> DEFAULT_MENUS = Arrays.asList("File", "Edit", "View", "Tools", "Help");

	private static final List<String> DEFAULT_LIST_ITEMS = Arrays.asList("Item 1", "Item 2", "Item 3");

    private static final List<String> DEFAULT_TABS = Arrays.asList("Tab 1", "Tab 2", "Tab 3");

    private NamedElement mElement;

    @Override
	public void configureElement(Element element)
    {
        if(element instanceof NamedElement)
        {
            mElement = (NamedElement) element;
            new JComponentHandlerAcceptor().acceptHandler(this.mElement, this);
        }
    }

    @Override
	public Object handleButton()
    {
        return null;
    }

    @Override
	public Object handleCheckBox()
    {
        return null;
    }

    @Override
	public Object handleComboBox()
    {
        return null;
    }

    @Override
	public Object handleHyperlink()
    {
        Icon icon = IfmlModelingHelper.getIconValue(mElement);
        String text = IfmlModelingHelper.getText(mElement);
        if (text.equals("") && icon == null)
        {
            IfmlModelingHelper.setText(mElement, "Hyperlink");
        }

        return null;
    }

    @Override
	public Object handleLabel()
    {
        Icon icon = IfmlModelingHelper.getIconValue(mElement);

        String text = IfmlModelingHelper.getText(mElement);
        if (text.equals("") && icon == null)
        {
            IfmlModelingHelper.setText(mElement, "Label");
        }

        return null;
    }

    @Override
	public Object handleMenuBar()
    {
        List<String> menus = IfmlModelingHelper.getMenuBarMenus(mElement);
        if (menus.size() == 0)
        {
            menus = DEFAULT_MENUS;
            StereotypesHelper.setStereotypePropertyValue(mElement, IfmlWidgetsProfile.getInstance(Project.getProject
                    (mElement)).getMenuBar(), IfmlWidgetsProfile.MENUBAR_MENUS_PROPERTY, menus);
        }
        return null;
    }

    @Override
	public Object handlePasswordField()
    {
        String text = IfmlModelingHelper.getText(mElement);
        if (text.equals(""))
        {
            IfmlModelingHelper.setText(mElement, "Password");
        }
        return null;
    }    

    @Override
	public Object handleProgressBar()
    {
        return null;
    }

    @Override
	public Object handleRadioButton()
    {
        return null;
    }

    @Override
	public Object handleScrollBar()
    {
        return null;
    }

    @Override
	public Object handleSeparator()
    {
        return null;
    }

    @Override
	public Object handleSlider()
    {
        List<String> values = IfmlModelingHelper.getSliderValues(mElement);
        if (values.size() == 0)
        {
            values.add("" + DEFAULT_MIN_VALUE + "\n" + DEFAULT_MIN_VALUE);
            values.add("" + DEFAULT_MAX_VALUE / 2 + "\n" + DEFAULT_MAX_VALUE / 2);
            values.add("" + DEFAULT_MAX_VALUE + "\n" + DEFAULT_MAX_VALUE);
            StereotypesHelper.setStereotypePropertyValue(mElement, IfmlWidgetsProfile.getInstance(Project.getProject(mElement)).getSlider(), IfmlWidgetsProfile.SLIDER_VALUES_PROPERTY, values);
        }

        return null;
    }

    @Override
	public Object handleSpinner()
    {
        return null;
    }

    @Override
	public Object handleTextArea()
    {
        String text = IfmlModelingHelper.getText(mElement);
        if (text.equals(""))
        {
            IfmlModelingHelper.setText(mElement, "Text");
        }

        return null;
    }

    @Override
	public Object handleTextField()
    {
        String text = IfmlModelingHelper.getText(mElement);
        if (text.equals(""))
        {
            IfmlModelingHelper.setText(mElement, "Text");
        }
        return null;
    }

    @Override
	public Object handleFrame()
    {
        return null;
    }

    @Override
	public Object handleGroupBox()
    {
        String text = IfmlModelingHelper.getText(mElement);
        if (text.equals(""))
        {
            IfmlModelingHelper.setTitle(mElement, "Group Box");
        }

        return null;
    }

    @Override
	public Object handleList()
    {
        List<String> values = IfmlModelingHelper.getListValues(mElement);
        if (values.isEmpty())
        {
            values = DEFAULT_LIST_ITEMS;
            StereotypesHelper.setStereotypePropertyValue(mElement, IfmlWidgetsProfile.getInstance(Project.getProject(mElement)).getList(), IfmlWidgetsProfile.LIST_VALUES_PROPERTY, values);
        }

        if (IfmlModelingHelper.getListSelectedValue(mElement) == null)
        {
            StereotypesHelper.setStereotypePropertyValue(mElement, IfmlWidgetsProfile.getInstance(Project.getProject(mElement)).getList(), IfmlWidgetsProfile.LIST_SELECTED_VALUE_PROPERTY, values.get(0));
        }

        return null;
    }

    @Override
	public Object handlePanel()
    {
        return null;
    }

    @Override
	public Object handleScrollPane()
    {
        return null;
    }

    @Override
	public Object handleTabbedPane()
    {
        List<String> tabs = IfmlModelingHelper.getTabbedPaneTabs(mElement);
        if (tabs.isEmpty())
        {
            tabs = DEFAULT_TABS;
            StereotypesHelper.setStereotypePropertyValue(mElement, IfmlWidgetsProfile.getInstance(Project.getProject(mElement)).getTabbedPane(), IfmlWidgetsProfile.TABBEDPANE_TABS_PROPERTY, tabs);
        }

        if (IfmlModelingHelper.getTabbedPaneActiveTab(mElement) == null)
        {
            StereotypesHelper.setStereotypePropertyValue(mElement, IfmlWidgetsProfile.getInstance(Project.getProject(mElement)).getTabbedPane(), IfmlWidgetsProfile.TABBEDPANE_TABS_PROPERTY, tabs.get(0));
        }

        return null;
    }

    @Override
	public Object handleTable()
    {
        if (mElement.getOwnedElement().size() == 1)
        {
            TableHelper.createTable(mElement);
        }

        return null;
    }

    @Override
	public Object handleToolBar()
    {
        return null;
    }

    @Override
	public Object handleTree()
    {
        if (mElement.getOwnedElement().size() == 1)
        {
            IfmlModelingHelper.setText(mElement, "Root");
            Project project = Project.getProject(mElement);
            IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(Project.getProject(mElement));
            Stereotype leafStereotype = profile.getLeaf();
            Stereotype nodeStereotype = profile.getNode();

            ElementsFactory f = project.getElementsFactory();
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class node1 = createNode(nodeStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class node2 = createNode(nodeStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class node3 = createNode(nodeStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class node4 = createNode(nodeStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class leaf1 = createLeaf(leafStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class leaf2 = createLeaf(leafStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class leaf3 = createLeaf(leafStereotype, f);
            com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class leaf4 = createLeaf(leafStereotype, f);
            IfmlModelingHelper.setText(leaf1, "Leaf 1");
            IfmlModelingHelper.setText(leaf2, "Leaf 2");
            IfmlModelingHelper.setText(leaf3, "Leaf 3");
            IfmlModelingHelper.setText(leaf4, "Leaf 4");
            IfmlModelingHelper.setText(node1, "Node 1");
            IfmlModelingHelper.setText(node2, "Node 2");
            IfmlModelingHelper.setText(node3, "Node 3");
            IfmlModelingHelper.setText(node4, "Node 4");
            StereotypesHelper.setStereotypePropertyValue(node1, nodeStereotype, IfmlWidgetsProfile.NODE_EXPAND_PROPERTY, true);
            StereotypesHelper.setStereotypePropertyValue(node2, nodeStereotype, IfmlWidgetsProfile.NODE_EXPAND_PROPERTY, true);
            StereotypesHelper.setStereotypePropertyValue(node3, nodeStereotype, IfmlWidgetsProfile.NODE_EXPAND_PROPERTY, true);
            StereotypesHelper.setStereotypePropertyValue(node4, nodeStereotype, IfmlWidgetsProfile.NODE_EXPAND_PROPERTY, true);
            node1.setOwner(mElement);
            node2.setOwner(mElement);
            node3.setOwner(node1);
            node4.setOwner(node2);
            leaf1.setOwner(mElement);
            leaf2.setOwner(node3);
            leaf3.setOwner(node2);
            leaf4.setOwner(node2);
        }
        return null;
    }

    public static Property createCell(Stereotype cellStereotype, Class column1, Element row)
	{
        ElementsFactory f = Project.getProject(cellStereotype).getElementsFactory();
        Property cell = f.createPropertyInstance();
        cell.setOwner(row);
        StereotypesHelper.addStereotype(cell, cellStereotype);
		StereotypesHelper.setStereotypePropertyValue(cell, cellStereotype, IfmlWidgetsProfile.CELL_RELATEDCOLUMN_PROPERTY, column1);

		return cell;
	}

    public static com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class createLeaf(Stereotype leafStereotype, ElementsFactory f)
	{
		com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class leaf = f.createClassInstance();
		StereotypesHelper.addStereotype(leaf, leafStereotype);
		return leaf;
	}

    public static com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class createNode(Stereotype nodeStereotype, ElementsFactory f)
	{
		com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class node = f.createClassInstance();
		StereotypesHelper.addStereotype(node, nodeStereotype);
		return node;
	}
}