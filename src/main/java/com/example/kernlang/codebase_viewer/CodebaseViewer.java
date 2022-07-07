package com.example.kernlang.codebase_viewer;

import com.example.kernlang.codebase_viewer.popup_screens.FieldContextMenu;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
            } else {
                cursorState.updateClickedPosition(e.getSceneX(), e.getSceneY());
                new FieldContextMenu(cursorState).show(this, e.getScreenX(), e.getScreenY());
            }
        });
    }

    @Override
    public void invalidated(Observable observable) {
    }
}