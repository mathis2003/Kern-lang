package com.example.kernlang.codebase_viewer;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.codebase_viewer.popup_screens.FieldContextMenu;
import com.example.kernlang.codebase_viewer.popup_screens.NodeContextMenu;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;

public class CodebaseViewer extends Pane implements InvalidationListener {


    private static CursorState cursorState;

    public CodebaseViewer() {
        cursorState = new CursorState(this);
        cursorState.addListener(this);
        this.setPrefSize(200, 200);

        // the functions below are controllers for the cursorState model
        this.setOnMouseMoved(e -> cursorState.updatePosition(e.getSceneX(), e.getSceneY()));
        this.setOnMouseClicked(e -> {
            if (cursorState.isDraggingEdge()) {
                cursorState.addEdge();
            } else if (cursorState.isDraggingNode()){
                cursorState.setStateFree();
            } else {
                GraphNode node = cursorState.getNodeAtPosition(e.getSceneX(), e.getSceneY());
                cursorState.updateClickedPosition(e.getSceneX(), e.getSceneY());
                ContextMenu cm;
                if (node != null) {
                    cm = new NodeContextMenu(cursorState, node);
                } else {
                    cm = new FieldContextMenu(cursorState);
                }
                cm.show(this, e.getScreenX(), e.getScreenY());
            }

        });
    }

    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    @Override
    public void invalidated(Observable observable) {
    }
}
