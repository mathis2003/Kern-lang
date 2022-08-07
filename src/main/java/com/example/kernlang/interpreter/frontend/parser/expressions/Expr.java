package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.ast_visitors.ExprVisitor;
import com.example.kernlang.interpreter.frontend.parser.ASTNode;

import java.util.HashMap;

public abstract class Expr implements ASTNode {
    public abstract String toString(int indent);
    protected static String getTabs(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs;
    }

    public abstract <R> R accept(ExprVisitor<R> visitor);

    public abstract Literal interpret(GraphNode contextNode, HashMap<String, Literal> additionalContext);
}
