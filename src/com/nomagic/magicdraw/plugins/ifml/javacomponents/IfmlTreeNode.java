package com.nomagic.magicdraw.plugins.ifml.javacomponents;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.ref.WeakReference;

public class IfmlTreeNode extends DefaultMutableTreeNode
{
	private String mId;

	private boolean mIconfied;

	private Icon mIcon;

	private WeakReference<Element> mElement;

	private boolean mColored;

	public IfmlTreeNode (String userObject, Element element)
	{
		super(userObject);
		setId(element.getID());
		setIconfied(false);
		setElement(element);
	}

	public String getId()
	{
		return mId;
	}

	public void setId(String id)
	{
		this.mId = id;
	}

	public boolean isIconfied()
	{
		return mIconfied;
	}

	public void setIconfied(boolean iconfied)
	{
		mIconfied = iconfied;
	}

	public Element getElement()
	{
		return mElement.get();
	}

	private void setElement(Element element)
	{
		mElement = new WeakReference<Element>(element);
	}

	public Icon getIcon()
	{
		return mIcon;
	}

	public void setIcon(Icon icon)
	{
		mIcon = icon;
	}

	public void setColored(boolean colored)
	{
		mColored = colored;
	}

	public boolean isColored()
	{
		return mColored;
	}
}
