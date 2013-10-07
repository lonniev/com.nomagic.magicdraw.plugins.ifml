package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlContainer;
import com.nomagic.magicdraw.plugins.ifml.listeners.JTableListener;
import com.nomagic.magicdraw.plugins.ifml.listeners.JTreeListener;
import com.nomagic.magicdraw.uml.ExtendedPropertyNames;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.utils.StandardObjectHolder;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartPropertyChangeListener;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.WeakHashMap;


public class JComponentManager extends StandardObjectHolder
{
    private Map<PresentationElement, JComponent> mComponents = new WeakHashMap<PresentationElement, JComponent>();

    /**
     * Collection of elements those have listeners
     */
    private Map<Element, PropertyChangeListener> mElementListeners = new WeakHashMap<Element, PropertyChangeListener>();

    private JComponentManager(Project project)
    {
        super(project);
    }

    private PropertyChangeListener mPresentationElementRemoveListener = new PropertyChangeListener()
    {
        @Override
		public void propertyChange(PropertyChangeEvent evt)
        {
            String propertyName = evt.getPropertyName();
            Object source = evt.getSource();
            Object newValue = evt.getNewValue();

            if (ExtendedPropertyNames.REMOVE.equals(propertyName) && source == newValue)
            {
                removeComponent((PresentationElement) source);
            }
            if (ExtendedPropertyNames.ADD.equals(propertyName) && source == newValue)
            {
                ((PresentationElement)source).update();
            }
        }
    };

    /**
     * Gets instance of {@link com.nomagic.magicdraw.plugins.ifml.JComponentManager} by project.
     *
     * @param prj project.
     * @return instance of {@link com.nomagic.magicdraw.plugins.ifml.JComponentManager}.
     */
    public static JComponentManager getInstance(@Nonnull Project prj)
    {
        JComponentManager instance = _getInstance(JComponentManager.class, prj);
        if (instance == null)
        {
            instance = new JComponentManager(prj);
        }
        return instance;
    }


	public void removeElement(Element element)
	{
		mElementListeners.remove(element);
	}

    public void removeComponent(PresentationElement view)
    {
        JComponent component = getComponent(view);

        if (component != null)
        {
            Container container = component.getParent();
            JPanel panel = (JPanel) view.getDiagramSurface();

            if (panel != null)
            {
                panel.remove(container);
            }
        }

        mComponents.remove(view);
    }

	public boolean hasComponentFor(PresentationElement presentationElement)
	{
		return mComponents.containsKey(presentationElement);
	}

    public JComponent getComponent(final PresentationElement view)
    {
        JComponent component = mComponents.get(view);

        if (component == null)
        {
        	class FactoryRunnable implements Runnable
        	{
        		JComponent component;
				@Override
				public void run()
				{
		        	component = (JComponent) new JComponentHandlerAcceptor().acceptHandler(view, new JComponentFactory(view));

		            if (component != null)
		            {
		                IfmlContainer c = new IfmlContainer();
		                c.setLayout(new BorderLayout(0, 0));
		                c.add(component, BorderLayout.CENTER);
		                view.addPropertyChangeListener(mPresentationElementRemoveListener);
		            }
				}
        	};
        	FactoryRunnable factory = new FactoryRunnable();
        	DiagramPresentationElement.invokeLoadContentsOnDispatcher(factory);
            component = factory.component;
            if (component != null)
            {
            	mComponents.put(view, component);
            }
        }

        createSmartListener(view.getElement());

        return component;
    }

    private void createSmartListener(Element element)
    {
        if (!mElementListeners.containsKey(element))
        {
            JComponentListener listener = null;

            if (IfmlModelingHelper.isTable(element))
            {
                listener = new JTableListener();
            }

            if (IfmlModelingHelper.isTree(element))
            {
                listener = new JTreeListener();
            }

            if (listener != null)
            {
                SmartPropertyChangeListener.createSmartPropertyListener(element, listener,
                        listener.getSmartListenerConfigurations());
            }

            mElementListeners.put(element, listener);
        }
    }
}
