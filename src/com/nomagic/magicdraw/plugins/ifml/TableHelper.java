package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.core.ApplicationEnvironment;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * @author Mindaugas Genutis
 */
public class TableHelper
{
    public static List<Element> getColumnElements(Element tableElement)
    {
        List<Element> columns = new ArrayList<Element>();

        for (Element child : tableElement.getOwnedElement())
        {
            if (IfmlModelingHelper.isColumn(child))
            {
                columns.add(child);
            }
        }

        return columns;
    }

    public static Element getTableElement(Element element)
    {
        return IfmlModelingHelper.getTableComponent(element);
    }

    public static void updateTableModel(Element tableElement)
    {
        updateTableCells(tableElement);
    }

    public static void updateTableViews(Element tableElement)
    {
        for (JComponent component : JComponentListener.getElementComponents(tableElement))
        {
            JTable table = (JTable) IfmlModelingHelper.getScrollPaneViewComponent(component);
            DefaultTableModel tableModel = (DefaultTableModel) (table.getModel());

            while (tableModel.getRowCount() > 0)
                tableModel.removeRow(0);            

            List<Class> columns = getTableColumns(tableElement);
            TableColumnModel columnModel = new DefaultTableColumnModel();
            tableModel.setColumnCount(0);

            for (Class aColumn : columns)
            {
                TableColumn column = new TableColumn();
                String text = IfmlModelingHelper.getText(aColumn);
                column.setHeaderValue(text);
                columnModel.addColumn(column);
                tableModel.addColumn(text);
            }

            table.setColumnModel(columnModel);            

            List<Class> rows = getTableRows(tableElement);
            for (int i = 0; i < rows.size(); i++)
            {
                tableModel.addRow(new Vector());
            }

            table.clearSelection();
            table.setCellSelectionEnabled(true);
            table.setRowSelectionAllowed(true);
            table.setColumnSelectionAllowed(true);

            tableModel.fireTableStructureChanged();

            IfmlModelingHelper.setTableValues(tableElement, getColumnElements(tableElement), tableModel, table);
        }
    }

    public static List<Element> getRowCells(Element row)
    {
        List<Element> cells = new ArrayList<Element>();

        if (row.hasOwnedElement())
        {
            Collection<Element> elements = row.getOwnedElement();

            for (Element element : elements)
            {
                if (IfmlModelingHelper.isCell(element))
                {
                    cells.add(element);
                }
            }
        }

        return cells;
    }

    public static List<Element> getRows(Element tableElement)
    {
        List<Element> rows = new ArrayList<Element>();

        if (tableElement.hasOwnedElement())
        {
            Collection<Element> elements = tableElement.getOwnedElement();

            for (Element element : elements)
            {
                if (IfmlModelingHelper.isRow(element))
                {
                    rows.add(element);
                }
            }
        }

        return rows;
    }

    public static void updateTableCells(Element tableElement)
    {
        List<Class> columns = getTableColumns(tableElement);

        for (Element child : tableElement.getOwnedElement())
        {
            if (IfmlModelingHelper.isRow(child))
            {
                List<Element> cells = getRowCells(child);

                int diff = columns.size() - cells.size();

                for (int i = 0; i < diff; i++)
                {
                    addCell(child, cells.size() + i);
                }
            }
        }
    }

    public static List<com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class> getTableColumns(Element tableElement)
    {
        List<com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class> columns = new ArrayList<com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class>();

        Collection<Element> ownedElements = tableElement.getOwnedElement();

        for (Element element : ownedElements)
        {
            if (IfmlModelingHelper.isColumn(element))
            {
                columns.add((com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) element);
            }
        }        

        return columns;
    }

    public static List<com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class> getTableRows(Element tableElement)
    {
        List<com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class> rows =
                new ArrayList<com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class>();

        Collection<Element> ownedElements = tableElement.getOwnedElement();

        for (Element element : ownedElements)
        {
            if (IfmlModelingHelper.isRow(element))
            {
                rows.add((com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) element);
            }
        }

        return rows;
    }

    public static Element getCellColumn(Element cell)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(cell);
        List values = StereotypesHelper.getStereotypePropertyValue(cell, profile.getCell(),
                IfmlWidgetsProfile.CELL_RELATEDCOLUMN_PROPERTY);

        if(!values.isEmpty())
        {
            return (Element) values.get(0);
        }

        return null;
    }

    public static void addCell(Element rowElement, int cellIndex)
    {
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(Project.getProject(rowElement));

        List<Element> columns = getColumnElements(IfmlModelingHelper.getTableComponent(rowElement));
        JComponentElementConfigurator.createCell(profile.getCell(),
                (Class) columns.get(cellIndex), rowElement);
    }

    public static void removeCell(Element cell)
    {
        boolean checkSession = false;
        if (!SessionManager.getInstance().isSessionCreated())
        {
            SessionManager.getInstance().createSession("Remove Cell");
            checkSession = true;
        }
        try
        {
            ModelElementsManager.getInstance().removeElement(cell);
        }
        catch (ReadOnlyElementException e)
        {
            e.printStackTrace();
        }
        if (checkSession)
        {
            SessionManager.getInstance().closeSession();
        }
    }

    public static void createTable(Element tableElement)
    {
        Project project = Project.getProject(tableElement);
        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(Project.getProject(tableElement));

        Stereotype cellStereotype = profile.getCell();
        Stereotype columnStereotype = profile.getColumn();
        Stereotype rowStereotype = profile.getRow();

        ElementsFactory f = project.getElementsFactory();

        Class column1 = f.createClassInstance();
        Class column2 = f.createClassInstance();
        StereotypesHelper.addStereotype(column1, columnStereotype);
        StereotypesHelper.addStereotype(column2, columnStereotype);
        IfmlModelingHelper.setText(column1, "Column 1");
        IfmlModelingHelper.setText(column2, "Column 2");
        column1.setOwner(tableElement);
        column2.setOwner(tableElement);

        com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class row1 = f.createClassInstance();
        com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class row2 = f.createClassInstance();
        StereotypesHelper.addStereotype(row1, rowStereotype);
        StereotypesHelper.addStereotype(row2, rowStereotype);
        IfmlModelingHelper.setText(row1, "Row 1");
        IfmlModelingHelper.setText(row2, "Row 2");
        row1.setOwner(tableElement);
        row2.setOwner(tableElement);

        Property cell1_1 = JComponentElementConfigurator.createCell(cellStereotype, column1, row1);
        Property cell1_2 = JComponentElementConfigurator.createCell(cellStereotype, column2, row1);
        Property cell2_1 = JComponentElementConfigurator.createCell(cellStereotype, column1, row2);
        Property cell2_2 = JComponentElementConfigurator.createCell(cellStereotype, column2, row2);

        if (ApplicationEnvironment.isDeveloper())
        {
            IfmlModelingHelper.setText(cell1_1, "11");
            IfmlModelingHelper.setText(cell1_2, "12");
            IfmlModelingHelper.setText(cell2_1, "21");
            IfmlModelingHelper.setText(cell2_2, "22");
        }

        cell1_1.setOwner(row1);
        cell1_2.setOwner(row1);
        cell2_1.setOwner(row2);
        cell2_2.setOwner(row2);
    }
}
