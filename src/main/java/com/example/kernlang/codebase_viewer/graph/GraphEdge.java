package com.example.kernlang.codebase_viewer.graph;

import javafx.scene.shape.Line;

/**
 * A GraphEdge has a startNode and an endNode, to whose coordinates it is bound.
 * That means that when the startNode or endNode is dragged around, the edge will follow accordingly.
 */
public class GraphEdge extends Line {
    private GraphNode startNode;
    private GraphNode endNode;

    /**
     * Perhaps this strikes you as somewhat unusual,
     * but the endNode is left unspecified at the creation of a GraphEdge.
     * @param startNode
     * The reason for leaving out the endNode in the constructor, is because the user might be in the midst
     * of dragging this edge to another Node, in which case we already want to render the Edge,
     * as a form of visual feedback to the user.
     */
    public GraphEdge(GraphNode startNode) {
        this.startNode = startNode;
        this.setStartX(startNode.getXProperty().getValue());
        this.setStartY(startNode.getYProperty().getValue());
        this.startNode.getXProperty().addListener(e -> this.setStartX(this.startNode.getXProperty().getValue()));
        this.startNode.getYProperty().addListener(e -> this.setStartY(this.startNode.getYProperty().getValue()));
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
        this.setEndX(endNode.getXProperty().getValue());
        this.setEndY(endNode.getYProperty().getValue());
        this.endNode.getXProperty().addListener(e -> this.setEndX(this.endNode.getXProperty().getValue()));
        this.endNode.getYProperty().addListener(e -> this.setEndY(this.endNode.getYProperty().getValue()));
    }
}
