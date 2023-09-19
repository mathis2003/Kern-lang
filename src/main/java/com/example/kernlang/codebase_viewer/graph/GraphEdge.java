package com.example.kernlang.codebase_viewer.graph;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * A GraphEdge has a startNode and an endNode, to whose coordinates it is bound.
 * That means that when the startNode or endNode is dragged around, the edge will follow accordingly.
 */
public class GraphEdge extends Line {
    private final GraphNode startNode;
    private GraphNode endNode;

    private ArrowHead arrowHead;

    private final static int ARROW_HEAD_RADIUS = 5;

    /**
     * Perhaps this strikes you as somewhat unusual,
     * but the endNode is left unspecified at the creation of a GraphEdge.
     * @param startNode
     * The reason for leaving out the endNode in the constructor, is because the user might be in the midst
     * of dragging this edge to another Node, in which case we already want to render the Edge,
     * as a form of visual feedback to the user.
     */
    public GraphEdge(GraphNode startNode, boolean isBound) {
        this.startNode = startNode;
        startXProperty().bind(startNode.getXProperty());
        startYProperty().bind(startNode.getYProperty());

        /*arrowHead.setFill(Color.RED);
        arrowHead.setRadius(ARROW_HEAD_RADIUS);
        arrowHead.visibleProperty().bind(visibleProperty());*/

        if (isBound) {
            setStroke(Color.GREEN);
        }
    }

    public ArrowHead getArrowHead() {
        return this.arrowHead;
    }

    public void setArrowHead(ArrowHead arrowHead) {
        this.arrowHead = arrowHead;
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
        this.endNode.addExport(this);
        this.setEndX(endNode.getXProperty().getValue());
        this.setEndY(endNode.getYProperty().getValue());
        endXProperty().bind(endNode.getXProperty());
        endYProperty().bind(endNode.getYProperty());
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public GraphNode getStartNode() {
        return startNode;
    }
}
