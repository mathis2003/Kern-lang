package com.example.kernlang.interpreter.frontend.parser.statements;

import com.example.kernlang.interpreter.frontend.parser.ASTNode;

public abstract class Stmt implements ASTNode {
    public abstract String toString(int indent);
    protected String getTabs(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs;
    }
}
