package com.nomagic.magicdraw.plugins.ifml.listeners;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.JComponentListener;
import com.nomagic.magicdraw.plugins.ifml.TreeHelper;
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
public class JTreeListener extends JComponentListener
{
    private UpdateTransactionCommitListener mUpdateListener;

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        super.propertyChange(evt);

        Project project = Project.getProject(mElement);
        //register a "lazy" tree updater after all events are fired if transaction is started, otherwise update tree immediately
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
            TreeHelper.updateTreeViews(IfmlModelingHelper.getTreeComponent(mElement));
        }
    }

    @Override
    public List<SmartListenerConfig> getSmartListenerConfigurations()
    {
        List <SmartListenerConfig> configurations = new ArrayList<SmartListenerConfig>();

        SmartListenerConfig config = new SmartListenerConfig();
        config.listenTo(PropertyNames.APPLIED_STEREOTYPE_INSTANCE,
                SmartListenerConfig.STEREOTYPE_AND_TAGS_INSTANCE_SPECIFICATION_CONFIG);
        config.listenTo(PropertyNames.OWNED_ELEMENT, config);

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
                    TreeHelper.updateTreeViews(IfmlModelingHelper.getTreeComponent(mElement));
                }
            };
        }
    }
}