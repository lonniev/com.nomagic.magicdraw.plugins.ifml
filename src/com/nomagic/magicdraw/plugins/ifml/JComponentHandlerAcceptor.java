package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.ClassView;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

/**
 * @author Mindaugas Genutis
 */
public class JComponentHandlerAcceptor
{
    public Object acceptHandler (PresentationElement pElement, JComponentHandler handler)
    {
        Object result = null;

        if (pElement instanceof ClassView)
        {
            NamedElement element = (NamedElement) pElement.getElement();
            result = acceptHandler(element, handler);
        }
        return result;
    }

    public Object acceptHandler (NamedElement element, JComponentHandler handler)
    {
        Object result = null;

        if (IfmlModelingHelper.isGroupBox(element))
        {
            result = handler.handleGroupBox();
        } else if (IfmlModelingHelper.isPanel(element))
        {
            result = handler.handlePanel();
        } else if (IfmlModelingHelper.isFrame(element))
        {
            result = handler.handleFrame();
        } else if (IfmlModelingHelper.isScrollPane(element))
        {
            result = handler.handleScrollPane();
        } else if (IfmlModelingHelper.isTabbedPane(element))
        {
            result = handler.handleTabbedPane();
        } else if (IfmlModelingHelper.isTable(element))
        {
            result = handler.handleTable();
        } else if (IfmlModelingHelper.isToolBar(element))
        {
            result = handler.handleToolBar();
        } else if (IfmlModelingHelper.isTree(element))
        {
            result = handler.handleTree();
        } else if (IfmlModelingHelper.isButton(element))
        {
            result = handler.handleButton();
        } else if (IfmlModelingHelper.isCheckBox(element))
        {
            result = handler.handleCheckBox();
        } else if (IfmlModelingHelper.isComboBox(element))
        {
            result = handler.handleComboBox();
        } else if (IfmlModelingHelper.isHyperlink(element))
        {
            result = handler.handleHyperlink();
        } else if (IfmlModelingHelper.isLabel(element))
        {
            result = handler.handleLabel();
        } else if (IfmlModelingHelper.isList(element))
        {
            result = handler.handleList();
        } else if (IfmlModelingHelper.isMenuBar(element))
        {
            result = handler.handleMenuBar();
        } else if (IfmlModelingHelper.isPasswordField(element))
        {
            result = handler.handlePasswordField();
        } else if (IfmlModelingHelper.isProgressBar(element))
        {
            result = handler.handleProgressBar();
        } else if (IfmlModelingHelper.isRadioButton(element))
        {
            result = handler.handleRadioButton();
        } else if (IfmlModelingHelper.isScrollBar(element))
        {
            result = handler.handleScrollBar();
        } else if (IfmlModelingHelper.isSeparator(element))
        {
            result = handler.handleSeparator();
        } else if (IfmlModelingHelper.isSlider(element))
        {
            result = handler.handleSlider();
        } else if (IfmlModelingHelper.isSpinner(element))
        {
            result = handler.handleSpinner();
        } else if (IfmlModelingHelper.isTextArea(element))
        {
            result = handler.handleTextArea();
        } else if (IfmlModelingHelper.isTextField(element))
        {
            result = handler.handleTextField();
        }
        return result;
    }
}