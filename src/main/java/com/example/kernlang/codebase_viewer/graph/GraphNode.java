package com.example.kernlang.codebase_viewer.graph;

import com.example.kernlang.codebase_viewer.CursorState;
import com.example.kernlang.codebase_viewer.popup_screens.NodeContextMenu;
import com.example.kernlang.interpreter.frontend.Compiler;
import com.example.kernlang.interpreter.frontend.parser.expressions.BinaryExpr;
import com.example.kernlang.interpreter.frontend.parser.expressions.IdentifierExpr;
import com.example.kernlang.interpreter.frontend.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.interpreter.frontend.parser.expressions.Literal;
import com.example.kernlang.interpreter.frontend.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.interpreter.frontend.parser.statements.Assignment;
import com.example.kernlang.interpreter.frontend.parser.statements.ReturnStmt;
import com.example.kernlang.interpreter.frontend.parser.statements.Stmt;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphNode extends Pane {
    private final Circle circle;
    private final Text nodeNameText;
    public final String name;

    private Literal astLiteralExpr;

    private Types type;
    private String codeString = "";
    private SimpleDoubleProperty x, y;

    private final int radius = 20;
    private boolean collapsed;

    // These fields contain the edges of the imports, and the edges of the exports
    private final ArrayList<GraphEdge> imports;
    private final ArrayList<GraphEdge> exports;

    public GraphNode(String name, double x, double y, CursorState cs) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        requestFocus();
        setOnMouseClicked(e -> {
            if (cs.isDraggingNode()) {
                cs.setStateFree();
            } /*else if (cs.isDraggingEdge()) {
                GraphNode startNode = cs.getImportLine().getStartNode();
                GraphNode endNode = this;
                cs.getImportLine().setEndNode(endNode);
                startNode.addImport(cs.getImportLine());
                cs.setStateFree();
                // TODO: For some reason, a click on this pane never registers when the user is dragging
                // TODO: an import edge, look for a fix for this
                // TODO: after a bit of testing, it seems like it can also happen when dragging a node
            } */else {
                cs.updateClickedPosition(e.getSceneX(), e.getSceneY());
                new NodeContextMenu(cs, this).show(this, e.getScreenX(), e.getScreenY());
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
        nodeNameText = new Text();
        nodeNameText.setX(radius + 2);
        nodeNameText.setY(radius + 2);
        this.getChildren().addAll(circle, nodeNameText);
        this.imports = new ArrayList<>();
        this.exports = new ArrayList<>();
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
        // cast because the node can only contain one Literal
        this.astLiteralExpr = (Literal) Compiler.compile(this);
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
                if (stmt instanceof Assignment) {
                    Assignment assignStmt = (Assignment) stmt;
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