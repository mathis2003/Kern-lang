package com.example.kernlang.codebase_viewer;

import com.example.kernlang.TextEditor;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.codebase_viewer.popup_screens.FieldContextMenu;
import com.example.kernlang.codebase_viewer.popup_screens.NodeContextMenu;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CodebaseViewer extends Pane implements InvalidationListener {


    private static CursorState cursorState;

    private double currentDraggedMouseX = 0;
    private double currentDraggedMouseY = 0;

    public CodebaseViewer(TextEditor textEditor) {
        cursorState = new CursorState(this, textEditor);
        cursorState.addListener(this);
        this.setPrefSize(200, 200);

        // the functions below are controllers for the cursorState model
        this.setOnMouseMoved(e -> cursorState.updatePosition(e.getSceneX(), e.getSceneY()));
        this.setOnMouseClicked(e -> {
            if (cursorState.isDraggingEdge()) {
                cursorState.addEdge();
            } else if (cursorState.isDraggingNode()) {
                cursorState.setStateFree();
            } else {
                cursorState.updateClickedPosition(e.getSceneX(), e.getSceneY());
                new FieldContextMenu(cursorState).show(this, e.getScreenX(), e.getScreenY());
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
            if (currentDraggedMouseX < 0.01 && currentDraggedMouseX > -0.01) {
                currentDraggedMouseX = e.getX();
                currentDraggedMouseY = e.getY();
                return;
            }
            double newX = e.getX();
            double newY = e.getY();
            double dx = newX - currentDraggedMouseX;
            double dy = newY - currentDraggedMouseY;
            currentDraggedMouseX = newX;
            currentDraggedMouseY = newY;
            cursorState.translateAllGraphNodes(dx, dy);

        });
        this.setOnMouseReleased(e -> {
            currentDraggedMouseX = 0;
            currentDraggedMouseY = 0;
        });
        Button zoomInButton = new Button("zoom in");
        Button zoomOutButton = new Button("zoom out");
        this.getChildren().add(new HBox(zoomInButton, zoomOutButton));
    }

    public void compileNodes() {
        cursorState.compileNodes();
    }

    @Override
    public void invalidated(Observable observable) {
    }
}