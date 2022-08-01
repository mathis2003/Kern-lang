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
        return null;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expr getExpr() {
        return expr;
    }
}
