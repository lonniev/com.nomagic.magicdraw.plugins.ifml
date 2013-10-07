package com.nomagic.magicdraw.plugins.ifml.converters;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.IfmlModelingHelper;
import com.nomagic.magicdraw.plugins.ifml.IfmlWidgetsProfile;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.SymbolElementMap;
import com.nomagic.magicdraw.uml.symbols.shapes.ClassifierView;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.util.List;

/**
 * Converts UI Modeling symbols from older than 16.6 version to 16.6 version.
 *
 * @author Mindaugas Genutis
 */
public class ConverterTo16_6Version
{
    /**
     * Performs conversion of the project.
     *
     * @param project project to convert.
     */
    public void convert(Project project)
    {
        List<Element> elements = IfmlModelingHelper.getAllUIElements(project);

        for (Element element : elements)
        {
            if (IfmlModelingHelper.hasTextProperty(element)
                    || IfmlModelingHelper.hasTitleProperty(element))
            {
                IfmlModelingHelper.setText(element, ((NamedElement)element).getName());
            }
        }
    }

    /**
     * Turns off use fill color property for UI Modeling symbols.
     *
     * @param project project in which to turn off this symbol property.
     */
    public void changeUseFillColorProperty(Project project)
    {
        SymbolElementMap symbol2ElementMap = project.getSymbolElementMap();
        List<Element> elements = IfmlModelingHelper.getAllUIElements(project);

        for (Element element : elements)
        {
            List<PresentationElement> views = symbol2ElementMap.getAllPresentationElements(element);

            for (PresentationElement view : views)
            {
                view.setUseFillColor(false);                
            }
        }
    }

    /**
     * Hide tags for UI Modeling symbols.
     *
     * @param project project in which to hide tags for UI Modeling symbols.
     */
    public void hideTags(Project project)
    {
        SymbolElementMap symbol2ElementMap = project.getSymbolElementMap();
        List<Element> elements = IfmlModelingHelper.getAllUIElements(project);

        for (Element element : elements)
        {
            List<PresentationElement> views = symbol2ElementMap.getAllPresentationElements(element);

            for (PresentationElement view : views)
            {
                if (view instanceof ClassifierView)
                {
                    ((ClassifierView) view).setTaggedValuesDisplayMode(ClassifierView.DO_NOT_DISPLAY);
                }
            }
        }
    }

    public void convertProjectTo16_6(Project project)
    {
        convertButtons(project);
    }

    private void convertButtons(Project project)
    {
        List<Element> elements = IfmlModelingHelper.getAllUIElements(project);

        for (Element element : elements)
        {
            if (IfmlModelingHelper.isButton(element))
            {
                String predefinedButtonType = IfmlModelingHelper.getPredefinedButtonType(element);

                if (predefinedButtonType != null)
                {
                    Stereotype stereotype = IfmlWidgetsProfile.getInstance(Project.getProject(element)).getIcon();

                    if (IfmlModelingHelper.isAvailableIcon(predefinedButtonType))
                    {
                        StereotypesHelper.setStereotypePropertyValue(element,
                            stereotype, IfmlWidgetsProfile.ICON_ICON_PROPERTY, predefinedButtonType);
                    }

                    if (predefinedButtonType.equals(ModelHelper.getComment(element)))
                    {
                        ModelHelper.setComment(element, "");
                    }
                }
            }
        }
    }
}