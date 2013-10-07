package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mindaugas Genutis
 */
public class JComponentDefaultSizeHandler implements JComponentHandler
{
    private Element mElement;

    public JComponentDefaultSizeHandler(PresentationElement pElement)
    {
        mElement = pElement.getElement();
    }

    @Override
	public Object handleButton()
    {
        Icon icon = IfmlModelingHelper.getIconValue(mElement);

        if (icon != null && IfmlModelingHelper.getText(mElement).length() == 0)
        {
            return new Dimension(icon.getIconWidth(), icon.getIconHeight());
        }
        else
        {
            return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
        }
    }

    @Override
	public Object handleCheckBox()
    {
        return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleComboBox()
    {
        return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleHyperlink()
    {
        return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleLabel()
    {
        return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleMenuBar()
    {
        return IfmlSizes.MENUBAR_INITIAL_SIZE;
    }

    @Override
	public Object handlePasswordField()
    {
        return IfmlSizes.PASSWORDFIELD_INITIAL_SIZE;
    }    

    @Override
	public Object handleProgressBar()
    {
        return IfmlSizes.SCROLLBAR_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleRadioButton()
    {
        return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleScrollBar()
    {
        return IfmlSizes.SCROLLBAR_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleSeparator()
    {
        return IfmlSizes.SCROLLBAR_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleSlider()
    {
        return IfmlSizes.SLIDER_INITIAL_SIZE;
    }

    @Override
	public Object handleSpinner()
    {
        return IfmlSizes.BUTTON_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleTextArea()
    {
        return IfmlSizes.TEXTAREA_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleTextField()
    {
        return IfmlSizes.PASSWORDFIELD_INITIAL_SIZE;
    }

    @Override
	public Object handleFrame()
    {
        return IfmlSizes.FRAME_INITIAL_SIZE;
    }

    @Override
	public Object handleGroupBox()
    {
        return IfmlSizes.TEXTAREA_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleList()
    {
        return IfmlSizes.TEXTAREA_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handlePanel()
    {
        return IfmlSizes.PANEL_INITIAL_SIZE;
    }

    @Override
	public Object handleScrollPane()
    {
        return IfmlSizes.PANEL_INITIAL_SIZE;
    }

    @Override
	public Object handleTabbedPane()
    {
        return IfmlSizes.PANEL_INITIAL_SIZE;
    }

    @Override
	public Object handleTable()
    {
        return IfmlSizes.TABLE_INITIAL_SIZE;
    }

    @Override
	public Object handleToolBar()
    {
        return IfmlSizes.SCROLLBAR_AND_OTHERS_INITIAL_SIZE;
    }

    @Override
	public Object handleTree()
    {
        return IfmlSizes.TREE_INITIAL_SIZE;
    }
}