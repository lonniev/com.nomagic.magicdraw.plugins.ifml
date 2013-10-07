package com.nomagic.magicdraw.plugins.ifml.javacomponents;

import java.awt.*;

public class IfmlContainer extends Container
{
    private boolean mShowing = false;

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
