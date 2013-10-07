package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.uml.symbols.AbstractSymbolDecoratorProvider;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.SymbolDecorator;

import javax.swing.*;

public class IfmlRendererProvider extends AbstractSymbolDecoratorProvider
{
    @Override
    public SymbolDecorator getSymbolDecorator(PresentationElement pElement)
    {
        JComponentRenderer symbolDecorator = (JComponentRenderer) super.getSymbolDecorator(pElement);
        if (symbolDecorator == null &&
            IfmlModelingHelper.isUISymbol(pElement))
        {
            JComponent component = JComponentManager.getInstance(pElement.getProject()).getComponent(pElement);
            if (component != null)
            {
                symbolDecorator = new JComponentRenderer(component);
                addToCash(pElement, symbolDecorator);
            }
        }
        return symbolDecorator;
    }
}
