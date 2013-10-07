package com.nomagic.magicdraw.plugins.ifml.javacomponents;

import com.nomagic.magicdraw.plugins.ifml.IfmlModelingHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class IfmlTreeCellRenderer extends DefaultTreeCellRenderer
{
    @Override
    public Component getTreeCellRendererComponent (JTree tree, Object value, boolean sel, boolean expanded,
                                                   boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        IfmlTreeNode node = (IfmlTreeNode) value;
        Element element = node.getElement();
        if (element != null && node.getChildCount() == 0 && IfmlModelingHelper.isNode(element))
        {
            setIcon(getClosedIcon());
        }

        if (node.isIconfied())
        {
            setIcon(node.getIcon());
        }

        if (node.isColored())
        {
            setOpaque(true);
        } else
        {
            setOpaque(false);
        }
        setBackground(tree.getBackground());
        setForeground(tree.getForeground());
        return this;
    }
}
