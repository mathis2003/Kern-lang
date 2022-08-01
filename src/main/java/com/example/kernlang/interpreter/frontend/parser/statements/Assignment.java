package com.example.kernlang.interpreter.frontend.parser.statements;

import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;

public class Assignment extends Stmt {

    private final String identifier;
    private final Expr expr;

    public Assignment(String identifier, Expr expr) {
        this.identifier = identifier;
        this.expr = expr;
    }

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);
        return tabs + "assignment:\n" +
                tabs + "\tidentifier: " + identifier + "\n" +
                tabs + "\tassigned expression:" + "\n" +
                expr.toString(indent + 2);
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expr getExpr() {
        return expr;
    }
}
