package com.example.kernlang.codebase_viewer.graph;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GraphNode extends Pane {
    private final Circle circle;
    private final Text nodeNameText;
    private final int cluster;
    private int parentCluster;
    private final int codeFileKey;
    private SimpleDoubleProperty x, y;

    private final int radius = 20;

    // These fields contain the edges of the imports, and the edges of the exports
    private final ArrayList<GraphEdge> imports;
    private final ArrayList<GraphEdge> exports;

    public GraphNode(int cluster, int parentCluster, int codeFileKey, double x, double y) {
        circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setFill(Color.GRAY);
        nodeNameText = new Text("test");
        nodeNameText.setX(x);
        nodeNameText.setY(y);
        this.getChildren().addAll(circle, nodeNameText);
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

    public void setNodeName(String s) {
        nodeNameText.setText(s);
    }

    public double getRadius() {
        return radius;
    }

    public void setCenterX(double x) {
        circle.setCenterX(x);
        nodeNameText.setX(x);
    }

    public void setCenterY(double y) {
        circle.setCenterY(y);
        nodeNameText.setY(y);
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


    public void setInvisible() {
        circle.setFill(Color.TRANSPARENT);
        nodeNameText.setFill(Color.TRANSPARENT);
    }

    public void setVisible() {
        circle.setFill(Color.GRAY);
        nodeNameText.setFill(Color.BLACK);
    }

    public void collapseSubClusters(GraphNode mainNode) {
        for (GraphEdge importEdge : imports) {
            GraphNode childNode = importEdge.getEndNode();
            if (allExportsLandAtNode(childNode, mainNode)) {
                childNode.setInvisible();
                importEdge.setInvisible();
                childNode.collapseSubClusters(mainNode);
            }
        }
    }

    public boolean allExportsLandAtNode(GraphNode startNode, GraphNode endNode) {
        for (GraphEdge exportEdge : startNode.exports) {
            GraphNode nextNode = exportEdge.getStartNode();
            if (nextNode.exports.size() == 0 && nextNode != endNode && startNode != endNode) return false;
            else if (!allExportsLandAtNode(nextNode, endNode)) return false;
        }

        return true;
    }

    public void openSubClusters() {
        for (GraphEdge importEdge : imports) {
            GraphNode childNode = importEdge.getEndNode();
            childNode.setVisible();
            importEdge.setVisible();
            childNode.openSubClusters();
        }
    }
}
