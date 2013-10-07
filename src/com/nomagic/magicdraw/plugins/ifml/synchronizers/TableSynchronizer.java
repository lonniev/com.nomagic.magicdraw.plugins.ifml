package com.nomagic.magicdraw.plugins.ifml.synchronizers;

import com.nomagic.magicdraw.commands.CommandHistory;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.IfmlWidgetsProfile;
import com.nomagic.magicdraw.plugins.ifml.JComponentElementConfigurator;
import com.nomagic.magicdraw.plugins.ifml.TableHelper;
import com.nomagic.magicdraw.plugins.ifml.IfmlModelingHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification;
import com.nomagic.uml2.ext.magicdraw.components.mdbasiccomponents.Component;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.PropertyNames;
import com.nomagic.uml2.synchronizer.TransactionBasedSynchronizer;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;

/**
 * @author Mindaugas Genutis
 */
public class TableSynchronizer extends TransactionBasedSynchronizer
{
    @Override
	public Runnable transactionCommited(Collection<PropertyChangeEvent> events)
    {
        for (PropertyChangeEvent event : events)
        {
            String propertyName = event.getPropertyName();
            Object source = event.getSource();

            if (PropertyNames.APPLIED_STEREOTYPE_INSTANCE.equals(propertyName))
            {
                if (event.getNewValue() instanceof InstanceSpecification)
                {
                    InstanceSpecification instanceSpecification = (InstanceSpecification) event.getNewValue();
                    CommandHistory history = Project.getProject(instanceSpecification).getCommandHistory();
                    //TODO mingen exit and do not analyze other events if you already now that undo/redo is going
                    if (!history.isUndoingOrRedoing())
                    {
                        final IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(instanceSpecification);
                        Stereotype rowStereotype = profile.getRow();
                        Stereotype columnStereotype = profile.getColumn();

                        if (rowStereotype != null)
                        {
                            if (instanceSpecification.getClassifier().contains(rowStereotype))
                            {
                                final Element row = (Element) source;
                                final Element tableElement = TableHelper.getTableElement(row);
                                //TODO mingen - you return just one runnable for first found element. This is wrong, because many elements may be affected by changes in transaction. You need to collect them and execute runnable for all of them
                                return new Runnable()
                                {
                                    @Override
									public void run()
                                    {
                                        List<Element> columns = TableHelper.getColumnElements(tableElement);

                                        for (Element column : columns)
                                        {
                                            JComponentElementConfigurator.createCell(profile.getCell(),
                                                    (Class) column, row);
                                        }
                                    }
                                };
                            }

                            if (instanceSpecification.getClassifier().contains(columnStereotype))
                            {
                                final Element column = (Element) source;
                                final Element tableElement = TableHelper.getTableElement(column);

                                return new Runnable()
                                {
                                    @Override
									public void run()
                                    {
                                        TableHelper.updateTableModel(tableElement);
                                    }
                                };
                            }
                        }
                    }
                }
            }

            if (PropertyNames._U_M_L_CLASS.equals(propertyName)
                    && source instanceof Class
                    && event.getOldValue() instanceof Element)
            {
                if (IfmlModelingHelper.isTable((Element) event.getOldValue()))
                {
                    final Element tableElement = TableHelper.getTableElement((Element) event.getOldValue());

                    if (tableElement != null)
                    {
                        return new Runnable()
                        {
                            @Override
							public void run()
                            {
                                TableHelper.updateTableModel(tableElement);
                            }
                        };
                    }
                }
            }

            if (PropertyNames.NESTED_CLASSIFIER.equals(propertyName)
                    && event.getNewValue() == null
                    && event.getOldValue() instanceof Class
                    && source instanceof Component
                    && IfmlModelingHelper.isTable((Element) source))
            {
                final Element tableElement = TableHelper.getTableElement((Element) source);

                if (tableElement != null)
                {
                    return new Runnable()
                    {
                        @Override
						public void run()
                        {
                            List<Element> rows = TableHelper.getRows(tableElement);

                            for (Element row : rows)
                            {
                                List<Element> cells = TableHelper.getRowCells(row);

                                for (Element cell : cells)
                                {
                                    Element cellColumn = TableHelper.getCellColumn(cell);

                                    if (cellColumn == null)
                                    {
                                        TableHelper.removeCell(cell);
                                    }
                                }
                            }
                        }
                    };
                }
            }
        }

        return null;
    }
}