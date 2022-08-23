package com.example.kernlang.codebase_viewer;

import com.example.kernlang.TextEditor;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.codebase_viewer.popup_screens.FieldContextMenu;
import com.example.kernlang.codebase_viewer.popup_screens.NodeContextMenu;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.Pane;

public class CodebaseViewer extends Pane implements InvalidationListener {


    private static CursorState cursorState;

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
    }

    public void compileNodes() {
        cursorState.compileNodes();
    }

    @Override
    public void invalidated(Observable observable) {
    }
}