package com.nomagic.magicdraw.plugins.ifml;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.jmi.helpers.StereotypeByProfileCach;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.util.Arrays;
import java.util.List;


public class IfmlWidgetsProfile extends StereotypeByProfileCach
{
    public static final String VERTICAL_VERTICAL_PROPERTY = "Vertical";
    public static final String TREE_EXPAND_PROPERTY = "expand";
    public static final String CELL_RELATEDCOLUMN_PROPERTY = "RelatedColumn";
    public static final String SELECTED_SELECTED_PROPERTY = "Selected";
    public static final String SCROLLPANE_HORIZONTAL_SCROLL_BAR_PROPERTY = "Horizontal Scroll Bar";
    public static final String SCROLLPANE_VERTICAL_SCROLL_BAR_PROPERTY = "Vertical Scroll Bar";
    public static final String TABBEDPANE_TABS_PROPERTY = "Tabs";
    public static final String TABBEDPANE_ACTIVE_TAB_PROPERTY = "Active Tab";
    public static final String TABBEDPANE_TAB_POSITION_PROPERTY = "Tab Position";
    public static final String PASSWORDFIELD_HIDDEN_PROPERTY = "hidden";
    public static final String ICON_ICON_PROPERTY = "Icon";
    public static final String HORIZONTAL_SCROLL_BAR_HORIZONTAL_SCROLL_BAR_PROPERTY = "Horizontal Scroll Bar";
    public static final String TABLE_COLUMN_HEADER_PROPERTY = "Column Header";
    public static final String TEXT_TEXT_PROPERTY = "Text";
    public static final String MINIMUM_VALUE_MINIMUM_VALUE_PROPERTY = "Minimum Value";
    public static final String SLIDER_KNOB_POSITION_PROPERTY = "Knob Position";
    public static final String SLIDER_INVERT_PROPERTY = "invert";
    public static final String SLIDER_SPACING_PROPERTY = "Spacing";
    public static final String SLIDER_VALUES_PROPERTY = "Values";
    public static final String INACTIVE_INACTIVE_PROPERTY = "Inactive";
    public static final String MAXIMUM_VALUE_MAXIMUM_VALUE_PROPERTY = "Maximum Value";
    public static final String TITLE_TITLE_PROPERTY = "Title";
    public static final String PROGRESSBAR_VALUE_PROPERTY = "Value";
    public static final String NODE_EXPAND_PROPERTY = "expand";
    public static final String FRAME_MINIMIZE_PROPERTY = "minimize";
    public static final String FRAME_MAXIMIZE_PROPERTY = "maximize";
    public static final String VERTICAL_SCROLL_BAR_VERTICAL_SCROLL_BAR_PROPERTY = "Vertical Scroll Bar";
    public static final String LIST_SELECTED_VALUE_PROPERTY = "Selected Value";
    public static final String LIST_VALUES_PROPERTY = "Values";
    public static final String MENUBAR_MENUS_PROPERTY = "Menus";
    public static final String GROUPBOX_TITLED_PROPERTY = "Titled";
    public static final String GROUPBOX_BORDER_TYPE_PROPERTY = "Border Type";
    //enumeration Borders literals
    public static final String BORDERS_SIMPLE_LINE_LITERAL = "Simple Line";
    public static final String BORDERS_RAISED_ETCHED_LITERAL = "Raised Etched";
    public static final String BORDERS_LOWERED_ETCHED_LITERAL = "Lowered Etched";
    public static final String BORDERS_RAISED_BEVEL_LITERAL = "Raised Bevel";
    public static final String BORDERS_LOWERED_BEVEL_LITERAL = "Lowered Bevel";
    //enumeration TabPositions literals
    public static final String TABPOSITIONS_TOP_LITERAL = "Top";
    public static final String TABPOSITIONS_BOTTOM_LITERAL = "Bottom";
    public static final String TABPOSITIONS_RIGHT_LITERAL = "Right";
    public static final String TABPOSITIONS_LEFT_LITERAL = "Left";
    //enumeration ScrollBarPolicy literals
    public static final String SCROLLBARPOLICY_ALWAYS_LITERAL = "Always";
    public static final String SCROLLBARPOLICY_AS_NEEDED_LITERAL = "As needed";
    public static final String SCROLLBARPOLICY_NEVER_LITERAL = "Never";
    //enumeration ScrollPaneScrollBarPolicy literals
    public static final String SCROLLPANESCROLLBARPOLICY_ALWAYS_LITERAL = "Always";
    public static final String SCROLLPANESCROLLBARPOLICY_NEVER_LITERAL = "Never";
    //enumeration Icons literals
    public static final String ICONS_CUSTOM_LITERAL = "custom";
    public static final String ICONS_CLOSE_LITERAL = "close";
    public static final String ICONS_COPY_LITERAL = "copy";
    public static final String ICONS_CUT_LITERAL = "cut";
    public static final String ICONS_DELETE_LITERAL = "delete";
    public static final String ICONS_NEW_LITERAL = "new";
    public static final String ICONS_OPEN_LITERAL = "open";
    public static final String ICONS_PASTE_LITERAL = "paste";
    public static final String ICONS_PRINT_LITERAL = "print";
    public static final String ICONS_REDO_LITERAL = "redo";
    public static final String ICONS_SAVE_LITERAL = "save";
    public static final String ICONS_SEARCH_LITERAL = "search";
    public static final String ICONS_UNDO_LITERAL = "undo";
    public static final String ICONS_ZOOM_IN_LITERAL = "zoom in";
    public static final String ICONS_ZOOM_OUT_LITERAL = "zoom out";
    //stereotype Vertical and its tags
    private static final String VERTICAL_STEREOTYPE = "Vertical";
    //stereotype TextArea and its tags
    private static final String TEXTAREA_STEREOTYPE = "TextArea";
    //stereotype ScrollBar and its tags
    private static final String SCROLLBAR_STEREOTYPE = "ScrollBar";
    //stereotype Tree and its tags
    private static final String TREE_STEREOTYPE = "Tree";
    //stereotype Cell and its tags
    private static final String CELL_STEREOTYPE = "Cell";
    //stereotype Column and its tags
    private static final String COLUMN_STEREOTYPE = "Column";
    //stereotype ToolBar and its tags
    private static final String TOOLBAR_STEREOTYPE = "ToolBar";
    //stereotype Selected and its tags
    private static final String SELECTED_STEREOTYPE = "Selected";
    //stereotype ScrollPane and its tags
    private static final String SCROLLPANE_STEREOTYPE = "ScrollPane";
    //stereotype RadioButton and its tags
    private static final String RADIOBUTTON_STEREOTYPE = "RadioButton";
    //stereotype TabbedPane and its tags
    private static final String TABBEDPANE_STEREOTYPE = "TabbedPane";
    //stereotype CheckBox and its tags
    private static final String CHECKBOX_STEREOTYPE = "CheckBox";
    //stereotype ComboBox and its tags
    private static final String COMBOBOX_STEREOTYPE = "ComboBox";
    //stereotype PasswordField and its tags
    private static final String PASSWORDFIELD_STEREOTYPE = "PasswordField";
    //stereotype Icon and its tags
    private static final String ICON_STEREOTYPE = "Icon";
    //stereotype Horizontal Scroll Bar and its tags
    private static final String HORIZONTAL_SCROLL_BAR_STEREOTYPE = "Horizontal Scroll Bar";
    //stereotype TextField and its tags
    private static final String TEXTFIELD_STEREOTYPE = "TextField";
    //stereotype Label and its tags
    private static final String LABEL_STEREOTYPE = "Label";
    //stereotype Row and its tags
    private static final String ROW_STEREOTYPE = "Row";
    //stereotype Table and its tags
    private static final String TABLE_STEREOTYPE = "Table";
    //stereotype Text and its tags
    private static final String TEXT_STEREOTYPE = "Text";
    //stereotype Minimum Value and its tags
    private static final String MINIMUM_VALUE_STEREOTYPE = "Minimum Value";
    //stereotype Slider and its tags
    private static final String SLIDER_STEREOTYPE = "Slider";
    //stereotype Inactive and its tags
    private static final String INACTIVE_STEREOTYPE = "Inactive";
    //stereotype Button and its tags
    private static final String BUTTON_STEREOTYPE = "Button";
    //stereotype Maximum Value and its tags
    private static final String MAXIMUM_VALUE_STEREOTYPE = "Maximum Value";
    //stereotype Panel and its tags
    private static final String PANEL_STEREOTYPE = "Panel";
    //stereotype Columns and its tags
    private static final String COLUMNS_STEREOTYPE = "Columns";
    //stereotype Title and its tags
    private static final String TITLE_STEREOTYPE = "Title";
    //stereotype ProgressBar and its tags
    private static final String PROGRESSBAR_STEREOTYPE = "ProgressBar";
    //stereotype Separator and its tags
    private static final String SEPARATOR_STEREOTYPE = "Separator";
    //stereotype Spinner and its tags
    private static final String SPINNER_STEREOTYPE = "Spinner";
    //stereotype Node and its tags
    private static final String NODE_STEREOTYPE = "Node";
    //stereotype Frame and its tags
    private static final String FRAME_STEREOTYPE = "Frame";
    //stereotype Vertical Scroll Bar and its tags
    private static final String VERTICAL_SCROLL_BAR_STEREOTYPE = "Vertical Scroll Bar";
    //stereotype List and its tags
    private static final String LIST_STEREOTYPE = "List";
    //stereotype MenuBar and its tags
    private static final String MENUBAR_STEREOTYPE = "MenuBar";
    //stereotype Leaf and its tags
    private static final String LEAF_STEREOTYPE = "Leaf";
    //stereotype Hyperlink and its tags
    private static final String HYPERLINK_STEREOTYPE = "Hyperlink";
    //stereotype GroupBox and its tags
    private static final String GROUPBOX_STEREOTYPE = "GroupBox";
    private static final String PROFILE_NAME = "IFML Widgets Profile";

    private List<Stereotype> mComponentsWithEditableNameLabel = Lists.newArrayList();
    private List<Stereotype> mAllUIComponents = Lists.newArrayList();
    private List<Stereotype> mAllStereotypes = Lists.newArrayList();

    public IfmlWidgetsProfile (Project prj)
    {
        super(prj, PROFILE_NAME);
    }

    public static IfmlWidgetsProfile getInstance (BaseElement element)
    {
        return getInstance(Project.getProject(element));
    }

    public static IfmlWidgetsProfile getInstance (Project prj)
    {
        Optional<IfmlWidgetsProfile> instance = Optional.fromNullable(_getInstance(IfmlWidgetsProfile.class, prj));

        if ( !instance.isPresent() )
        {
            return new IfmlWidgetsProfile(prj);
        }
        else
        {
            return instance.get();
        }
    }

    private Optional<Stereotype> findStereotypeInProfileByName( String name )
    {
        final Optional<Stereotype> stereo = Optional.fromNullable(getStereotype(name));

        if (!stereo.isPresent())
        {
            Application.getInstance().getGUILog().log("Did not find expected Stereotype <<" + name +
                    ">> for Profile '" + PROFILE_NAME);
        }

        return stereo;
    }

    public Stereotype getVertical ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(VERTICAL_STEREOTYPE).orNull();
    }

    public Stereotype getTextArea ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TEXTAREA_STEREOTYPE).orNull();
    }

    public Stereotype getScrollBar ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(SCROLLBAR_STEREOTYPE).orNull();
    }

    public Stereotype getTree ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TREE_STEREOTYPE).orNull();
    }

    public Stereotype getCell ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(CELL_STEREOTYPE).orNull();
    }

    public Stereotype getColumn ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(COLUMN_STEREOTYPE).orNull();
    }

    public Stereotype getToolBar ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TOOLBAR_STEREOTYPE).orNull();
    }

    public Stereotype getSelected ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(SELECTED_STEREOTYPE).orNull();
    }

    public Stereotype getScrollPane ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(SCROLLPANE_STEREOTYPE).orNull();
    }

    public Stereotype getRadioButton ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(RADIOBUTTON_STEREOTYPE).orNull();
    }

    public Stereotype getTabbedPane ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TABBEDPANE_STEREOTYPE).orNull();
    }

    public Stereotype getCheckBox ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(CHECKBOX_STEREOTYPE).orNull();
    }

    public Stereotype getComboBox ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(COMBOBOX_STEREOTYPE).orNull();
    }

    public Stereotype getPasswordField ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(PASSWORDFIELD_STEREOTYPE).orNull();
    }

    public Stereotype getIcon ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(ICON_STEREOTYPE).orNull();
    }

    public Stereotype getHorizontalScrollBar ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(HORIZONTAL_SCROLL_BAR_STEREOTYPE).orNull();
    }

    public Stereotype getTextField ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TEXTFIELD_STEREOTYPE).orNull();
    }

    public Stereotype getLabel ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(LABEL_STEREOTYPE).orNull();
    }

    public Stereotype getRow ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(ROW_STEREOTYPE).orNull();
    }

    public Stereotype getTable ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TABLE_STEREOTYPE).orNull();
    }

    public Stereotype getText ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TEXT_STEREOTYPE).orNull();
    }

    public Stereotype getMinimumValue ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(MINIMUM_VALUE_STEREOTYPE).orNull();
    }

    public Stereotype getSlider ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(SLIDER_STEREOTYPE).orNull();
    }

    public Stereotype getInactive ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(INACTIVE_STEREOTYPE).orNull();
    }

    public Stereotype getButton ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(BUTTON_STEREOTYPE).orNull();
    }

    public Stereotype getMaximumValue ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(MAXIMUM_VALUE_STEREOTYPE).orNull();
    }

    public Stereotype getPanel ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(PANEL_STEREOTYPE).orNull();
    }

    public Stereotype getColumns ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(COLUMNS_STEREOTYPE).orNull();
    }

    public Stereotype getTitle ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(TITLE_STEREOTYPE).orNull();
    }

    public Stereotype getProgressBar ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(PROGRESSBAR_STEREOTYPE).orNull();
    }

    public Stereotype getSeparator ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(SEPARATOR_STEREOTYPE).orNull();
    }

    public Stereotype getSpinner ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(SPINNER_STEREOTYPE).orNull();
    }

    public Stereotype getNode ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(NODE_STEREOTYPE).orNull();
    }

    public Stereotype getFrame ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(FRAME_STEREOTYPE).orNull();
    }

    public Stereotype getVerticalScrollBar ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(VERTICAL_SCROLL_BAR_STEREOTYPE).orNull();
    }

    public Stereotype getList ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(LIST_STEREOTYPE).orNull();
    }

    public Stereotype getMenuBar ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(MENUBAR_STEREOTYPE).orNull();
    }

    public Stereotype getLeaf ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(LEAF_STEREOTYPE).orNull();
    }

    public Stereotype getHyperlink ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(HYPERLINK_STEREOTYPE).orNull();
    }

    public Stereotype getGroupBox ()
    {
        // look for the requested stereotype by its name, returning null if not found
        return findStereotypeInProfileByName(GROUPBOX_STEREOTYPE).orNull();
    }

    public List<Stereotype> getComponentsWithEditableNameLabel ()
    {
        if (mComponentsWithEditableNameLabel.isEmpty())
        {
            mComponentsWithEditableNameLabel = Arrays.asList(getButton(), getCheckBox(), getComboBox(),
                    getGroupBox(), getFrame(), getHyperlink(), getLabel(), getPasswordField(), getRadioButton(),
                    getSpinner(), getTextArea(), getTextField(), getTree());
        }
        return mComponentsWithEditableNameLabel;
    }

    public List<Stereotype> getAllUIComponents ()
    {
        if (mAllUIComponents.isEmpty())
        {
            mAllUIComponents = Arrays.asList(getButton(), getCheckBox(), getComboBox(), getFrame(), getGroupBox(),
                    getHyperlink(), getLabel(), getList(), getMenuBar(), getPanel(), getPasswordField(),
                    getProgressBar(), getRadioButton(), getScrollBar(), getScrollPane(), getSeparator(), getSlider(),
                    getSpinner(), getTabbedPane(), getTable(), getTextArea(), getTextField(), getToolBar(), getTree());
        }
        return mAllUIComponents;
    }

    public List<Stereotype> getAllStereotypes ()
    {
        if (mAllStereotypes.isEmpty())
        {
            mAllStereotypes = Arrays.asList(getTextField(), getSeparator(), getTabbedPane(), getList(),
                    getRadioButton(), getCell(), getPanel(), getMenuBar(), getSelected(), getComboBox(), getLabel(),
                    getSlider(), getVerticalScrollBar(), getLeaf(), getText(), getScrollPane(), getInactive(),
                    getNode(), getCheckBox(), getTitle(), getTree(), getHyperlink(), getSpinner(), getVertical(),
                    getMaximumValue(), getIcon(), getToolBar(), getColumn(), getHorizontalScrollBar(),
                    getPasswordField(), getProgressBar(), getTextArea(), getTable(), getGroupBox(),
                    getMinimumValue(), getColumns(), getButton(), getRow(), getFrame(), getScrollBar());
        }
        return mAllStereotypes;
    }

    @Override
    protected void clearCach ()
    {
        mAllStereotypes.clear();
        mAllUIComponents.clear();
        mComponentsWithEditableNameLabel.clear();
    }
}

