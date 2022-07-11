package com.example.kernlang.codebase_viewer.graph;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * A GraphEdge has a startNode and an endNode, to whose coordinates it is bound.
 * That means that when the startNode or endNode is dragged around, the edge will follow accordingly.
 */
public class GraphEdge extends Pane {
    private final GraphNode startNode;
    private GraphNode endNode;

    private final Line line;
    // the circle below acts as the arrow head
    private final Circle circle;
    private final int arrowHeadRadius = 5;

    private final boolean isBound;

    /**
     * Perhaps this strikes you as somewhat unusual,
     * but the endNode is left unspecified at the creation of a GraphEdge.
     * @param startNode
     * The reason for leaving out the endNode in the constructor, is because the user might be in the midst
     * of dragging this edge to another Node, in which case we already want to render the Edge,
     * as a form of visual feedback to the user.
     */
    public GraphEdge(GraphNode startNode, boolean isBound) {
        this.isBound = isBound;
        this.line = new Line();
        this.circle = new Circle();
        circle.setFill(Color.RED);
        circle.setRadius(arrowHeadRadius);
        this.getChildren().addAll(line, circle);
        this.startNode = startNode;
        line.startXProperty().bind(this.startNode.getXProperty());
        line.startYProperty().bind(this.startNode.getYProperty());

        if (isBound) { line.setStroke(Color.GREEN); }
    }

    public void setEndX(double x) {
        line.setEndX(x);
        circle.setCenterX(x);
    }

    public void setEndY(double y) {
        line.setEndY(y);
        circle.setCenterY(y);
    }

    public double getStartX() {
        return line.getStartX();
    }

    public double getStartY() {
        return line.getStartY();
    }

    public double getEndX() {
        return line.getEndX();
    }

    public double getEndY() {
        return line.getEndY();
    }

    public void setInvisible() {
        line.setVisible(false);
        circle.setVisible(false);
    }

    public void setVisible() {
        line.setVisible(true);
        circle.setVisible(true);
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
        this.endNode.addExport(this);
        this.setEndX(endNode.getXProperty().getValue());
        this.setEndY(endNode.getYProperty().getValue());
        line.endXProperty().bind(this.endNode.getXProperty());
        line.endYProperty().bind(this.endNode.getYProperty());
        circle.centerXProperty().bind(this.endNode.getXProperty());
        circle.centerYProperty().bind(this.endNode.getYProperty());
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public GraphNode getStartNode() {
        return startNode;
    }
}
