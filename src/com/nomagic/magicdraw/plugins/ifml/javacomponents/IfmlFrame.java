package com.nomagic.magicdraw.plugins.ifml.javacomponents;

import javax.swing.*;

public class IfmlFrame extends JInternalFrame
{
	private boolean mShowing = false;

	public IfmlFrame (String name, boolean b, boolean c)
	{
		super(name, b, c);
	}

	@Override
	public boolean isShowing()
	{
		return mShowing;
	}

	public boolean getRealShowing()
	{
		return super.isShowing();
	}

	public void setShowing(boolean showing)
	{
		mShowing = showing;
	}
}
