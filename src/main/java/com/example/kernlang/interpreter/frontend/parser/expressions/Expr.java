package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.parser.ASTNode;

public abstract class Expr implements ASTNode {
    public abstract String toString(int indent);
    protected String getTabs(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs;
    }

    public abstract Literal interpret(GraphNode context);
}
