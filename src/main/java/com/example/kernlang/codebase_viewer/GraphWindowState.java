package com.example.kernlang.codebase_viewer;

import com.example.kernlang.TextEditor;
import com.example.kernlang.codebase_viewer.graph.ArrowHead;
import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.codebase_viewer.graph.Types;
import com.example.kernlang.compiler.Compiler;
import com.example.kernlang.db.EdgeData;
import com.example.kernlang.db.NodeData;
import com.example.kernlang.operating_system.OS;
import javafx.beans.InvalidationListener;

import java.util.ArrayList;

/**
 * This class contains some data about the state of the graph-window.
 * It acts as the model of the MVC, the CodebaseViewer on the other hand is the viewer and controller.
 */
public class GraphWindowState {

    private int currentNodeCount = 0;

    private final ArrayList<InvalidationListener> listeners;
    private final ArrayList<GraphNode> graphNodes;

    public void compileNodes() {
        Compiler.compile(graphNodes);
    }

    private enum State {
        DRAGGING_EDGE, DRAGGING_NODE, FREE
    }

    private double clickedX, clickedY;

    private GraphEdge importLine;

    public GraphEdge getImportLine() {
        return importLine;
    }

    private GraphNode draggedNode;

    public State state = State.FREE;

    public final CodebaseViewer cbv;
    private final TextEditor textEditor;

    private final OS os;

    public GraphWindowState(CodebaseViewer cbv, TextEditor textEditor) {
        this.listeners = new ArrayList<>();
        this.graphNodes = new ArrayList<>();
        this.cbv = cbv;
        this.textEditor = textEditor;
        this.os = new OS();
    }

    public void addProcess(GraphNode n) {
        os.addNewProcess(n);
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

        ArrowHead arrowHead = new ArrowHead(importLine);
        this.cbv.getChildren().add(arrowHead);
    }

    public void addEdge() {
        GraphNode startNode = importLine.getStartNode();
        GraphNode endNode = getNodeAtPosition(importLine.getEndX(), importLine.getEndY());
        if (endNode != null) {
            importLine.setEndNode(endNode);
            startNode.addImport(importLine);
        } else {
            cbv.getChildren().remove(importLine);
        }
        setStateFree();
    }

    public void setStateFree() {
        state = State.FREE;
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
            if (Math.abs(x + (node.getRadius() / 2) - node.getXProperty().getValue()) < node.getRadius() * 2 &&
                    Math.abs(y + (node.getRadius() / 2) - node.getYProperty().getValue()) < node.getRadius() * 2)
                return node;

        // if no node was found at the position, return null
        return null;
    }

    public void addEdgeFromDB(EdgeData edgeData) {
        GraphNode startNode = null;
        GraphNode endNode = null;

        //TODO: refactor this so there's a hashmap from databaseID to graphnode
        for (GraphNode n : this.graphNodes) {
            if (n.getDatabaseID() == edgeData.startID()) {
                startNode = n;
                if (endNode != null) break;
            }
            if (n.getDatabaseID() == edgeData.endID()) {
                endNode = n;
                if (startNode != null) break;
            }
        }

        if (startNode == null || endNode == null) return;
        GraphEdge e = new GraphEdge(startNode, false);
        e.setEndNode(endNode);
        startNode.addImport(e);

        this.cbv.getChildren().add(e);
        ArrowHead arrowHead = new ArrowHead(e);
        this.cbv.getChildren().add(arrowHead);
    }

    public void addNodeFromDB(NodeData nodeData) {
        currentNodeCount++;
        GraphNode node = new GraphNode(nodeData.name(), nodeData.xpos(), nodeData.ypos(), this, nodeData.id());
        node.setNodeType(Types.UNIT);
        node.setCodeString(nodeData.code());
        cbv.getChildren().add(node);
        graphNodes.add(node);
    }

    public void addNode(String name, Types t) {
        currentNodeCount++;
        GraphNode node = new GraphNode(name, clickedX, clickedY, this, currentNodeCount);
        node.setNodeType(t);
        cbv.getChildren().add(node);
        graphNodes.add(node);
    }

    public void translateAllGraphNodes(double x, double y) {
        for (GraphNode n : this.graphNodes) {
            n.setCenter(n.getXProperty().get() + x,n.getYProperty().get() + y);
        }
    }

    public void zoomOnGraph(double scale) {
        for (GraphNode n : this.graphNodes) {
            // (0, 0) is the top left of the screen,
            // so we do some tricks in order to make the zoom-center the middle of the screen
            double centerX = cbv.getWidth() / 2;
            double centerY = cbv.getHeight() / 2;
            double oldX = n.getXProperty().get(), oldY = n.getYProperty().get();
            double dx = scale * (oldX -centerX), dy = scale * (oldY - centerY);
            n.setCenter(centerX + dx, centerY + dy);
        }
    }

    public ArrayList<GraphNode> getGraphNodes() {
        return graphNodes;
    }
}