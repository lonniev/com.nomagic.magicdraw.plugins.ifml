package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.actions.ActionsID;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlContainer;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlFrame;
import com.nomagic.magicdraw.properties.PropertyID;
import com.nomagic.magicdraw.ui.DiagramCanvas;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.editors.TextEditor;
import com.nomagic.magicdraw.uml.symbols.manipulators.actions.CompoundManipulatorActionManager;
import com.nomagic.magicdraw.uml.symbols.manipulators.actions.ManipulatorAction;
import com.nomagic.magicdraw.uml.symbols.shapes.*;
import com.nomagic.magicdraw.utils.MDLog;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.utils.Utilities;

import javax.swing.*;
import java.awt.*;

public class JComponentRenderer extends ShapeDecorator
{
    private JComponent mComponent;

    public JComponentRenderer (JComponent component)
    {
        mComponent = component;
    }

    public static void hideHeader (PresentationElement presentationElement)
    {
        Project project = presentationElement.getProject();
        ((HeaderShapeView) presentationElement).sSetStereotypesDisplayMode(StereotypesDisplayModeOwner
                .STEREOTYPE_DISPLAY_MODE_DO_NOT_DISPLAY_STEREOTYPES);

        //hide all header symbols
        HeaderShapeView headerShapeView = (HeaderShapeView) presentationElement;
        HeaderView header = headerShapeView.getHeaderView();
        java.util.List<PresentationElement> elementList = header.getPresentationElements();
        for (PresentationElement element : elementList)
        {
            element.sSetVisible(false);
        }
        //leave name visible if needed
        TextObject nameLabel = headerShapeView.getNameLabel();
        if (StereotypesHelper.hasStereotype(presentationElement.getElement()) && StereotypesHelper.hasStereotype
                (presentationElement.getElement(), IfmlWidgetsProfile.getInstance(project)
                        .getComponentsWithEditableNameLabel()))
        {
            nameLabel.sSetVisible(true);
        }
        header.sSetSize(headerShapeView.getBounds().getSize());
    }

    @Override
    public void update (final PresentationElement element)
    {
        new JComponentHandlerAcceptor().acceptHandler(element, new JComponentUpdater(mComponent, element));
        hideHeader(element);

//        BlockingCompletionHandler.invokeAndWaitOnDispatcher(new Runnable()
//        {
//            @Override
//            public void run ()
//            {
//                new JComponentHandlerAcceptor().acceptHandler(element, new JComponentUpdater(mComponent, element));
//            }
//        });
//        hideHeader(element);
    }

    @Override
    public void silentApply (PresentationElement view)
    {
        hideHeader(view);
    }

    @Override
    public void draw (Graphics g, PresentationElement presentationElement)
    {
        ShapeElement shapeElement = (ShapeElement) presentationElement;
        //make local variable, because it may be changed during recursion
        Element element = presentationElement.getElement();
        Rectangle bounds = presentationElement.getBounds();
        Container parent = mComponent.getParent();
        if (parent == null)
        {
            System.out.println("JComponentRenderer.draw invalid component" + presentationElement + "  " + mComponent);
            return;
        }

        final DiagramCanvas canvas = (DiagramCanvas) presentationElement.getDiagramSurface();
        if (parent.getParent() == null && canvas != null)
        {
            canvas.add(parent);
        }
        mComponent.setBounds(bounds);
        setSymbolProperties(shapeElement);

        if (IfmlModelingHelper.isFrame(element))
        {
            try
            {
                ((IfmlFrame) mComponent).setSelected(mComponent.isShowing());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                g.translate(bounds.x, bounds.y);
                mComponent.print(g);
                g.translate(-bounds.x, -bounds.y);
            } catch (Exception e)
            {
                MDLog.getGeneralLog().error("Ignored exception while painting a frame in UI diagram", e);
            }
        } else
        {
            boolean realShowing = ((IfmlContainer) parent).getRealShowing();
            ((IfmlContainer) parent).setShowing(true);

            if (IfmlModelingHelper.isSeparator(element))
            {
                if (((JSeparator) mComponent).getOrientation() == Adjustable.VERTICAL)
                {
                    g.drawLine(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height);
                    mComponent.setVisible(false);
                } else
                {
                    mComponent.setVisible(true);
                }
            }

            parent.paintComponents(g);
            ((IfmlContainer) parent).setShowing(realShowing);
        }

        for (PresentationElement nestedPresentationElement : presentationElement.getPresentationElements())
        {
            if (nestedPresentationElement.hasManipulator())
            {
                nestedPresentationElement.draw(g);
            }
        }
    }

    protected void setSymbolProperties (final ShapeElement presentationElement)
    {
        Utilities.invokeAndWaitOnDispatcher(new Runnable()
        {
            @Override
            public void run ()
            {
                new JComponentHandlerAcceptor().acceptHandler(presentationElement,
                        new JComponentColorFontHandler(presentationElement, mComponent));
            }
        });
    }

    @Override
    public Dimension getPreferredSize (ShapeElement shapeElement, Dimension dimension)
    {
        new JComponentHandlerAcceptor().acceptHandler(shapeElement, new JComponentPrefSizeHandler(mComponent,
                dimension));
        return dimension;
    }

    @Override
    public void calculateMinimumSize (ShapeElement shapeElement, Dimension dimension)
    {
        hideHeader(shapeElement);
        new JComponentHandlerAcceptor().acceptHandler(shapeElement, new JComponentMinSizeHandler(shapeElement,
                mComponent, dimension));
    }

    @Override
    public Dimension getDefaultSize (ShapeElement shapeElement)
    {
        return (Dimension) new JComponentHandlerAcceptor().acceptHandler(shapeElement,
                new JComponentDefaultSizeHandler(shapeElement));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure (CompoundManipulatorActionManager manager)
    {
        ManipulatorAction action = manager.getAction(PropertyID.SUPPRESS_CLASS_ATTRIBUTES);
        if (action != null)
        {
            manager.removeAction(action);
        }
        action = manager.getAction(PropertyID.SUPPRESS_CLASS_OPERATIONS);
        if (action != null)
        {
            manager.removeAction(action);
        }
        action = manager.getAction(ActionsID.INSERT_NEW_ATTRIBUTE);
        if (action != null)
        {
            manager.removeAction(action);
        }
        action = manager.getAction(ActionsID.INSERT_NEW_OPERATION);
        if (action != null)
        {
            manager.removeAction(action);
        }

        action = manager.getAction(ActionsID.INSERT_NEW_PORT);
        if (action != null)
        {
            manager.removeAction(action);
        }

        action = manager.getAction(ActionsID.INSERT_NEW_RECEPTION);
        if (action != null)
        {
            manager.removeAction(action);
        }
    }

    @Override
    public TextEditor createTextEditor (PresentationElement element)
    {
        return new JComponentTextEditor(((HeaderShapeView) element).getNameLabel());
    }
}
