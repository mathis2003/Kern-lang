package com.example.kernlang.codebase_viewer.graph;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Circle;

public class ArrowHead extends Circle {
    private final DoubleProperty startX, startY, endX, endY;
    private final double radius;

    public ArrowHead(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, double radius) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.radius = radius;
        updatePos();
        // TODO: centerX en centerY correct binden aan de input waarden
    }

    private void updatePos() {
        // IDK why, but this works, so for now it's good enough
        double m = (endY.get() - startY.get()) / (endX.get() - startX.get());
        double angle = Math.atan(m);
        int a = endX.get() > startX.get() ? 4 : -4;
        this.setCenterX(endX.get() - Math.cos(angle) * a * this.radius);
        this.setCenterY(endY.get() - Math.sin(angle) * a * this.radius);
    }
}
