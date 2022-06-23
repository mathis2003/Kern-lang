package com.example.kernlang;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CodebaseViewer extends Pane {


    private static CursorState cursorState;

    public CodebaseViewer() {
        cursorState = new CursorState(this);
        this.setPrefSize(200, 200);
        this.setOnMouseMoved(e -> {
            if (cursorState.isDraggingEdge()) {
                this.getChildren().remove(cursorState.importLine);
                cursorState.importLine.setEndX(e.getSceneX());
                cursorState.importLine.setEndY(e.getSceneY());
                System.out.println(cursorState.importLine.getStartX() + "  :  " + cursorState.importLine.getStartY());
                this.getChildren().add(cursorState.importLine);
            }

        });
        this.setOnMouseClicked(e -> {
            //Circle c = new Circle();
            //c.setCenterX(event.getSceneX());
            //c.setCenterY(event.getSceneY());
            //c.setRadius(20);
            //c.setFill(Color.GRAY);
            //this.getChildren().add(c);
            cursorState.startX = e.getSceneX();
            cursorState.startY = e.getSceneY();
            if (cursorState.isDraggingEdge()) {
                cursorState.setStateFree();
            } else {
                new GraphPopUp(cursorState);
            }

        });
    }
}
