package com.example.kernlang.codebase_viewer;

import com.example.kernlang.TextEditor;
import com.example.kernlang.codebase_viewer.popup_screens.FieldContextMenu;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CodebaseViewer extends Pane {


    private static GraphWindowState graphWindowState;

    private double currentDraggedMouseX = 0;
    private double currentDraggedMouseY = 0;
    private boolean isNavigatingScreen = false;

    public CodebaseViewer(TextEditor textEditor) {
        graphWindowState = new GraphWindowState(this, textEditor);
        //graphWindowState.addListener(this);
        this.setPrefSize(200, 200);

        // the functions below are controllers for the cursorState model
        this.setOnMouseMoved(e -> graphWindowState.updatePosition(e.getSceneX(), e.getSceneY()));
        this.setOnMouseClicked(e -> {
            if (graphWindowState.isDraggingEdge()) {
                graphWindowState.addEdge();
            } else if (graphWindowState.isDraggingNode()) {
                graphWindowState.setStateFree();
            } else {
                if (isNavigatingScreen) {
                    // this means the user was dragging the screen to navigate, and now released the drag.
                    // so instead of creating a popup for a new node, we set the isNavigatingScreen field to false.
                    isNavigatingScreen = false;
                    return;
                }
                graphWindowState.updateClickedPosition(e.getSceneX(), e.getSceneY());
                new FieldContextMenu(graphWindowState).show(this, e.getScreenX(), e.getScreenY());
                /* this code should not be necessary, but is left in in case we have to revert back in the future
                cursorState.updateClickedPosition(e.getSceneX(), e.getSceneY());
                GraphNode n = cursorState.getNodeAtPosition(e.getSceneX(), e.getSceneY());
                if (n == null) {
                    new FieldContextMenu(cursorState).show(this, e.getScreenX(), e.getScreenY());
                } else {
                    new NodeContextMenu(cursorState, n).show(n, e.getScreenX(), e.getScreenY());
                }*/
            }
        });
        this.setOnMouseDragged(e -> {
            //get drag vector, subtract this from the position of the objects
            if (!isNavigatingScreen) {
                currentDraggedMouseX = e.getX();
                currentDraggedMouseY = e.getY();
                isNavigatingScreen = true;
                return;
            }
            double newX = e.getX();
            double newY = e.getY();
            double dx = newX - currentDraggedMouseX;
            double dy = newY - currentDraggedMouseY;
            currentDraggedMouseX = newX;
            currentDraggedMouseY = newY;
            graphWindowState.translateAllGraphNodes(dx, dy);

        });
        Button zoomInButton = new Button("zoom in");
        zoomInButton.setOnAction(e -> graphWindowState.zoomOnGraph(1.1));
        Button zoomOutButton = new Button("zoom out");
        zoomOutButton.setOnAction(e -> graphWindowState.zoomOnGraph(0.9));
        this.getChildren().add(new HBox(zoomInButton, zoomOutButton));
    }

    public void compileNodes() {
        graphWindowState.compileNodes();
    }
}