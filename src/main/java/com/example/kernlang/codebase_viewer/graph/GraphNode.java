package com.example.kernlang.codebase_viewer.graph;

import com.example.kernlang.codebase_viewer.CursorState;
import com.example.kernlang.codebase_viewer.popup_screens.NodeContextMenu;
import com.example.kernlang.interpreter.frontend.Compiler;
import com.example.kernlang.interpreter.frontend.parser.ASTNode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GraphNode extends Pane {
    private final Circle circle;
    private final Text nodeNameText;
    private final String name;

    private ASTNode ast;

    private Types type;
    private String codeString = "";
    private SimpleDoubleProperty x, y;

    private final int radius = 20;
    private boolean collapsed;

    // These fields contain the edges of the imports, and the edges of the exports
    private final ArrayList<GraphEdge> imports;
    private final ArrayList<GraphEdge> exports;

    public GraphNode(String name, double x, double y, CursorState cs) {
        setOnMouseClicked(e -> {
            if (cs.isDraggingNode()) {
                cs.setStateFree();
            } else {
                cs.updateClickedPosition(e.getSceneX(), e.getSceneY());
                new NodeContextMenu(cs, this).show(this, e.getScreenX(), e.getScreenY());
            }
            e.consume();
        });
        this.name = name;
        circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setFill(Color.GRAY);
        nodeNameText = new Text();
        nodeNameText.setX(x);
        nodeNameText.setY(y);
        this.getChildren().addAll(circle, nodeNameText);
        this.imports = new ArrayList<>();
        this.exports = new ArrayList<>();
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.x.addListener(e -> this.setCenterX(this.x.getValue()));
        this.y.addListener(e -> this.setCenterY(this.y.getValue()));
        collapsed = false;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }

    public String getCodeString() {
        return this.codeString;
    }

    public void setNodeType(Types t) {
        this.type = t;
        nodeNameText.setText(this.name + " : " + t.getTypeName());
    }

    public Types getNodeType() {
        return this.type;
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


    /**
     * Note: the functionality of a GraphNode is asymetric, that is:
     * you can tell a GraphNode to import some other GraphNode,
     * but you can't tell a GraphNode to export to some other GraphNode.
     * For now, every GraphNode *does* keep both import edges and export edges, for perhaps future convenience.
     * @param importNode represents the GraphNode we want to import
     */
    public void importGraphNode(GraphNode importNode, boolean isBound) {
        GraphEdge edge = new GraphEdge(this, isBound);
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
        circle.setVisible(false);
        nodeNameText.setVisible(false);
    }

    public void setVisible() {
        circle.setVisible(true);
        nodeNameText.setVisible(true);
    }

    public void collapseSubClusters(GraphNode mainNode) {
        collapsed = true;
        mainNode.circle.setFill(Color.GREEN);
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

    public void openSubClusters(GraphNode mainNode) {
        collapsed = false;
        mainNode.circle.setFill(Color.GRAY);
        for (GraphEdge importEdge : imports) {
            GraphNode childNode = importEdge.getEndNode();
            childNode.setVisible();
            importEdge.setVisible();
            childNode.openSubClusters(mainNode);
        }
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void compile() {
        this.ast = Compiler.compile(this);
    }

    public ASTNode getAST() {
        return this.ast;
    }
}