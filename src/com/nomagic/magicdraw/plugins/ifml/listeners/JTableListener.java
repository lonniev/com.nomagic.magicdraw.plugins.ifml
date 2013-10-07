package com.nomagic.magicdraw.plugins.ifml.listeners;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.JComponentListener;
import com.nomagic.magicdraw.plugins.ifml.TableHelper;
import com.nomagic.magicdraw.plugins.ifml.IfmlModelingHelper;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;
import com.nomagic.uml2.transaction.TransactionCommitListener;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Mindaugas Genutis
 * @author Mindaugas Ringys
 */
public class JTableListener extends JComponentListener
{
    private UpdateTransactionCommitListener mUpdateListener;

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        super.propertyChange(evt);
        Project project = Project.getProject(mElement);
        //register a "lazy" table updater after all events are fired if transaction is started, otherwise update table immediately
        if(project.getTransactionsManager().isStarted())
        {
            if(mUpdateListener == null)
            {
                mUpdateListener = new UpdateTransactionCommitListener(mElement);
                project.getTransactionsManager().addTransactionCommitListenerIncludingUndoAndRedo(mUpdateListener);
            }
        }
        else
        {
            TableHelper.updateTableViews(IfmlModelingHelper.getTableComponent(mElement));
        }
    }

    @Override
    public List<SmartListenerConfig> getSmartListenerConfigurations()
    {
        List <SmartListenerConfig> configurations = new ArrayList<SmartListenerConfig>();

        SmartListenerConfig cellConfig = new SmartListenerConfig();
        cellConfig.listenToNested(PropertyNames.OWNED_ELEMENT).listenTo(PropertyNames.OWNED_ELEMENT,
                SmartListenerConfig.STEREOTYPE_AND_TAGS_CONFIG);
        configurations.add(cellConfig);

        configurations.add(cellConfig);

        SmartListenerConfig config = new SmartListenerConfig();
        config.listenTo(PropertyNames.OWNED_ELEMENT, SmartListenerConfig.STEREOTYPE_AND_TAGS_CONFIG);

        configurations.add(config);

        return configurations;
    }


    private class UpdateTransactionCommitListener implements TransactionCommitListener
    {
        private final Element element;

        public UpdateTransactionCommitListener(Element element)
        {
            this.element = element;
        }

        @Override
        public Runnable transactionCommited(Collection<PropertyChangeEvent> events)
        {
            return new Runnable()
            {
                @Override
                public void run()
                {
                    Project.getProject(element).getTransactionsManager().removeTransactionCommitListener(mUpdateListener);
                    mUpdateListener = null;
                    TableHelper.updateTableViews(IfmlModelingHelper.getTableComponent(element));
                }
            };
        }
    }
}