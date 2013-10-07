package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.core.ApplicationEnvironment;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlFrame;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlTreeNode;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.PropertyID;
import com.nomagic.magicdraw.properties.PropertyManager;
import com.nomagic.magicdraw.uml.symbols.DefaultSymbolStyleHelper;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.ShapeElement;
import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Handler for setting component foreground, bacground, text colors and font according symbol properties.
 *
 * @author Mindaugas Ringys
 * @author Mindaugas Genutis
 */
public class JComponentColorFontHandler implements JComponentHandler
{
    private ShapeElement pElement;

    private JComponent component;

    private PropertyManager mSpecificProperties;

    public JComponentColorFontHandler(ShapeElement pElement, JComponent component)
    {
        this.pElement = pElement;
        this.component = component;
        mSpecificProperties = (PropertyManager)pElement.getPropertyManager().clone();
        mSpecificProperties.distinct(DefaultSymbolStyleHelper.getDefaultPropertyManagerFor(pElement,pElement.getDiagramPresentationElement()));
    }

    @Override
	public Object handleButton()
    {
        component.setForeground(getTextColor(component));
        component.setBackground(getBackgroundColor(component));
        component.setFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleCheckBox()
    {
        component.setOpaque(pElement.isUseFillColor());
        component.setBackground(getBackgroundColor(component));
        component.setForeground(getTextColor(component));
        component.setFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleComboBox()
    {
        component.setBackground(getBackgroundColor(component));
        component.setForeground(getTextColor(component));
        component.setFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleHyperlink()
    {
        component.setOpaque(pElement.isUseFillColor());
        component.setBackground(getBackgroundColor(component));
        component.setFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleLabel()
    {
        component.setOpaque(pElement.isUseFillColor());
        component.setBackground(getBackgroundColor(component));
        component.setForeground(getTextColor(component));
        component.setFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleMenuBar()
    {
        JMenuBar bar = (JMenuBar) component;
        Color backgroundColor = getBackgroundColor(bar);
        bar.setBackground(backgroundColor);

        Color textColor = getTextColor(bar);
        Font textFont = getTextFont(bar);
        for (Component menu : bar.getComponents())
        {
            menu.setForeground(textColor);
            menu.setBackground(backgroundColor);
            menu.setFont(textFont);
        }
        return null;
    }

    @Override
	public Object handlePasswordField()
    {
        component.setForeground(getTextColor(component));
        component.setFont(getTextFont(component));
        return null;
    }    

    @Override
	public Object handleProgressBar()
    {
        component.setForeground(pElement.isUseFillColor() ? getBackgroundColor(component) : UIManager.getColor(getUIID(component) + ".foreground"));
        // seems not possible to change text color, just with editing uimanager, but then every new bar is changed
        return null;
    }

    @Override
	public Object handleRadioButton()
    {
        component.setOpaque(pElement.isUseFillColor());
        component.setBackground(getBackgroundColor(component));
        component.setFont(getTextFont(component));
        component.setForeground(getTextColor(component));
        return null;
    }

    @Override
	public Object handleScrollBar()
    {
        component.setBackground(getBackgroundColor(component));
        return null;
    }

    @Override
	public Object handleSeparator()
    {
        component.setForeground(getForegroundColor(component));
        return null;
    }

    @Override
	public Object handleSlider()
    {
        component.setBackground(getBackgroundColor(component));

        Color textColor = getTextColor(component);
        Font textFont = getTextFont(component);
        JSlider slider = (JSlider) component;
        if(slider.getLabelTable() != null)
        {
            Enumeration<JLabel> labels = slider.getLabelTable().elements();

            while (((Iterator<JLabel>) labels).hasNext())
            {
                JLabel label = ((Iterator<JLabel>) labels).next();
                label.setForeground(textColor);
                label.setFont(textFont);
            }
        }
        return null;
    }

    @Override
	public Object handleSpinner()
    {
        JSpinner spinner = (JSpinner)component;
        JTextField editor = (JTextField) spinner.getEditor();
        Font font = getTextFont(editor);
        Color textColor = getTextColor(editor);
        Color background = getBackgroundColor(editor);
        editor.setForeground(textColor);
        editor.setFont(font);
        editor.setBackground(background);
        return null;
    }

    @Override
	public Object handleTextArea()
    {
        JTextArea area = (JTextArea) IfmlModelingHelper.getScrollPaneViewComponent(component);
        area.setForeground(getTextColor(area));
        area.setFont(getTextFont(area));
        area.setBackground(getBackgroundColor(area));
        return null;
    }

    @Override
	public Object handleTextField()
    {
        component.setBackground(getBackgroundColor(component));
        component.setForeground(getTextColor(component));
        component.setFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleFrame()
    {
        ((IfmlFrame) component).getContentPane().setBackground(getBackgroundColor(component));
        // ((WindowsInternalFrameTitlePane)
        // component.getComponent(FIRST_COMPONENT)).getComponent(1).setForeground(color);

        if (!ApplicationEnvironment.isMacOS())
        {
            Font textFont = getTextFont(component);
            for (int i = 0; i < component.getComponents().length; i++)
            {
                Component c = component.getComponents()[i];
                if (c instanceof WindowsInternalFrameTitlePane)
                {
                    c.setFont(textFont);
                }
            }
        }
        return null;
    }

    @Override
	public Object handleGroupBox()
    {
        component.setBackground(getBackgroundColor(component));

        TitledBorder border = (TitledBorder) component.getBorder();
        Border innerBorder = border.getBorder();
        if(innerBorder instanceof LineBorder)
        {
            border.setBorder(BorderFactory.createLineBorder(getBorderColor(component)));
        }
        border.setTitleColor(getTextColor(component));
        border.setTitleFont(getTextFont(component));
        return null;
    }

    @Override
	public Object handleList()
    {
        JList list = (JList) IfmlModelingHelper.getScrollPaneViewComponent(component);
        list.setForeground(getTextColor(list));
        list.setFont(getTextFont(list));
        list.setBackground(getBackgroundColor(list));
        return null;
    }

    @Override
	public Object handlePanel()
    {
        component.setBackground(getBackgroundColor(component));
        return null;
    }

    @Override
	public Object handleScrollPane()
    {
        ((JScrollPane) component).getViewport().setBackground(getBackgroundColor(component));
        return null;
    }

    @Override
	public Object handleTabbedPane()
    {
        JTabbedPane tabPane = (JTabbedPane) component;
        Color backgroundColor = getBackgroundColor(tabPane);

        tabPane.setForeground(getTextColor(tabPane));
        tabPane.setFont(getTextFont(tabPane));
        tabPane.setBackground(backgroundColor);

        JPanel panel = (JPanel) tabPane.getSelectedComponent();
        if (panel == null && tabPane.getTabCount() > 0)
        {
            panel = (JPanel) tabPane.getComponentAt(0);
        }
        if (panel != null)
        {
            panel.setBackground(backgroundColor);
        }
        return null;
    }

    @Override
	public Object handleTable()
    {
        JScrollPane scrollPane = (JScrollPane) component;
        JTable table = (JTable) IfmlModelingHelper.getScrollPaneViewComponent(component);
        Color textColor = getTextColor(table);
        table.setForeground(textColor);
        Font textFont = getTextFont(table);
        table.setFont(textFont);
        // table.setGridColor(Color.black);
        Color backgroundColor = getBackgroundColor(table);
        table.setBackground(backgroundColor);

        initTableHeader(textColor, textFont, backgroundColor, scrollPane.getColumnHeader());
        initTableHeader(textColor, textFont, backgroundColor, scrollPane.getRowHeader());

        return null;
    }

    private void initTableHeader(Color textColor, Font textFont, Color bacgroundColor, JViewport header)
    {
        if (header != null && header.getComponentCount() > 0)
        {
            Component headerComponent = header.getComponent(0);

            headerComponent.setForeground(textColor);
            headerComponent.setBackground(bacgroundColor);
            headerComponent.setFont(textFont);
        }
    }

    @Override
	public Object handleToolBar()
    {
        component.setBackground(getBackgroundColor(component));
        return null;
    }

    @Override
	public Object handleTree()
    {
        JTree tree = (JTree) IfmlModelingHelper.getScrollPaneViewComponent(component);
        tree.setForeground(getTextColor(tree));
        tree.setFont(getTextFont(tree));

        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        tree.setBackground(getBackgroundColor(tree));
        for (IfmlTreeNode node : IfmlModelingHelper.getIfmlTreeNodes((IfmlTreeNode) treeModel.getRoot(),
                new ArrayList<IfmlTreeNode>(), treeModel))
        {
            node.setColored(pElement.isUseFillColor());
        }
        return null;
    }

    private static String getUIID(JComponent c)
    {
        String id = c.getUIClassID();
        if(id.endsWith("UI"))
        {
            id=id.substring(0, id.length()-2);
        }
        return id;
    }

    public Color getBackgroundColor(JComponent c)
    {
        return getBackgroundColor(pElement, c);
    }

    public static Color getBackgroundColor(PresentationElement pElement, JComponent c)
    {
        return pElement.isUseFillColor() ? pElement.getFillColor() : UIManager.getColor(getUIID(c) + ".background");
    }

    public Color getForegroundColor(JComponent c)
    {
        return getForegroundColor(pElement, c, mSpecificProperties);
    }

    public static Color getForegroundColor(PresentationElement pElement, JComponent c, PropertyManager properties)
    {
        return properties.getProperty(PropertyID.PEN_COLOR) != null ? pElement.getLineColor() : UIManager.getColor(getUIID(c) + ".foreground");
    }

    public Color getTextColor(JComponent c)
    {
        return getTextColor(pElement, c, mSpecificProperties);
    }

    public static Color getTextColor(PresentationElement view, JComponent c, PropertyManager properties)
    {
        Property textColor = properties.getProperty(PropertyID.TEXT_COLOR);

        if (textColor == null)
        {
            String uiid = getUIID(c);

            if ("Slider".equals(uiid))
            {
                uiid = "Label";
            }

            return UIManager.getColor(uiid + ".foreground");
        }
        else
        {
            return view.getTextColor();
        }
    }

    public Font getTextFont(JComponent c)
    {
        return getTextFont(pElement, c, mSpecificProperties);
    }

    public static Font getTextFont(PresentationElement view, JComponent c, PropertyManager properties)
    {
        Property fontProperty = properties.getProperty(PropertyID.FONT);

        if (fontProperty == null)
        {
            Font font = UIManager.getFont(getUIID(c) + ".font");

            if (font != null)
            {
                return font;
            }
        }
        else
        {
            return view.getFont();
        }

        return UIManager.getFont("Label.font");
    }

    public Color getBorderColor(JComponent c)
    {
        return getBorderColor(pElement, c, mSpecificProperties);
    }

    public static Color getBorderColor(PresentationElement pElement, JComponent c, PropertyManager properties)
    {
        return properties.getProperty(PropertyID.PEN_COLOR ) != null ? pElement.getLineColor() : UIManager.getColor(getUIID(c) + ".foreground");
    }
}