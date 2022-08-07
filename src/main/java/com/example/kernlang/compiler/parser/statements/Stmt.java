package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.compiler.parser.ASTNode;

public abstract class Stmt implements ASTNode {
    public abstract String toString(int indent);
    protected String getTabs(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs;
    }
}
