package com.nomagic.magicdraw.plugins.ifml.javacomponents;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class IfmlRowHeaderRenderer extends JLabel implements ListCellRenderer
{
	public IfmlRowHeaderRenderer (JTable table)
	{
		JTableHeader tableHeader = table.getTableHeader();
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(tableHeader.getForeground());
		setOpaque(true);
		setFont(tableHeader.getFont());
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean fSelected,
			boolean fCellHasFocus)
	{
		setForeground(list.getForeground());
		setFont(list.getFont());
		setText((value == null) ? "" : value.toString());
		return this;
	}
}
