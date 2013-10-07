package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsManager;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.actions.ActionsID;
import com.nomagic.magicdraw.actions.ConfiguratorWithPriority;
import com.nomagic.magicdraw.actions.DiagramContextAMConfigurator;
import com.nomagic.magicdraw.evaluation.EvaluationConfigurator;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.plugins.ifml.synchronizers.TableSynchronizer;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.PropertyID;
import com.nomagic.magicdraw.properties.qproperties.QPropertiesExtender;
import com.nomagic.magicdraw.ui.browser.Node;
import com.nomagic.magicdraw.ui.browser.TreeEditorFactory;
import com.nomagic.magicdraw.ui.diagrams.CustomDiagramAction;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.uml.DiagramType;
import com.nomagic.magicdraw.uml.RepresentationTextCreator;
import com.nomagic.magicdraw.uml.symbols.*;
import com.nomagic.magicdraw.uml.symbols.shapes.DiagramOverviewCompartmentView;
import com.nomagic.magicdraw.utils.MDLog;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.synchronizer.ModelSynchronizerConfigurator;
import org.apache.log4j.Logger;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * MagicDrawIfmlPlugin
 *
 * $Revision$ $Date$
 * @author Lonnie VanZandt
 *
 * This plugin registers the classes for the plugin offering Model Transformation for MD to XMI 2.4.1.
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */

public class MagicDrawIfmlPlugin extends Plugin
{
    public final static String MAGICDRAW_IFML_PLUGIN_ID = "com.nomagic.magicdraw.plugins.ifml";

    @Override
    public boolean close ()
    {
        return true;
    }

    @Override
    public void init ()
    {

        Logger pluginLogger = MDLog.getGUILog();

        // Called only after isSupported when isSupported returns true
        EvaluationConfigurator.getInstance().registerBinaryImplementers(MagicDrawIfmlPlugin.class.getClassLoader());

        pluginLogger.info("MagicDraw IFML Plugin. Compiled at @@@compile-timestamp@@@ (debug=@@@debug-flag@@@).");

        DiagramOverviewCompartmentView.addBufferedDiagramType(IfmlProperties.UI_DIAGRAM_TYPE);
        SymbolRendererManager.getManager().addProvider(new IfmlRendererProvider());
        SymbolPropertiesConfiguratorManager.addConfigurator(new UISymbolPropertiesConfigurator());
        ActionsConfiguratorsManager actionsConfiguratorsManager = ActionsConfiguratorsManager.getInstance();

        IfmlDiagramDiagramContextMenuConfigurator contextMenuConfig = new IfmlDiagramDiagramContextMenuConfigurator();

        ModelSynchronizerConfigurator.getInstance().addModelSynchronizer(new TableSynchronizer());

        actionsConfiguratorsManager.addBaseDiagramContextConfigurator(DiagramType.UML_STATIC_DIAGRAM,
                contextMenuConfig);

        DiagramObjectViewContainerFactory.getInstance().addCreator(IfmlProperties.UI_DIAGRAM_TYPE,
                new DiagramObjectViewContainerFactory.Creator()
        {
            @Override
            public DiagramObjectViewContainer create (DiagramType diagramType)
            {
                return new IfmlDiagramObjectViewContainer();
            }
        });

        actionsConfiguratorsManager.addDiagramToolbarConfigurator(IfmlProperties.UI_DIAGRAM_TYPE, new AMConfigurator()
        {

            @Override
            public void configure (ActionsManager mngr)
            {
                JComponentElementConfigurator configurator = new JComponentElementConfigurator();
                List<NMAction> allActions = mngr.getAllActions();
                for (NMAction action : allActions)
                {
                    if (action instanceof CustomDiagramAction)
                    {
                        CustomDiagramAction cAction = (CustomDiagramAction) action;
                        cAction.setPresentationElementConfigurator(configurator);
                    }
                }
            }

            @Override
            public int getPriority ()
            {
                return AMConfigurator.LOW_PRIORITY;
            }
        });

        //display text in UI, not element name
        RepresentationTextCreator.addProvider(new RepresentationTextCreator.RepresentationTextProvider()
        {
            @Override
            public String getRepresentedText (BaseElement element, boolean addColor, boolean fullSignature)
            {
                return IfmlModelingHelper.getText((Element) element);
            }

            @Override
            public SmartListenerConfig createSmartListenerConfig (Element element, boolean browserConfiguration)
            {
                return SmartListenerConfig.STEREOTYPE_AND_TAGS_CONFIG;
            }

            @Override
            public boolean accept (BaseElement element)
            {
                return element instanceof Element && IfmlModelingHelper.isUISymbolWithText((Element) element);
            }
        });

        //display text in UI, not element name
        TreeEditorFactory.addTreeEditorProvider(new TreeEditorFactory.TreeEditorProvider()
        {
            @Override
            public TreeEditorFactory.TreeEditor createEditor (Node node)
            {
                final Object o = node.getUserObject();
                if (o instanceof Element && IfmlModelingHelper.isUISymbolWithText((Element) o))
                {
                    return new TreeEditorFactory.TreeEditor()
                    {
                        @Override
                        public String getText (TreeNode node)
                        {
                            return IfmlModelingHelper.getText((Element) o);
                        }

                        @Override
                        public void setText (TreeNode node, String text)
                        {
                            IfmlModelingHelper.setText((Element) o, text);
                        }

                        @Override
                        public boolean isSpellCheckable ()
                        {
                            return true;
                        }
                    };
                }
                return null;
            }

            @Override
            public int getPriority ()
            {
                return ConfiguratorWithPriority.LOW_PRIORITY;
            }
        });

        configureMainMenu();
        configureQProperties();
    }

    /**
     * Configures quick properties for this plugin.
     */
    private void configureQProperties ()
    {
        QPropertiesExtender.addQPropertiesExtenderConfigurator(new IfmlQPropertiesExtenderConfigurator());
    }

    /**
     * Adds necessary UI Modeling plugin actions to the MagicDraw main menu.
     */
    private void configureMainMenu ()
    {
//        ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
//        manager.addMainMenuConfigurator(new Converter16_16ActionsConfigurator());
    }

    @Override
    public boolean isSupported ()
    {
        return true;
    }

    /**
     * Symbol properties configurator
     */
    private static class UISymbolPropertiesConfigurator extends SymbolPropertiesConfigurator
    {
        private static List<String> PROPERTIES = Arrays.asList(PropertyID.TEXT_COLOR, PropertyID.AUTOSIZE,
                PropertyID.FILL_COLOR, PropertyID.USE_FILL_COLOR, PropertyID.FONT,
                PropertyID.USE_FIXED_CONNECTION_POINTS);
        private static List<String> SEPARATOR_PROPERTIES = new ArrayList<String>(PROPERTIES);

        {
            SEPARATOR_PROPERTIES.add(PropertyID.PEN_COLOR);
        }

        @Override
        public boolean isVisible (PresentationElement element, Property property)
        {
            return !IfmlModelingHelper.isUISymbol(element) || element.getElement() != null && (IfmlModelingHelper
                    .isSeparator(element.getElement()) ? SEPARATOR_PROPERTIES.contains(property.getID()) : PROPERTIES
                    .contains(property.getID()));
        }

        @Override
        public int getPriority ()
        {
            return SymbolPropertiesConfigurator.LOW_PRIORITY;
        }
    }

    public class IfmlDiagramDiagramContextMenuConfigurator implements DiagramContextAMConfigurator
    {
        @Override
        public void configure (ActionsManager mngr, DiagramPresentationElement diagram,
                               PresentationElement selected[], PresentationElement requestor)
        {
            if (IfmlModelingHelper.isUISymbol(requestor))
            {
                mngr.removeAction(ActionsID.INSERT_NEW_ATTRIBUTE);
                mngr.removeAction(ActionsID.INSERT_NEW_OPERATION);
                mngr.removeAction(ActionsID.INSERT_NEW_RECEPTION);
                mngr.removeAction(ActionsID.INSERT_NEW_PORT);

                mngr.removeAction(ActionsID.MAKE_SUB_TREE);
                mngr.removeAction(ActionsID.MAKE_GENERALIZATION_SET_TREE);
                mngr.removeAction(ActionsID.TOOLS_CATEGORY);
                mngr.removeAction(ActionsID.REFACTOR_CATEGORY);

                mngr.removeAction(ActionsID.DISPLAY_PARTS);
                mngr.removeAction(ActionsID.DISPLAY_PORTS);

                mngr.removeAction(ActionsID.EDIT_COMPARTMENT);
                mngr.removeAction(ActionsID.EDIT_STEREOTYPE);
                mngr.removeAction(ActionsID.SELECT_IN_HIERARCHY_TREE);

                mngr.removeAction(ActionsID.PRESENTATION_OPTIONS);
            }
        }

        @Override
        public int getPriority ()
        {
            return AMConfigurator.LOW_PRIORITY;
        }
    }
}

