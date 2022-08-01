package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;

public class IdentifierExpr extends Expr {
    private final String ident;

    public IdentifierExpr(String ident) {
        this.ident = ident;
    }

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);
        return tabs + "identifier: " + ident + "\n";
    }

    @Override
    public Literal interpret(GraphNode context) {
        for (GraphEdge edge : context.getImports()) {
            GraphNode importNode = edge.getEndNode();
            if (importNode.getName().equals(ident)) {
                return ((Expr)importNode.getAST()).interpret(importNode);
            }
        }
        return null;
    }
}
