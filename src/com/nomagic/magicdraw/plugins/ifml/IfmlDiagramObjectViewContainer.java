/**
 * $Id: DependencyMatrixDiagramObjectViewContainer.java 51608 2007-09-27 17:12:00Z manbal $
 *
 * Copyright (c) 2005 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.ui.DiagramWindowPanel;
import com.nomagic.magicdraw.uml.symbols.DiagramObjectViewContainer;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PaintableComponent;

import javax.swing.*;

/**
 * UI modeling container initializes diagram panel for image exporting
 * when diagram isn't open.
 *
 * @author Mindaugas Genutis
 * @version $Revision: 51608 $, $Date: 2007-09-27 20:12:00 +0300 (Thu, 27 Sep 2007) $
 */
public class IfmlDiagramObjectViewContainer extends DiagramObjectViewContainer
{
    @Override
    public PaintableComponent getPaintableComponent()
    {
        DiagramPresentationElement dpe = getDiagramPresentationElement();
        DiagramWindowPanel panel = dpe.getPanel();

        if (panel == null)
        {
            final JDialog dialog = new JDialog();
            panel = new DiagramWindowPanel(dpe.getDiagramType().getType(), false, false)
            {
                @Override public void dispose()
                {
                    super.dispose();
                    dialog.dispose();
                }
            };
            panel.setDiagramView(dpe);

            dialog.add(panel);
            dialog.pack();

            panel.setVisible(true);
            panel.validate();
            panel.doLayout();
        }

        return this;
    }
    @Override public void disposePaintable()
    {
    }
}