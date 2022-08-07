package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;

import java.util.HashMap;

public class IdentifierExpr implements Expr {
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
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitIdentifierExpr(this);
    }

    public String getIdentifier() {
        return ident;
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
        if (additionalContext.containsKey(ident)) {
            // here, context and additionalContext will never be used since the expression is a Literal anyways
            return (additionalContext.get(ident)).interpret(context, additionalContext);
        } else {
            for (GraphEdge edge : context.getImports()) {
                GraphNode importNode = edge.getEndNode();
                if (importNode.getName().equals(ident)) {
                    // we give an empty hashmap, as the identifier's expression is to be evaluated in another context,
                    // but obviously we don't give arguments to an identifier expression
                    return (importNode.getAST()).interpret(importNode, new HashMap<>());
                }
            }
        }

        return null;
    }
}
