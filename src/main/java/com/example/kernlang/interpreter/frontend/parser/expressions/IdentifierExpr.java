package com.example.kernlang.interpreter.frontend.parser.expressions;

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
}
