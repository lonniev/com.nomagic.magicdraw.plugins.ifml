package com.nomagic.magicdraw.plugins.ifml;

import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlTreeCellRenderer;
import com.nomagic.magicdraw.plugins.ifml.javacomponents.IfmlTreeNode;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mindaugas Genutis
 */
public class TreeHelper
{
    public static void updateTreeViews(Element treeElement)
    {
        for (JComponent component : JComponentListener.getElementComponents(treeElement))
        {
            JScrollPane treeScrollPane = (JScrollPane) component;
            JTree tree = (JTree) treeScrollPane.getViewport().getView();

            updateTree(treeScrollPane, tree, (NamedElement) treeElement);
        }
    }

    public static void updateTree(JScrollPane treeScrollPane, JTree tree, NamedElement treeElement)
    {
        tree.setCellRenderer(new IfmlTreeCellRenderer());

        IfmlTreeNode rootNode = new IfmlTreeNode(IfmlModelingHelper.getText(treeElement), treeElement);

        initIconForTreeNode(treeElement, rootNode);
        fillNodes(treeElement, rootNode);

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);

        collapseAllRows(tree);

        expandNecessaryNodes(tree, treeElement, treeModel);

        if (!IfmlModelingHelper.isTreeExpanded(treeElement))
        {
            collapseAllRows(tree);
        }

        initTreeSelection(tree, treeElement, treeModel);

        treeScrollPane.setHorizontalScrollBarPolicy(IfmlModelingHelper.HORIZONTAL_SCROLLBAR_POLICY.get(
                IfmlModelingHelper.getHorizontalScrollbarPolicy(treeElement)));
        treeScrollPane.setVerticalScrollBarPolicy(IfmlModelingHelper.VERTICAL_SCROLLBAR_POLICY.get(
                IfmlModelingHelper.getVerticalScrollbarPolicy(treeElement)));
    }

    private static void expandNecessaryNodes(JTree tree, NamedElement treeElement, DefaultTreeModel treeModel)
    {
        if(IfmlModelingHelper.isTreeExpanded(treeElement))
        {
            tree.expandRow(0);
        }
        for (Element node : IfmlModelingHelper.getNodeElements(treeElement, new ArrayList<Element>(), false))
        {
            if (IfmlModelingHelper.isTreeNodeExpanded(node))
            {
                TreePath treePath = null;

                for (IfmlTreeNode treeNode : IfmlModelingHelper.getIfmlTreeNodes((IfmlTreeNode) treeModel.getRoot(),
                        new ArrayList<IfmlTreeNode>(), treeModel))
                {
                    if (treeNode.getId().equals(node.getID()))
                    {
                        treePath = new TreePath(treeNode.getPath());
                        break;
                    }
                }
    
                if (treePath != null)
                {
                    tree.expandPath(treePath);
                }
            }
        }
    }

    private static void initIconForTreeNode(NamedElement element, IfmlTreeNode node)
    {
        Icon icon = IfmlModelingHelper.getIconValue(element);

        if (icon != null)
        {
            node.setIcon(icon);
            node.setIconfied(true);
        }
        else
        {
            node.setIconfied(false);
        }
    }

    private static void collapseAllRows(JTree tree)
    {
        for (int i = tree.getRowCount() - 1; i >= 0; i--)
        {
            tree.collapseRow(i);
        }
    }

    private static void initTreeSelection(JTree tree, NamedElement tableElement, DefaultTreeModel treeModel)
    {
        TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();

        List<Element> nodes = IfmlModelingHelper.getNodeElements(tableElement, new ArrayList<Element>(), true);

        for (Element node : nodes)
        {
            if (IfmlModelingHelper.isSelected(node))
            {
                IfmlTreeNode treeNode = getTreeNode(node, treeModel);

                if (treeNode != null)
                {
                    treeSelectionModel.addSelectionPath(new TreePath(treeNode.getPath()));
                }
            }
        }

        tree.setSelectionModel(treeSelectionModel);
    }

    private static IfmlTreeNode getTreeNode(Element treeElement, DefaultTreeModel treeModel)
    {
        for (IfmlTreeNode treeNode : IfmlModelingHelper.getIfmlTreeNodes((IfmlTreeNode) treeModel
                .getRoot(), new ArrayList<IfmlTreeNode>(), treeModel))
        {
            if (treeNode.getId().equals(treeElement.getID()))
            {
                return treeNode;
            }
        }

        return null;
    }

    static void fillNodes(Element parentElement, IfmlTreeNode parentNode)
    {
        if (parentElement.hasOwnedElement())
        {
            for (Element childElement : parentElement.getOwnedElement())
            {
                if (IfmlModelingHelper.isLeaf(childElement) ||
                        IfmlModelingHelper.isNode(childElement))
                {
                    IfmlTreeNode childNode = new IfmlTreeNode(IfmlModelingHelper.getText(childElement), childElement);
                    initIconForTreeNode((NamedElement) childElement, childNode);
                    parentNode.add(childNode);

                    fillNodes(childElement, childNode);
                }
            }
        }
    }
}
