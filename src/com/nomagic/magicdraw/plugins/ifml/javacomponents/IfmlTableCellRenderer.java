package com.nomagic.magicdraw.plugins.ifml.javacomponents;

import com.nomagic.magicdraw.uml.symbols.shapes.html.HTMLHelper;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author Mindaugas Genutis
 */
public class IfmlTableCellRenderer implements TableCellRenderer
{
    private JTextArea mTextAreaRenderer;

    public IfmlTableCellRenderer ()
    {
        mTextAreaRenderer = new JTextArea();
        mTextAreaRenderer.setLineWrap(true);
        mTextAreaRenderer.setWrapStyleWord(true);
        mTextAreaRenderer.setOpaque(true);
        mTextAreaRenderer.setMinimumSize(new Dimension(0, 0));
    }

    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column)
    {
        String text = (value == null) ? "" : value.toString();
        boolean html = HTMLHelper.isHTMLText(text);
        JComponent renderer;
//        if(html)
//        {
//            //create different component for every cell, otherwise rendering does not work....
//            HTMLTextPanel htmlRenderer = new HTMLTextPanel(text);
//            HTMLSferyxEditor htmlSferyxEditor = htmlRenderer.getEditor();
//            htmlSferyxEditor.setPreferredSize(null);
//            JEditorPane jEditorPane = htmlSferyxEditor.getInternalJEditorPane();
//            Dimension size = jEditorPane.getPreferredSize();
//            //do some hack with height, otherwise text does not fit into cell
//            size.height+=50;
//            htmlRenderer.setPreferredSize(size);
//            renderer = jEditorPane;
//        }
//        else
        {
            mTextAreaRenderer.setText((value == null) ? "" : value.toString());
            renderer = mTextAreaRenderer;
        }
        renderer.setFont(table.getFont());

        if (isSelected)
        {
            renderer.setBackground(table.getSelectionBackground());
            renderer.setForeground(table.getSelectionForeground());
        } else
        {
            renderer.setForeground(table.getForeground());
            renderer.setBackground(table.getBackground());
        }
        return renderer;
    }
}
