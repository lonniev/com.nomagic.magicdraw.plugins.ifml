/**
 * $
 *
 * Copyright (c) 2003 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.editors.TextEditingResult;
import com.nomagic.magicdraw.uml.symbols.editors.TextEditor;
import com.nomagic.magicdraw.uml.symbols.shapes.TextObject;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

/**
 * @author Mindaugas Ringys
 * @version : 1 $, : $
 */
public class JComponentTextEditor extends TextEditor
{
    public JComponentTextEditor(TextObject textObject)
    {
        super(textObject);
        setEditStereotypes(false);
    }

    @Override
    public boolean isSpellCheckable()
    {
        return true;
    }

    @Override
    protected String getPureTextForEditing()
    {
        Element element = getEditableModelElement();
        return IfmlModelingHelper.getText(element);
    }

    @Override
    protected void parseText(PresentationElement element, TextEditingResult result, String newText, String oldText)
    {
        if(!oldText.equals(newText))
        {
            IfmlModelingHelper.setText(getEditableModelElement(), newText);
        }
    }
}
