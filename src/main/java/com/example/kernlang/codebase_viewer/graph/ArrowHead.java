package com.example.kernlang.codebase_viewer.graph;

import javafx.scene.shape.Circle;

public class ArrowHead extends Circle {
    double startX, startY, endX, endY;
    double radius;

    public ArrowHead(double startX, double startY, double endX, double endY, double radius) {
        this.startX = startX;
        this.startY = startY;
        this.endX   = endX;
        this.endY   = endY;
        this.radius = radius;
        updatePos();
    }

    public void setNewStartX(double newStartX) {
        this.startX = newStartX;
        updatePos();
    }

    public void setNewStartY(double newStartY) {
        this.startY = newStartY;
        updatePos();
    }

    public void setNewEndX(double newEndX) {
        this.endX = newEndX;
        updatePos();
    }

    public void setNewEndY(double newEndY) {
        this.endY = newEndY;
        updatePos();
    }

    public void updatePos() {
        // idk why, but this works, so for now it's good enough
        double m = (endY - startY) / (endX - startX);
        double angle = Math.atan(m);
        int a = endX > startX ? 4 : -4;
        this.setCenterX(endX - Math.cos(angle) * a * this.radius);
        this.setCenterY(endY - Math.sin(angle) * a * this.radius);
    }
}
