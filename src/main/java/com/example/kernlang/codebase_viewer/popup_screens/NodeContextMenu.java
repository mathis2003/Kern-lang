package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.GraphWindowState;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class NodeContextMenu extends ContextMenu {

    public NodeContextMenu(GraphWindowState gws, GraphNode node) {
        MenuItem move = new MenuItem("Move");
        move.setOnAction(e -> gws.setStateDraggingNode(node));

        MenuItem importFreeMenuItem = new MenuItem("Import");
        importFreeMenuItem.setOnAction(e -> new EdgeCreationPopup(gws, node));

        MenuItem viewCode = new MenuItem("View Code");
        viewCode.setOnAction(e -> gws.setTextEditorNode(node));

        MenuItem viewAST = new MenuItem("View AST");
        viewAST.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Abstract Syntax Tree");
            alert.setHeaderText(null);
            alert.setContentText(node.getAST().toString(""));

            alert.showAndWait();
        });

        MenuItem runCode = new MenuItem("Run");
        runCode.setOnAction(e -> {
            //Interpreter interpreter = new Interpreter();
            //interpreter.runFunction(node);
            node.runNode();
        });

        MenuItem runApp = new MenuItem("Run App");
        runApp.setOnAction(e -> {
            gws.addProcess(node);
        });

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> {
            new DeletePopup(gws, node);
        });

        getItems().addAll(move, importFreeMenuItem, viewCode, viewAST, runCode, runApp, delete);
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
