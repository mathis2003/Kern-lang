package com.example.kernlang.codebase_viewer;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * This class contains some data about the state of the cursor in the window of the application.
 * It acts as the model of the MVC, the CodebaseViewer on the other hand is the viewer and controller.
 */
public class CursorState implements Observable {

    private final ArrayList<InvalidationListener> listeners;
    private final ArrayList<GraphNode> graphNodes;

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }

    private void fireInvalidationEvent() {
        for (InvalidationListener l : listeners) {
            l.invalidated(this);
        }
    }

    private enum State {
        DRAGGING_EDGE, DRAGGING_NODE, FREE
    }

    private double clickedX, clickedY;
    private double currentX, currentY;

    GraphEdge importLine;
    GraphNode draggedNode;

    private State state = State.FREE;

    private final CodebaseViewer cbv;

    public CursorState(CodebaseViewer cbv) {
        this.listeners = new ArrayList<>();
        this.graphNodes = new ArrayList<>();
        this.cbv = cbv;
    }

    public void setStateDraggingNode() {
        draggedNode = getNodeAtPosition(clickedX, clickedY);
        if (draggedNode != null) {
            this.state = State.DRAGGING_NODE;
        }
    }

    public void setStateDraggingEdge() {
        GraphNode startNode = getNodeAtPosition(clickedX, clickedY);
        if (startNode != null) {
            state = State.DRAGGING_EDGE;
            importLine = new GraphEdge(startNode);
            importLine.setEndX(clickedX);
            importLine.setEndY(clickedY);
            this.cbv.getChildren().add(importLine);
        } else {
            // TODO: show pop up saying you should select a node to start the import from
        }

    }

    public void addEdge() {
        GraphNode startNode = getNodeAtPosition(importLine.getStartX(), importLine.getStartY());
        GraphNode endNode = getNodeAtPosition(importLine.getEndX(), importLine.getEndY());
        if (startNode == null) {
            cbv.getChildren().remove(importLine);
        } else if (endNode != null) {
            importLine.setEndNode(endNode);
            startNode.addImport(importLine);
        } else {
            cbv.getChildren().remove(importLine);
        }
        setStateFree();
    }

    public void setStateFree() {
        state = State.FREE;
        fireInvalidationEvent();
    }

    public boolean isDraggingEdge() {
        return state == State.DRAGGING_EDGE;
    }

    public boolean isDraggingNode() {
        return state == State.DRAGGING_NODE;
    }

    public void updatePosition(double x, double y) {
        currentX = x;
        currentY = y;
        if (isDraggingEdge()) {
            importLine.setEndX(currentX);
            importLine.setEndY(currentY);
        } else if (state == State.DRAGGING_NODE) {
            draggedNode.getXProperty().set(currentX);
            draggedNode.getYProperty().set(currentY);
        }
    }

    public void updateClickedPosition(double x, double y) {
        clickedX = x;
        clickedY = y;
    }

    public GraphNode getNodeAtPosition(double x, double y) {
        for (GraphNode node : graphNodes)
            if (Math.abs(x - node.getXProperty().getValue()) < 5.0 && Math.abs(y - node.getYProperty().getValue()) < 5.0 )
                return node;

        // if no node was found at the position, return null
        return null;
    }

    public void drawCircle(String name) {
        GraphNode node = new GraphNode(0, 0, 0, clickedX, clickedY);
        node.setNodeName(name);
        cbv.getChildren().add(node);
        graphNodes.add(node);
    }
}
