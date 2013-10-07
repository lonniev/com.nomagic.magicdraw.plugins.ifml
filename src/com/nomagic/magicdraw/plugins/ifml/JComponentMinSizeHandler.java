package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.html.HTMLHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mindaugas Genutis
 */
public class JComponentMinSizeHandler implements JComponentHandler
{
    private NamedElement element;

    private JComponent component;

    private Dimension dimension;

    public JComponentMinSizeHandler(PresentationElement pElement, JComponent component, Dimension size)
    {
        element = (NamedElement)pElement.getElement();
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
        return handleTextField();
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
        Dimension comSize = component.getMinimumSize();
        int orientation = IfmlModelingHelper.getComponentOrientation(component, element);

        if (orientation == Adjustable.HORIZONTAL)
        {
            dimension.width = comSize.height;
            dimension.height = IfmlSizes.BUTTON_MIN_SIZE.height;
        }
        else if (orientation == Adjustable.VERTICAL)
        {
            dimension.width = IfmlSizes.BUTTON_MIN_SIZE.width;
            dimension.height = comSize.height;
        }
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
        return handleTextField();
    }

    @Override
	public Object handleTextField()
    {
        return handlePasswordField();
    }

    @Override
	public Object handleFrame()
    {
        dimension.width = IfmlSizes.FRAME_MIN_SIZE.width;
        dimension.height = IfmlSizes.FRAME_MIN_SIZE.height;
        return dimension;
    }

    @Override
	public Object handleGroupBox()
    {
        return handlePanel();
    }

    @Override
	public Object handleList()
    {
        return handlePanel();
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
        return handlePanel();
    }

    @Override
	public Object handleTabbedPane()
    {
        return handlePanel();
    }

    @Override
	public Object handleTable()
    {
        dimension.width = IfmlSizes.TABLE_MIN_SIZE.width;
        dimension.height = IfmlSizes.TABLE_MIN_SIZE.height;
        return dimension;
    }

    @Override
	public Object handleToolBar()
    {
        Dimension comSize = component.getMinimumSize();
        dimension.width = comSize.width;
        dimension.height = comSize.height;
        return dimension;
    }

    @Override
	public Object handleTree()
    {
        return handlePanel();
    }
}