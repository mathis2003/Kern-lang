package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;

import java.util.HashMap;

public interface Expr extends ASTNode {
    public abstract String toString(int indent);
    public default String getTabs(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs;
    }

    public abstract <R> R accept(ExprVisitor<R> visitor);

    public abstract Literal interpret(GraphNode contextNode, HashMap<String, Literal> additionalContext);
}
