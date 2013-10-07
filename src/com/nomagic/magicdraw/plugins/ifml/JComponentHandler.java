package com.nomagic.magicdraw.plugins.ifml;

/**
 * @author Mindaugas Genutis
 */
public interface JComponentHandler
{
	Object handleButton();

	Object handleCheckBox();

	Object handleComboBox();

	Object handleHyperlink();

	Object handleLabel();

	Object handleMenuBar();

	Object handlePasswordField();

	Object handleProgressBar();

	Object handleRadioButton();

	Object handleScrollBar();

	Object handleSeparator();

	Object handleSlider();

	Object handleSpinner();

	Object handleTextArea();

	Object handleTextField();

	Object handleFrame();

	Object handleGroupBox();

	Object handleList();

	Object handlePanel();

	Object handleScrollPane();

	Object handleTabbedPane();

	Object handleTable();

	Object handleToolBar();

	Object handleTree();
}