package com.nomagic.magicdraw.plugins.ifml;

import com.jidesoft.grid.JideTable;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlFrame;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlTableCellRenderer;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlTreeNode;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author Mindaugas Genutis
 */
public class JComponentFactory implements JComponentHandler
{
    private NamedElement element;

    public JComponentFactory (PresentationElement pElement)
    {
        element = (NamedElement) pElement.getElement();
    }

    @Override
    public Object handleButton ()
    {
        return new JButton();
    }

    @Override
    public Object handleCheckBox ()
    {
        return new JCheckBox();
    }

    @Override
    public Object handleComboBox ()
    {
        return new JComboBox();
    }

    @Override
    public Object handleHyperlink ()
    {
        return new JLabel();
    }

    @Override
    public Object handleLabel ()
    {
        return new JLabel();
    }

    @Override
    public Object handleMenuBar ()
    {
        return new JMenuBar();
    }

    @Override
    public Object handlePasswordField ()
    {
        return new JPasswordField();
    }

    @Override
    public Object handleProgressBar ()
    {
        return new JProgressBar();
    }

    @Override
    public Object handleRadioButton ()
    {
        return new JRadioButton();
    }

    @Override
    public Object handleScrollBar ()
    {
        return new JScrollBar();
    }

    @Override
    public Object handleSeparator ()
    {
        return new JSeparator();
    }

    @Override
    public Object handleSlider ()
    {
        return new JSlider();
    }

    @Override
    public Object handleSpinner ()
    {
        JSpinner spinner = new JSpinner();
        spinner.setEditor(new JTextField());
        return spinner;
    }

    @Override
    public Object handleTextArea ()
    {
        return new JScrollPane(new JTextArea(1, 20));
    }

    @Override
    public Object handleTextField ()
    {
        return new JTextField();
    }

    @Override
    public Object handleFrame ()
    {
        return new IfmlFrame(IfmlModelingHelper.getText(element), false, true);
    }

    @Override
    public Object handleGroupBox ()
    {
        return new JPanel();
    }

    @Override
    public Object handleList ()
    {

        return new JScrollPane(new JList());
    }

    @Override
    public Object handlePanel ()
    {
        return new JPanel();
    }

    @Override
    public Object handleScrollPane ()
    {
        return new JScrollPane();
    }

    @Override
    public Object handleTabbedPane ()
    {
        return new JTabbedPane();
    }

    @Override
    public Object handleTable ()
    {
        JideTable table = new JideTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowAutoResizes(true);

        table.setDefaultRenderer(Object.class, new IfmlTableCellRenderer());

        return new JScrollPane(table);
    }

    @Override
    public Object handleToolBar ()
    {
        return new JToolBar();
    }

    @Override
    public Object handleTree ()
    {
        return new JScrollPane(new JTree(new DefaultTreeModel(new IfmlTreeNode(IfmlModelingHelper.getText(element), element))));
    }
}
