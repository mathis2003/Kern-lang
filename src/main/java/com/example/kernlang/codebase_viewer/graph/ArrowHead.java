package com.example.kernlang.codebase_viewer.graph;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ArrowHead extends Circle {
    private final DoubleProperty startX, startY, endX, endY;
    private final double radius = 5;

    public ArrowHead(Line l) {
        this.startX = l.startXProperty();
        this.startY = l.startYProperty();
        this.endX = l.endXProperty();
        this.endY = l.endYProperty();

        this.startX.addListener(e -> updatePos());
        this.startY.addListener(e -> updatePos());
        this.endX.addListener(e -> updatePos());
        this.endY.addListener(e -> updatePos());

        this.setFill(Color.RED);
        this.setRadius(radius);
        this.visibleProperty().bind(l.visibleProperty());

        updatePos();

        // TODO: centerX en centerY correct binden aan de input waarden
    }

    private void updatePos() {
        // IDK why, but this works, so for now it's good enough
        double m = (endY.get() - startY.get()) / (endX.get() - startX.get());
        double angle = Math.atan(m);
        int a = endX.get() > startX.get() ? 1 : -1;
        this.setCenterX(endX.get() - Math.cos(angle) * a * this.radius);
        this.setCenterY(endY.get() - Math.sin(angle) * a * this.radius);
    }
}
