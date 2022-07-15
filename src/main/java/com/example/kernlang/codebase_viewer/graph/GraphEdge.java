package com.example.kernlang.codebase_viewer.graph;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * A GraphEdge has a startNode and an endNode, to whose coordinates it is bound.
 * That means that when the startNode or endNode is dragged around, the edge will follow accordingly.
 */
public class GraphEdge extends Pane {
    private final GraphNode startNode;
    private GraphNode endNode;

    private final Text identifierText;

    private final Line line;
    // the circle below acts as the arrow head
    //private final Circle circle;
    private final ArrowHead arrowHead;

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
    public GraphEdge(GraphNode startNode, String identifier, boolean isBound) {
        this.identifierText = new Text(identifier);
        this.isBound = isBound;
        this.line = new Line();

        this.startNode = startNode;
        line.startXProperty().bind(this.startNode.getXProperty());
        line.startYProperty().bind(this.startNode.getYProperty());

        this.arrowHead = new ArrowHead(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), arrowHeadRadius);
        arrowHead.setFill(Color.RED);
        arrowHead.setRadius(arrowHeadRadius);
        //this.circle = new Circle();
        //circle.setFill(Color.RED);
        //circle.setRadius(arrowHeadRadius);
        this.getChildren().addAll(line, arrowHead, identifierText);

        if (isBound) { line.setStroke(Color.GREEN); }
    }

    public void setEndX(double x) {
        line.setEndX(x);
        arrowHead.setNewEndX(x);
        identifierText.setX((getStartX() + x) / 2);
    }

    public void setEndY(double y) {
        line.setEndY(y);
        arrowHead.setNewEndY(y);
        identifierText.setY((getStartY() + y) / 2);
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
        arrowHead.setVisible(false);
    }

    public void setVisible() {
        line.setVisible(true);
        arrowHead.setVisible(true);
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
        this.endNode.addExport(this);
        this.setEndX(endNode.getXProperty().getValue());
        this.setEndY(endNode.getYProperty().getValue());
        line.endXProperty().bind(this.endNode.getXProperty());
        line.endYProperty().bind(this.endNode.getYProperty());
        //circle.centerXProperty().bind(this.endNode.getXProperty());
        //circle.centerYProperty().bind(this.endNode.getYProperty());
        startNode.getXProperty().addListener(e -> {
            identifierText.setX((getStartX() + getEndX()) / 2);
            arrowHead.setNewStartX(line.getStartX());
        });
        endNode.getXProperty().addListener(e -> {
            identifierText.setX((getStartX() + getEndX()) / 2);
            arrowHead.setNewEndX(line.getEndX());
        });
        startNode.getYProperty().addListener(e -> {
            identifierText.setY((getStartY() + getEndY()) / 2);
            arrowHead.setNewStartY(line.getStartY());
        });
        endNode.getYProperty().addListener(e -> {
            identifierText.setY((getStartY() + getEndY()) / 2);
            arrowHead.setNewEndY(line.getEndY());
        });
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public GraphNode getStartNode() {
        return startNode;
    }
}
