package com.example.kernlang.codebase_viewer;

import com.example.kernlang.TextEditor;
import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.codebase_viewer.graph.Types;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

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

    public void compileNodes() {
        for (GraphNode node : graphNodes) {
            node.compile();
        }
    }

    private enum State {
        DRAGGING_EDGE, DRAGGING_NODE, FREE
    }

    private double clickedX, clickedY;

    private GraphEdge importLine;
    private GraphNode draggedNode;

    private State state = State.FREE;

    private final CodebaseViewer cbv;
    private final TextEditor textEditor;

    public CursorState(CodebaseViewer cbv, TextEditor textEditor) {
        this.listeners = new ArrayList<>();
        this.graphNodes = new ArrayList<>();
        this.cbv = cbv;
        this.textEditor = textEditor;
    }

    public void setTextEditorNode(GraphNode node) {
        textEditor.setCurrentNode(node);
    }

    public void setStateDraggingNode(GraphNode node) {
        draggedNode = node;
        this.state = State.DRAGGING_NODE;
    }

    public void setStateDraggingEdge(GraphNode startNode, boolean isBound) {
        state = State.DRAGGING_EDGE;
        importLine = new GraphEdge(startNode, isBound);
        importLine.setEndX(clickedX);
        importLine.setEndY(clickedY);
        this.cbv.getChildren().add(importLine);
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
        if (isDraggingEdge()) {
            importLine.setEndX(x);
            importLine.setEndY(y);
        } else if (state == State.DRAGGING_NODE) {
            draggedNode.setCenter(x, y);
        }
    }

    public void updateClickedPosition(double x, double y) {
        clickedX = x;
        clickedY = y;
    }

    public GraphNode getNodeAtPosition(double x, double y) {
        for (GraphNode node : graphNodes)
            if (Math.abs(x - node.getXProperty().getValue()) < node.getRadius() / 2 &&
                    Math.abs(y - node.getYProperty().getValue()) < node.getRadius() / 2 )
                return node;

        // if no node was found at the position, return null
        return null;
    }

    public void drawCircle(String name, Types t) {
        GraphNode node = new GraphNode(name, clickedX, clickedY, this);
        node.setNodeType(t);
        cbv.getChildren().add(node);
        graphNodes.add(node);
    }
}