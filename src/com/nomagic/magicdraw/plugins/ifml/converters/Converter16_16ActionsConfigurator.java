package com.nomagic.magicdraw.plugins.ifml.converters;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.ActionsGroups;
import com.nomagic.magicdraw.actions.ActionsID;
import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.ApplicationEnvironment;
import com.nomagic.magicdraw.core.Project;

import java.awt.event.ActionEvent;

/**
 * Actions configurator for UI modeling conversion from older thank 16.6 versions.
 *
 * @author Mindaugas Genutis
*/
public class Converter16_16ActionsConfigurator implements AMConfigurator
{
    /**
     * Action for converting UI Modeling project.
     */
    private String CONVERT_ACTION_ID = "CONVERT_UI_MODELING_MODEL";

    /**
     * UI Modeling project conversion action name.
     */
    private String CONVERT_ACTION_NAME = "Convert UI Modeling Project from lower than MagicDraw 16.6 versions";

    /**
     * Action for converting UI Modeling project use fill color symbol property.
     */
    private String CHANGE_FILL_COLOR_ACTION_ID = "CHANGE_FILL_COLOR_ACTION_ID";

    /**
     * UI Modeling project conversion action name.
     */
    private String CHANGE_FILL_COLOR_ACTION_NAME = "Change use fill color to false for UI Modeling symbols";

    /**
     * Action for converting UI Modeling project show tagged values symbol property.
     */
    private String HIDE_TAGS_ACTION_ID = "HIDE_TAGS_ACTION_ID";

    /**
     * UI Modeling project conversion action name.
     */
    private String HIDE_TAGS_ACTION_NAME = "Hide tags for UI Modeling symbols";

    @Override
	public void configure(ActionsManager mngr)
    {
        if (ApplicationEnvironment.isDeveloper())
        {
            ActionsCategory toolsCategory = (ActionsCategory) mngr.getActionFor(ActionsID.TOOLS);

            if (toolsCategory != null)
            {
                MDAction convertAction = new MDAction(CONVERT_ACTION_ID, CONVERT_ACTION_NAME, 0,
                        ActionsGroups.PROJECT_OPENED_RELATED)
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        super.actionPerformed(e);

                        Project activeProject = Application.getInstance().getProjectsManager().getActiveProject();

                        if (activeProject != null)
                        {
                            new ConverterTo16_6Version().convert(activeProject);
                        }
                    }
                };
                toolsCategory.addAction(convertAction);

                MDAction fillColorAction = new MDAction(CHANGE_FILL_COLOR_ACTION_ID,
                        CHANGE_FILL_COLOR_ACTION_NAME, 0, ActionsGroups.PROJECT_OPENED_RELATED)
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        super.actionPerformed(e);

                        Application.getInstance().getActionsManager().getActionsExecuter().loadNotLoadedDiagrams();
                        Project activeProject = Application.getInstance().getProjectsManager().getActiveProject();

                        if (activeProject != null)
                        {
                            new ConverterTo16_6Version().changeUseFillColorProperty(activeProject);
                        }
                    }
                };

                toolsCategory.addAction(fillColorAction);

                MDAction hideTagsAction = new MDAction(HIDE_TAGS_ACTION_ID,
                        HIDE_TAGS_ACTION_NAME, 0, ActionsGroups.PROJECT_OPENED_RELATED)
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        super.actionPerformed(e);

                        Application.getInstance().getActionsManager().getActionsExecuter().loadNotLoadedDiagrams();
                        Project activeProject = Application.getInstance().getProjectsManager().getActiveProject();

                        if (activeProject != null)
                        {
                            new ConverterTo16_6Version().hideTags(activeProject);
                        }
                    }
                };

                toolsCategory.addAction(hideTagsAction);
            }
        }
    }

    @Override
	public int getPriority()
    {
        return AMConfigurator.LOW_PRIORITY;
    }
}
