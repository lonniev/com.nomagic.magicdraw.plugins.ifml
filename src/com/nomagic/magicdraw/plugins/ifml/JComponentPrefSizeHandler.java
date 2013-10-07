package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.uml.symbols.shapes.html.HTMLHelper;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mindaugas Genutis
 */
//TODO mingen I don't know if we need this class at all...
public class JComponentPrefSizeHandler implements JComponentHandler
{
    private JComponent component;

    private Dimension dimension;

    public JComponentPrefSizeHandler(JComponent component, Dimension size)
    {
        this.component = component;
        dimension = size;
    }

    @Override
	public Object handleButton()
    {
        dimension.width = IfmlSizes.BUTTON_MIN_SIZE.width;
        dimension.height = IfmlSizes.BUTTON_MIN_SIZE.height;
        return dimension;
    }

    @Override
	public Object handleCheckBox()
    {
        return handleButton();
    }

    @Override
	public Object handleComboBox()
    {
        return handleButton();
    }

    @Override
	public Object handleHyperlink()
    {
        return handleLabel();
    }

    @Override
	public Object handleLabel()
    {
        JLabel label = (JLabel) component;
        String text = label.getText();
        if(HTMLHelper.isHTMLText(text))
        {
            text = HTMLHelper.getPureTextFromHTMLSource(text);
        }
        dimension.width = (int) (text.length() * IfmlSizes.LABEL_LENGTH_ADJUSTMENT);
        dimension.height = component.getFont().getSize();
        Icon icon = label.getIcon();
        if (icon != null)
        {
            dimension.width = icon.getIconWidth();
            dimension.height = icon.getIconHeight();
        }
        return dimension;
    }

    @Override
	public Object handleMenuBar()
    {
        return handlePanel();
    }

    @Override
	public Object handlePasswordField()
    {
        dimension.width = IfmlSizes.PASSWORDFIELD_MIN_SIZE.width;
        dimension.height = IfmlSizes.PASSWORDFIELD_MIN_SIZE.height;
        return dimension;
    }    

    @Override
	public Object handleProgressBar()
    {
        return handleScrollBar();
    }

    @Override
	public Object handleRadioButton()
    {
        return handleButton();
    }

    @Override
	public Object handleScrollBar()
    {
        //TODO mingen it is too dangerous to use component.getMinimumSize(). because this is platform specific
        dimension.setSize(component.getMinimumSize());
        return dimension;
    }

    @Override
	public Object handleSeparator()
    {
        return handleScrollBar();
    }

    @Override
	public Object handleSlider()
    {
        return handleToolBar();
    }

    @Override
	public Object handleSpinner()
    {
        return handleButton();
    }

    @Override
	public Object handleTextArea()
    {
        return handlePanel();
    }

    @Override
	public Object handleTextField()
    {
        return handlePasswordField();
    }

    @Override
	public Object handleFrame()
    {
        return handlePanel();
    }

    @Override
	public Object handleGroupBox()
    {
        return handlePanel();
    }

    @Override
	public Object handleList()
    {
        return handleTextField();
    }

    @Override
	public Object handlePanel()
    {
        dimension.width = IfmlSizes.BUTTON_MIN_SIZE.width;
        dimension.height = IfmlSizes.BUTTON_MIN_SIZE.height;
        return dimension;
    }

    @Override
	public Object handleScrollPane()
    {
        //TODO mingen it is too dangerous to use component.getPreferredSize(). because this is platform specific
        Dimension preferredSize = component.getPreferredSize();
        dimension.width = preferredSize.width;
        dimension.height = preferredSize.height;
        return dimension;
    }

    @Override
	public Object handleTabbedPane()
    {
        return handlePanel();
    }

    @Override
	public Object handleTable()
    {
        JTable table = (JTable) IfmlModelingHelper.getScrollPaneViewComponent(component);
        //TODO mingen it is too dangerous to use component.getPreferredSize(). because this is platform specific
        Dimension preferredSize = table.getPreferredSize();
        dimension.width = preferredSize.width;
/*
        if (IfmlModelingHelper.isTableColumnHeaderVisible (element))
        {
            if (System.getProperty("os.name").equals("Windows XP"))
            {
                dimension.height = preferredSize.height + IfmlSizes.TABLE_ADJUSTMENT_XP;
            }
            else
            {
                dimension.height = preferredSize.height + IfmlSizes.TABLE_ADJUSTMENT_VISTA;
            }
        }
        else
*/
        {
            dimension.height = Math.min(preferredSize.height, 100);
        }
        return dimension;
    }

    @Override
	public Object handleToolBar()
    {
        //TODO mingen it is too dangerous to use component.getMinimumSize(). because this is platform specific
        dimension.setSize(component.getMinimumSize());
        return dimension;
    }

    @Override
	public Object handleTree()
    {
        Component scrollPaneComponent = IfmlModelingHelper.getScrollPaneViewComponent(component);
        //TODO mingen it is too dangerous to use component.getPreferredSize(). because this is platform specific
        Dimension preferredSize = scrollPaneComponent.getPreferredSize();
        dimension.width = preferredSize.width;
        dimension.height = preferredSize.height;
        return dimension;
    }


}