package com.example.kernlang.codebase_viewer.graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

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

    /**
     * Perhaps this strikes you as somewhat unusual,
     * but the endNode is left unspecified at the creation of a GraphEdge.
     * @param startNode
     * The reason for leaving out the endNode in the constructor, is because the user might be in the midst
     * of dragging this edge to another Node, in which case we already want to render the Edge,
     * as a form of visual feedback to the user.
     */
    public GraphEdge(GraphNode startNode) {
        this.line = new Line();
        this.circle = new Circle();
        circle.setFill(Color.RED);
        circle.setRadius(arrowHeadRadius);
        this.getChildren().addAll(line, circle);
        this.startNode = startNode;
        this.setStartX(startNode.getXProperty().getValue());
        this.setStartY(startNode.getYProperty().getValue());
        this.startNode.getXProperty().addListener(e -> this.setStartX(this.startNode.getXProperty().getValue()));
        this.startNode.getYProperty().addListener(e -> this.setStartY(this.startNode.getYProperty().getValue()));
    }

    public void setStartX(double x) {
        line.setStartX(x);
        circle.setCenterX(x);
    }

    public void setStartY(double y) {
        line.setStartY(y);
        circle.setCenterY(y);
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
        this.endNode.getXProperty().addListener(e -> this.setEndX(this.endNode.getXProperty().getValue()));
        this.endNode.getYProperty().addListener(e -> this.setEndY(this.endNode.getYProperty().getValue()));
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public GraphNode getStartNode() {
        return startNode;
    }
}
