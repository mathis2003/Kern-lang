package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.CursorState;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class NodeContextMenu extends ContextMenu {

    public NodeContextMenu(CursorState cs, GraphNode node) {
        MenuItem move = new MenuItem("Move");
        move.setOnAction(e -> cs.setStateDraggingNode(node));
        MenuItem importFreeMenuItem = new MenuItem("Import Free");
        importFreeMenuItem.setOnAction(e -> cs.setStateDraggingEdge(node, false));

        MenuItem importBoundMenuItem = new MenuItem("Import Bound");
        importBoundMenuItem.setOnAction(e -> cs.setStateDraggingEdge(node, true));

        MenuItem viewCode = new MenuItem("View Code");
        viewCode.setOnAction(e -> System.out.println("viewing code"));
        getItems().addAll(move, importFreeMenuItem, importBoundMenuItem, viewCode);
        if (node.isCollapsed()) {
            MenuItem openCluster = new MenuItem("Open Cluster");
            openCluster.setOnAction(e -> node.openSubClusters(node));
            getItems().add(openCluster);
        } else if (node.getImports().size() != 0) {
            MenuItem closeCluster = new MenuItem("Close Cluster");
            closeCluster.setOnAction(e -> node.collapseSubClusters(node));
            getItems().add(closeCluster);
        }
        setAutoHide(true);
    }
}