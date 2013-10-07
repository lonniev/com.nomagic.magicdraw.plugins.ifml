package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.properties.ChoiceProperty;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.StringProperty;
import com.nomagic.magicdraw.properties.qproperties.AbstractQPropertiesExtenderConfigurator;
import com.nomagic.magicdraw.properties.qproperties.PropertyProvider;
import com.nomagic.magicdraw.properties.qproperties.QPropertiesHelper;
import com.nomagic.magicdraw.properties.ui.ObjectListProperty;
import com.nomagic.magicdraw.properties.ui.jideui.AbstractImageEditorRenderer;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.jmi.helpers.ElementImageHelper;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Quick properties configurator for UI Modeling plugin.
 *
 * @author Mindaugas Genutis
 */
class IfmlQPropertiesExtenderConfigurator extends AbstractQPropertiesExtenderConfigurator
{
    /**
     * {@inheritDoc}
     */
    @Override
	public List<Property> getCustomProperties(BaseElement element, PropertyProvider existingPropertyProvider)
    {
        List<Property> customProperties = new ArrayList<Property>();

        if (IfmlModelingHelper.isList((Element) element))
        {
            constructChoiceProperty(existingPropertyProvider, customProperties,
                    IfmlWidgetsProfile.LIST_SELECTED_VALUE_PROPERTY,
                    IfmlWidgetsProfile.LIST_VALUES_PROPERTY);
        }

        if (IfmlModelingHelper.isTabbedPane((Element) element))
        {
            constructChoiceProperty(existingPropertyProvider, customProperties,
                    IfmlWidgetsProfile.TABBEDPANE_ACTIVE_TAB_PROPERTY,
                    IfmlWidgetsProfile.TABBEDPANE_TABS_PROPERTY);
        }

        return customProperties;
    }

    /**
     * Given a property and a list property, constructs a choice property which selects
     * its possible values from a list.
     *
     * @param existingPropertyProvider provides already existing properties in properties creation context.
     * @param customProperties a list of custom properties to which created property should be added.
     * @param selectedProperty property for which value should be selected from a list.
     * @param listProperty property from which values should be selected for another property.
     */
    private void constructChoiceProperty(PropertyProvider existingPropertyProvider, List<Property> customProperties,
                                         String selectedProperty, String listProperty)
    {
        Property property = existingPropertyProvider.getProperty(selectedProperty);
        ObjectListProperty listValuesProperty =
                (ObjectListProperty) existingPropertyProvider.getProperty(listProperty);

        ChoiceProperty selectedValueProperty = createChoiceFromListProperty(property, listValuesProperty);
        if(selectedValueProperty != null)
        {
            customProperties.add(selectedValueProperty);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean willSetProperty(BaseElement element, Property property)
    {
        return doSetProperty(element, property, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void setProperty(BaseElement baseElement, Property property)
    {
        doSetProperty(baseElement, property, false);
    }

    /**
     * Sets property for an element.
     *
     * @param baseElement element for which property should be set.
     * @param property property to set.
     * @param justCheck an option to check whether setting could be performed.
     *
     * @return true if check could be performed when justCheck paramater is true, false otherwise.
     */
    private boolean doSetProperty(BaseElement baseElement, Property property, boolean justCheck)
    {
        Element element = IfmlModelingHelper.getElement(baseElement);

        if (element != null)
        {
            if (IfmlModelingHelper.isUIElement(element))
            {
                if (IfmlWidgetsProfile.ICON_ICON_PROPERTY.equals(property.getName()))
                {
                    if (IfmlProperties.CUSTOM_ICON.equals(property.getValue()))
                    {
                        if (justCheck)
                        {
                            return true;
                        }
                        else
                        {
                            File file = AbstractImageEditorRenderer.browseImageFile(null);

                            if (file != null)
                            {
                                Image image = ModelHelper.createImage(file);
                                ElementImageHelper.setCustomImageInformation(element, image);
                                image.dispose();
                            }
                        }
                    }
                }
            }

            if (IfmlModelingHelper.isList(element))
            {
                if (IfmlWidgetsProfile.LIST_SELECTED_VALUE_PROPERTY.equals(property.getName()))
                {
                    if (justCheck)
                    {
                        return true;
                    }
                    else
                    {
                        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(element);

                        StereotypesHelper.setStereotypePropertyValue(element, profile.getList(),
                                IfmlWidgetsProfile.LIST_SELECTED_VALUE_PROPERTY, property.getValue());
                    }
                }
            }

            if (IfmlModelingHelper.isTabbedPane(element))
            {
                if (IfmlWidgetsProfile.TABBEDPANE_ACTIVE_TAB_PROPERTY.equals(property.getName()))
                {
                    if (justCheck)
                    {
                        return true;
                    }
                    else
                    {
                        IfmlWidgetsProfile profile = IfmlWidgetsProfile.getInstance(element);

                        StereotypesHelper.setStereotypePropertyValue(element, profile.getTabbedPane(),
                                IfmlWidgetsProfile.TABBEDPANE_ACTIVE_TAB_PROPERTY, property.getValue());
                    }
                }
            }
        }

        return false;
    }

    /**
     * Given a property and a list property, constructs a choice property which selects
     * its possible values from a list.
     *
     * @param property property for which choice property should be created.
     * @param listValuesProperty property from which choice values should be retrieved.
     *
     * @return created choice property.
     */
    private ChoiceProperty createChoiceFromListProperty(Property property, ObjectListProperty listValuesProperty)
    {
        if(listValuesProperty == null)
        {
            return null;
        }
        List choiceProperties = listValuesProperty.getAsList();
        List<String> values = new ArrayList<String>();

        for (Object choiceProperty : choiceProperties)
        {
            StringProperty valueProperty = (StringProperty) choiceProperty;
            values.add((String) valueProperty.getValue());
        }

        String name = QPropertiesHelper.getRealPropertyName(property.getID());

        ChoiceProperty selectedValueProperty = new ChoiceProperty(
                name,
                property.getValue(), values);
        selectedValueProperty.setValuesTranslatable(false);
        selectedValueProperty.setGroup(property.getGroup());
        selectedValueProperty.setResourceProvider(listValuesProperty.getResourceProvider());

        return selectedValueProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public SmartListenerConfig getSmartListenerConfig()
    {
        return null;
    }
}
