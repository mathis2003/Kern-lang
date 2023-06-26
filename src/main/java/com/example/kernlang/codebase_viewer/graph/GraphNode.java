package com.example.kernlang.codebase_viewer.graph;

import com.example.kernlang.codebase_viewer.GraphWindowState;
import com.example.kernlang.codebase_viewer.popup_screens.NodeContextMenu;
import com.example.kernlang.compiler.parser.expressions.BinaryExpr;
import com.example.kernlang.compiler.parser.expressions.IdentifierExpr;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.Literal;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.parser.statements.Stmt;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class GraphNode extends Pane {
    private final Circle circle;
    private final Text nodeNameText;
    public final String name;

    private Literal astLiteralExpr;

    private Types type;
    private String codeString = "";
    private SimpleDoubleProperty x, y;

    private final int radius = 20;
    public boolean collapsed;

    public boolean collapsable;
    public GraphNode collapser;

    // These fields contain the edges of the imports, and the edges of the exports
    private final ArrayList<GraphEdge> imports;
    private final ArrayList<GraphEdge> exports;

    private final int dbId;

    public GraphNode(String name, double x, double y, GraphWindowState gws, int dbId) {
        this.dbId = dbId;
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        requestFocus();
        setOnMouseClicked(e -> {
            if (gws.isDraggingNode()) {
                gws.setStateFree();
            } else if (gws.isDraggingEdge()) {
                GraphNode startNode = gws.getImportLine().getStartNode();
                GraphNode endNode = this;
                gws.getImportLine().setEndNode(endNode);
                startNode.addImport(gws.getImportLine());
                gws.setStateFree();
            } else {
                gws.updateClickedPosition(e.getSceneX(), e.getSceneY());
                new NodeContextMenu(gws, this).show(this, e.getScreenX(), e.getScreenY());
            }
            e.consume();
        });
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        relocate(x, y);
        this.name = name;
        circle = new Circle();
        circle.setCenterX(radius + 2);
        circle.setCenterY(radius + 2);
        circle.setRadius(radius);
        circle.setFill(Color.GRAY);
        circle.visibleProperty().bind(visibleProperty());
        nodeNameText = new Text();
        nodeNameText.setX(radius + 2);
        nodeNameText.setY(radius + 2);
        nodeNameText.visibleProperty().bind(visibleProperty());
        this.getChildren().addAll(circle, nodeNameText);
        this.imports = new ArrayList<>();
        this.exports = new ArrayList<>();
        collapsed = false;

        collapsable = false;
        collapser = null;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }

    public String getCodeString() {
        return this.codeString;
    }

    public int getDatabaseID() {
        return this.dbId;
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

    public void setCenter(double x, double y) {
        relocate(x - radius, y - 40);
        this.x.set(x);
        this.y.set(y);
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

    public void collapseSubClusters(GraphNode mainNode) {

        if (isCollapsable(mainNode, new HashSet<>())) {
            collapsed = true;
            mainNode.circle.setFill(Color.GREEN);
            for (GraphEdge importEdge : imports) {
                GraphNode childNode = importEdge.getEndNode();
                if (childNode.isCollapsable(mainNode, new HashSet<>()) && !childNode.collapsed) {
                    childNode.collapsed = true;
                    childNode.setVisible(false);
                    for (GraphEdge impEdg : childNode.imports) {
                        impEdg.setVisible(false);
                    }
                    for (GraphEdge expEdg : childNode.exports) {
                        expEdg.setVisible(false);
                    }

                    childNode.collapseSubClusters(mainNode);
                }
            }
        }

    }

    public boolean isCollapsable(GraphNode collapseNode, HashSet<GraphNode> visitedNodes) {
        // rule 1 is obsolete since we wouldn't get here if the
        // collapseNode didn't have this as some sort of child
        boolean result = false;

        if (collapseNode == collapser) {
            return collapsable;
        }


        if (visitedNodes.contains(this)) {
            visitedNodes.remove(this);
            collapsable = true;
            collapser = collapseNode;
            return true;
        }
        visitedNodes.add(this);



        // rule 2
        if (!allExportsLandAtNode(this, collapseNode, new HashSet<>())) {
            visitedNodes.remove(this);
            return false;
        }

        // rule 3
        for (GraphEdge importEdge : imports) {
            GraphNode childNode = importEdge.getEndNode();
            if (!childNode.isCollapsable(collapseNode, visitedNodes)) {
                visitedNodes.remove(this);
                return false;
            }
        }

        visitedNodes.remove(this);
        collapsable = true;
        collapser = collapseNode;
        return true;
    }

    public boolean allExportsLandAtNode(GraphNode startNode, GraphNode endNode, HashSet<GraphNode> visitedNodes) {
        // without this condition, which MUST come before the loop,
        // this function would take recurse infinitely when it finds a cycle
        if (Collections.frequency(visitedNodes, startNode) > 1) return true;

        for (GraphEdge exportEdge : startNode.exports) {
            boolean result = true;
            GraphNode nextNode = exportEdge.getStartNode();
            if (visitedNodes.contains(nextNode)) continue;
            visitedNodes.add(nextNode);
            if (nextNode.exports.size() == 0 && nextNode != endNode && startNode != endNode) result = false;
            else if (!allExportsLandAtNode(nextNode, endNode, visitedNodes)) result = false;
            visitedNodes.remove(nextNode);
            if (!result) return false;
        }

        return true;
    }

    public void openSubClusters(GraphNode mainNode) {
        if (!collapsed) return;
        collapsed = false;
        mainNode.circle.setFill(Color.GRAY);
        for (GraphEdge importEdge : imports) {
            GraphNode childNode = importEdge.getEndNode();
            childNode.setVisible(true);
            importEdge.setVisible(true);
            childNode.openSubClusters(mainNode);
            childNode.collapsed = false;
        }
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public String getName() {
        return this.name;
    }

    public void setAstExpr(Literal astLiteralExpr) {
        this.astLiteralExpr = astLiteralExpr;
    }

    public Literal getAST() {
        return this.astLiteralExpr;
    }

    public HashMap<String, Literal> getContext() {
        HashMap<String, Literal> result = new HashMap<>();
        for (GraphEdge e : imports) {
            GraphNode importNode = e.getEndNode();
            result.put(importNode.getName(), importNode.getAST());
        }
        return result;
    }

    public void runNode() {
        if (this.astLiteralExpr instanceof FunctionLiteral) {
            for (Stmt stmt : ((FunctionLiteral)astLiteralExpr).getStatements()) {
                if (stmt instanceof Assignment assignStmt) {
                    String ident = null;
                    if (assignStmt.getAssignedObj() instanceof IdentifierExpr) {
                        ident = ((IdentifierExpr)assignStmt.getAssignedObj()).getIdentifier();
                    } else if (assignStmt.getAssignedObj() instanceof BinaryExpr) {
                        ident = ((IdentifierExpr)((BinaryExpr)assignStmt.getAssignedObj()).getLeftExpr()).getIdentifier();
                    }
                    for (GraphEdge edge : imports) {
                        if (edge.getEndNode().name.equals(ident)) {
                            assignStmt.assign(edge.getEndNode(), this, new HashMap<>());
                            //edge.getEndNode().setAstExpr(assignStmt.getExpr().interpret(this, new HashMap<>()));
                        }
                    }

                }
                else if (stmt instanceof ReturnStmt) {
                    // since this function isn't called anywhere, but instead run by hand,
                    // it doesn't have to return a value, but can just stop the program
                    return;
                }
            }
        } else {
            // perhaps it is a composition of functions or something?
            // since function composition, nor any other operations on functions are implemented yet,
            // it would be an error if there was any expression other than a function literal
            System.out.println("function literal expected as the contents of the node");
        }
    }
}