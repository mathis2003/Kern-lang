package com.example.kernlang.codebase_viewer.graph;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class GraphNode extends Circle {
    private final int cluster;
    private int parentCluster;
    private final int codeFileKey;
    private SimpleDoubleProperty x, y;

    // These fields contain the edges of the imports, and the edges of the exports
    private final ArrayList<GraphEdge> imports;
    private final ArrayList<GraphEdge> exports;

    public GraphNode(int cluster, int parentCluster, int codeFileKey, double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(20);
        this.setFill(Color.GRAY);
        this.cluster = cluster;
        this.parentCluster = parentCluster;
        this.codeFileKey = codeFileKey;
        this.imports = new ArrayList<>();
        this.exports = new ArrayList<>();
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.x.addListener(e -> this.setCenterX(this.x.getValue()));
        this.y.addListener(e -> this.setCenterY(this.y.getValue()));
    }

    public SimpleDoubleProperty getXProperty() {
        return this.x;
    }

    public SimpleDoubleProperty getYProperty() {
        return this.y;
    }

    public int getCodeFileKey() {
        return this.codeFileKey;
    }

    /**
     * Note: the functionality of a GraphNode is asymetric, that is:
     * you can tell a GraphNode to import some other GraphNode,
     * but you can't tell a GraphNode to export to some other GraphNode.
     * For now, every GraphNode *does* keep both import edges and export edges, for perhaps future convenience.
     * @param importNode represents the GraphNode we want to import
     */
    public void importGraphNode(GraphNode importNode) {
        GraphEdge edge = new GraphEdge(this);
        edge.setEndNode(importNode);
        imports.add(edge);
        importNode.addExport(edge);
    }

    public void addExport(GraphEdge exportEdge) {
        exports.add(exportEdge);
    }

    public void addImport(GraphEdge edge) { imports.add(edge); }

    public ArrayList<GraphEdge> getImports() {
        return this.imports;
    }
}
