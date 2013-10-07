package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlFrame;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.ShapeElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 *
 * @author Mindaugas Genutis
 */
public class JComponentUpdater implements JComponentHandler
{	
    private JComponent mComponent;

    private ShapeElement presentationElement;

    private NamedElement mElement;

    public JComponentUpdater(JComponent component, PresentationElement pElement)
    {
        mComponent = component;
        presentationElement = (ShapeElement) pElement;
        mElement = (NamedElement)pElement.getElement();
    }

    @Override
	public Object handleButton()
    {
		JButton button = (JButton) mComponent;
        button.setText(IfmlModelingHelper.getText(mElement));
		IfmlModelingHelper.setComponentIcon(mElement, button);
		IfmlModelingHelper.setComponentEnabled(mElement, button);
		IfmlModelingHelper.setButtonSelected(mElement, button);
		return button;
	}       

	@Override
	public Object handleCheckBox()
	{
		JCheckBox checkBox = (JCheckBox) mComponent;
        checkBox.setText(IfmlModelingHelper.getText(mElement));
		checkBox.setOpaque(false);
		IfmlModelingHelper.setComponentEnabled(mElement, checkBox);
		IfmlModelingHelper.setButtonSelected(mElement, checkBox);
		return checkBox;
	}

	@Override
	public Object handleComboBox()
	{
		JComboBox comboBox = (JComboBox) mComponent;
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement(IfmlModelingHelper.getText(mElement));
		comboBox.setModel(model);
		IfmlModelingHelper.setComponentEnabled(mElement, comboBox);
		comboBox.setEditable(false);
		return comboBox;
	}

	@Override
	public Object handleHyperlink()
	{
		JLabel hyperlink = (JLabel) mComponent;

        hyperlink.setText("<html><U>" + IfmlModelingHelper.getText(mElement)+ "</html>");
		IfmlModelingHelper.setComponentIcon(mElement, hyperlink);
		IfmlModelingHelper.setComponentEnabled(mElement, hyperlink);
		hyperlink.setForeground(IfmlModelingHelper.isInactive(mElement)
                ? (Color) UIManager.get("Label.disabledForeground") : Color.BLUE);

        return hyperlink;
	}

	@Override
	public Object handleLabel()
	{
		JLabel label = (JLabel) mComponent;

        label.setText(IfmlModelingHelper.getText(mElement));
		IfmlModelingHelper.setComponentIcon(mElement, label);
		IfmlModelingHelper.setComponentEnabled(mElement, label);

		return label;
	}

	@Override
	public Object handleMenuBar()
	{
		JMenuBar menuBar = (JMenuBar) mComponent;
        menuBar.removeAll();

        List<String> menus = IfmlModelingHelper.getMenuBarMenus(mElement);
        for (String menuTitle : menus)
        {
            menuBar.add(new JMenu(menuTitle));
		}
		return menuBar;
	}

	@Override
	public Object handlePasswordField()
	{
		JPasswordField passwordField = (JPasswordField) mComponent;
        passwordField.setText(IfmlModelingHelper.getText(mElement));
        passwordField.setColumns(10);
		if (!IfmlModelingHelper.isPasswordFieldHidden(mElement))
		{
			passwordField.setEchoChar((char) 0);
		}
		else
		{
			passwordField.setEchoChar('*');
		}

		IfmlModelingHelper.setComponentEnabled(mElement, passwordField);
		return passwordField;
	}

	@Override
	public Object handleProgressBar()
	{
		JProgressBar progressBar = (JProgressBar) mComponent;

        int oldOrientation = progressBar.getOrientation();
        int orientation = IfmlModelingHelper.getOrientation(IfmlModelingHelper.isVertical(mElement));
        progressBar.setOrientation(orientation);
        changeSizeByOrientation(presentationElement, oldOrientation, orientation);

		progressBar.setMinimum(IfmlModelingHelper.getMinValue(mElement));
		progressBar.setMaximum(IfmlModelingHelper.getMaxValue(mElement));

		progressBar.setValue(IfmlModelingHelper.getProgressBarValue(mElement));
        progressBar.setString(Integer.toString(progressBar.getValue()));

		progressBar.setStringPainted(true);
		return progressBar;
	}

	@Override
	public Object handleRadioButton()
	{
		JRadioButton radioButton = (JRadioButton) mComponent;
        radioButton.setText(IfmlModelingHelper.getText(mElement));
		radioButton.setOpaque(false);
		IfmlModelingHelper.setComponentEnabled(mElement, radioButton);
		IfmlModelingHelper.setButtonSelected(mElement, radioButton);
		return radioButton;
	}

	@Override
	public Object handleScrollBar()
	{
		JScrollBar scrollBar = (JScrollBar) mComponent;
        int oldOrientation = scrollBar.getOrientation();
        int orientation = IfmlModelingHelper.getOrientation(IfmlModelingHelper.isVertical(mElement));
        scrollBar.setOrientation(orientation);
        changeSizeByOrientation(presentationElement, oldOrientation, orientation);

		IfmlModelingHelper.setComponentEnabled(mElement, scrollBar);
		return scrollBar;
	}

	@Override
	public Object handleSeparator()
	{
		JSeparator separator = (JSeparator) mComponent;
        int oldOrientation = separator.getOrientation();
        int orientation = IfmlModelingHelper.getOrientation(IfmlModelingHelper.isVertical(mElement));
        separator.setOrientation(orientation);
        changeSizeByOrientation(presentationElement, oldOrientation, orientation);
		return separator;
	}

    @Override
	public Object handleSlider()
    {
        JSlider slider = (JSlider) mComponent;
        int oldOrientation = slider.getOrientation();
        int orientation = IfmlModelingHelper.getOrientation(IfmlModelingHelper.isVertical(mElement));
        slider.setOrientation(orientation);
        changeSizeByOrientation(presentationElement, oldOrientation, orientation);

        slider.setMinimum(IfmlModelingHelper.getMinValue(mElement));
        slider.setMaximum(IfmlModelingHelper.getMaxValue(mElement));

        int minimum = slider.getMinimum();
        int maximum = slider.getMaximum();

        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(minimum, new JLabel(Integer.toString(minimum)));
        labelTable.put(maximum, new JLabel(Integer.toString(maximum)));
        List<String> values = IfmlModelingHelper.getSliderValues(mElement);
        //remove empty strings
        while (values.remove("")) ;

        for (String value : values)
        {
            String[] splitted = value.split("\n");

            if (splitted.length >= 1)
            {
                Integer key = Integer.valueOf(splitted[0]);
                String label = splitted.length >= 2 ? splitted[1] : key.toString();

                labelTable.put(key, new JLabel(label));
            }
        }

        //set spacing in ranges
        int spacing = IfmlModelingHelper.getSliderMajorTickSpacing(mElement);
        if (spacing <= maximum)
        {
            slider.setMajorTickSpacing(spacing);
        }
        else
        {
            slider.setMajorTickSpacing(maximum);
        }
        slider.setInverted(IfmlModelingHelper.isSliderInverted(mElement));

        //set position in ranges
        int position = IfmlModelingHelper.getSliderValue(mElement);
        if (position > maximum)
        {
            slider.setValue(maximum);
        }
        else if (position < minimum)
        {
            slider.setValue(minimum);
        }
        else
        {
            slider.setValue(position);
        }

        // labelTable.put(slider.getValue(), new JLabel(Integer.toString(slider.getValue())));

        slider.setLabelTable(labelTable);

        IfmlModelingHelper.setComponentEnabled(mElement, slider);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        return slider;
    }

	@Override
	public Object handleSpinner()
	{
        JSpinner spinner = (JSpinner) mComponent;

		List<String> values = new ArrayList<String>();
        String text = IfmlModelingHelper.getText(mElement);
        values.add(text);

        SpinnerListModel spinnerModel = new SpinnerListModel();
		spinnerModel.setList(values);
		spinner.setModel(spinnerModel);
        ((JTextField)spinner.getEditor()).setText(text);

		IfmlModelingHelper.setComponentEnabled(mElement, spinner);
		return spinner;
	}

	@Override
	public Object handleTextArea()
	{
		JScrollPane textFieldScrollPane = (JScrollPane)mComponent;
		JTextArea textArea = (JTextArea) textFieldScrollPane.getViewport().getView();
        textArea.setText(IfmlModelingHelper.getText(mElement));

        textFieldScrollPane.setHorizontalScrollBarPolicy(IfmlModelingHelper.HORIZONTAL_SCROLLBAR_POLICY.get(IfmlModelingHelper.getHorizontalScrollbarPolicy(mElement)));
        textFieldScrollPane.setVerticalScrollBarPolicy(IfmlModelingHelper.VERTICAL_SCROLLBAR_POLICY.get(IfmlModelingHelper.getVerticalScrollbarPolicy(mElement)));

		IfmlModelingHelper.setComponentEnabled(mElement, textArea);
		return textFieldScrollPane;
	}

	@Override
	public Object handleTextField()
	{
		JTextField textField = (JTextField) mComponent;
		textField.setText(IfmlModelingHelper.getText(mElement));
		IfmlModelingHelper.setComponentEnabled(mElement, textField);
		return textField;
	}

	@Override
	public Object handleFrame()
	{
		IfmlFrame frame = (IfmlFrame) mComponent;
        frame.setTitle(IfmlModelingHelper.getText(mElement));

		IfmlModelingHelper.setComponentIcon(mElement, frame);
		frame.setMaximizable(IfmlModelingHelper.isFrameMaximize(mElement));
		frame.setIconifiable(IfmlModelingHelper.isFrameMinimize(mElement));

		IfmlModelingHelper.setComponentEnabled(mElement, frame);
		return frame;
	}

    @Override
	public Object handleGroupBox()
	{
		JPanel panel = (JPanel) mComponent;
		TitledBorder titledBorder = BorderFactory.createTitledBorder(IfmlModelingHelper.getBorder(presentationElement), IfmlModelingHelper.getText(mElement));

		if (!IfmlModelingHelper.isGroupBoxNamed(mElement))
		{
			titledBorder.setTitle("");
		}
		panel.setBorder(titledBorder);
		return panel;
	}

	@Override
	public Object handleList()
	{
        JScrollPane listScrollPane = (JScrollPane) mComponent;
		JList list = (JList) listScrollPane.getViewport().getView();

        DefaultListModel model = new DefaultListModel();
        List<String> values = IfmlModelingHelper.getListValues(mElement);
        for (String value : values)
        {
            model.addElement(value);
        }
        list.setModel(model);
		list.setSelectedValue(IfmlModelingHelper.getListSelectedValue(mElement), true);

		listScrollPane.setHorizontalScrollBarPolicy(IfmlModelingHelper.HORIZONTAL_SCROLLBAR_POLICY.get(IfmlModelingHelper.getHorizontalScrollbarPolicy(mElement)));
		listScrollPane.setVerticalScrollBarPolicy(IfmlModelingHelper.VERTICAL_SCROLLBAR_POLICY.get(IfmlModelingHelper.getVerticalScrollbarPolicy(mElement)));
		IfmlModelingHelper.setComponentEnabled(mElement, list);
		return listScrollPane;
	}

    @Override
	public Object handlePanel()
	{
		return mComponent;
	}

	@Override
	public Object handleScrollPane()
	{
		JScrollPane scrollPane = (JScrollPane) mComponent;

        scrollPane.setHorizontalScrollBarPolicy(IfmlModelingHelper.HORIZONTAL_SCROLLBAR_POLICY.get(IfmlModelingHelper.getScrollPaneHorizontalScrollbarPolicy(mElement)));
        scrollPane.setVerticalScrollBarPolicy(IfmlModelingHelper.VERTICAL_SCROLLBAR_POLICY.get(IfmlModelingHelper.getScrollPanelVerticalScrollbarPolicy(mElement)));
		return scrollPane;
	}

	@Override
	public Object handleTabbedPane()
	{
		JTabbedPane tabbedPane = (JTabbedPane) mComponent;
        tabbedPane.removeAll();

		UIManager.put("TabbedPane.contentBorderInsets", new InsetsUIResource(-2, 0, 0, 0));
		tabbedPane.updateUI();

        Border border = BorderFactory.createLineBorder(Color.GRAY);
        List<String> tabs = IfmlModelingHelper.getTabbedPaneTabs(mElement);
        for (String tab : tabs)
        {
            JPanel panel = new JPanel();
            panel.setBorder(border);
            tabbedPane.addTab(tab, panel);
        }

		tabbedPane.setTabPlacement(IfmlModelingHelper.TAB_POSITIONS.get(IfmlModelingHelper.getTabbedPanePlacement(mElement)));

        int active = tabbedPane.indexOfTab(IfmlModelingHelper.getTabbedPaneActiveTab(mElement));
        if (active == -1 && tabbedPane.getTabCount() > 0)
        {
            active = 0;
        }
        tabbedPane.setSelectedIndex(active);

		return tabbedPane;
	}

    @Override
	public Object handleTable()
    {
        JScrollPane tableScrollPane = (JScrollPane) mComponent;
        JViewport header = tableScrollPane.getColumnHeader();

        if (header != null)
        {
            header.setVisible(IfmlModelingHelper.isTableColumnHeaderVisible(mElement));
        }

        tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        TableHelper.updateTableViews(IfmlModelingHelper.getTableComponent(mElement));

        return tableScrollPane;
    }

	@Override
	public Object handleToolBar()
	{
		return mComponent;
	}

    @Override
	public Object handleTree()
    {
        JScrollPane treeScrollPane = (JScrollPane) mComponent;
        JTree tree = (JTree) treeScrollPane.getViewport().getView();
        TreeHelper.updateTree(treeScrollPane, tree, mElement);

        return treeScrollPane;
    }

    private void changeSizeByOrientation(PresentationElement presentationElement, int previousOrientation, int newOrientation)
    {
        if(newOrientation != previousOrientation)
        {
            Rectangle bounds = presentationElement.getBounds();
            if(newOrientation == Adjustable.HORIZONTAL && bounds.width < bounds.height ||
               newOrientation == Adjustable.VERTICAL && bounds.width > bounds.height)
            {
                presentationElement.setSize(bounds.height, bounds.width);
            }
        }
    }

}