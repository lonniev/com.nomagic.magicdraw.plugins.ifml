package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.evaluation.EvaluationConfigurator;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.utils.MDLog;
import org.apache.log4j.Logger;

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
    public boolean isSupported ()
    {
        return true;
    }

    @Override
    public void init ()
    {
        Logger pluginLogger = MDLog.getGUILog();

        // Called only after isSupported when isSupported returns true
        EvaluationConfigurator.getInstance().registerBinaryImplementers(MagicDrawIfmlPlugin.class.getClassLoader());

        pluginLogger.info("MagicDraw IFML Plugin. Compiled at 20130929-1514 " + "" +
                "(debug=enabled).");

    }

    @Override
    public boolean close ()
    {
        return true;
    }

}
