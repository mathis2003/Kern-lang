package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.CursorState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * When a click is registered on a node, a pop-up of this class will appear.
 * It allows the user to draw imports or view the code of the node in the text editor.
 * Objects of this class act as a controller for the MVC model that is cursorState.
 */
public class GraphPopUp extends Stage {
    public GraphPopUp(CursorState cursorState) {
        super();
        HBox layout = new HBox();

        // "new import" button
        Button importButton = new Button("import");
        importButton.setOnAction(e -> {
            cursorState.setStateDraggingEdge();
            this.close();
        });
        layout.getChildren().add(importButton);

        // "new node" button
        Button nodeButton = new Button("new Node");
        nodeButton.setOnAction(e -> {
            new NodeCreationPopup(cursorState);
            this.close();
        });
        layout.getChildren().add(nodeButton);

        // "move node" button
        Button moveNodeButton = new Button("move");
        moveNodeButton.setOnAction(e -> {
            cursorState.setStateDraggingNode();
            this.close();
        });
        layout.getChildren().add(moveNodeButton);

        // "open clusters" button
        Button openClustersButton = new Button("open clusters");
        openClustersButton.setOnAction(e -> {
            cursorState.openSubClusters();
            this.close();
        });
        layout.getChildren().add(openClustersButton);

        // "collapse clusters" button
        Button collapseClustersButton = new Button("collapse clusters");
        collapseClustersButton.setOnAction(e -> {
            cursorState.collapseSubClusters();
            this.close();
        });
        layout.getChildren().add(collapseClustersButton);

        // "view in editor" button
        Button viewButton = new Button("View in Editor");
        viewButton.setOnAction(e -> this.close());
        layout.getChildren().add(viewButton);

        Scene scene = new Scene(layout, 500, 300);
        //scene.getStylesheets().add(String.valueOf(Main.class.getResource(".css")));
        this.setScene(scene);
        this.showAndWait();
    }
}
