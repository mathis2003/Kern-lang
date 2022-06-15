package com.example.kernlang;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CodebaseViewer extends Pane {
    public CodebaseViewer() {
        this.setPrefSize(200, 200);
        this.setOnMouseClicked(event -> {
            Circle c = new Circle();
            c.setCenterX(event.getSceneX());
            c.setCenterY(event.getSceneY());
            c.setRadius(20);
            c.setFill(Color.GRAY);
            this.getChildren().add(c);
        });
    }
}
