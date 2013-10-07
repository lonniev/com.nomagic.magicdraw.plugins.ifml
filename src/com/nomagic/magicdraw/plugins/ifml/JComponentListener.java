package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.jmi.UML2MetamodelConstants;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartPropertyChangeEvent;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class JComponentListener implements PropertyChangeListener
{
	protected String mEditedProperty;

	protected Element mElement;

	protected Element getElement(PropertyChangeEvent evt)
	{
		if (evt instanceof SmartPropertyChangeEvent)
		{
			return ((SmartPropertyChangeEvent) evt).getTopSource();
		}
		return (Element) evt.getSource();
	}

    public static Collection<JComponent> getElementComponents(Element e)
	{
		Collection<JComponent> components = new ArrayList<JComponent>();

        Project project = Project.getProject(e);
        JComponentManager instance = JComponentManager.getInstance(project);
        for (PresentationElement presentationElement : project.getSymbolElementMap().getAllPresentationElements(e))
		{
            if (instance.hasComponentFor(presentationElement))
			{
				JComponent component;
				try
				{
					component = instance.getComponent(presentationElement);
					components.add(component);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		}
		return (components.isEmpty() ? Collections.<JComponent>emptyList() : components);
	}	

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		mEditedProperty = evt.getPropertyName();
		mElement = getElement(evt);
		if (UML2MetamodelConstants.INSTANCE_DELETED.equals(evt.getPropertyName()))
		{
            Project project = Project.getProject(mElement);
            JComponentManager.getInstance(project).removeElement(mElement);
		}
	}

    public List<SmartListenerConfig> getSmartListenerConfigurations()
    {
        return Collections.emptyList();
    }
}
