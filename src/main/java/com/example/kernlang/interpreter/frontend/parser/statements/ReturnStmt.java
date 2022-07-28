package com.example.kernlang.interpreter.frontend.parser.statements;

import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;

public class ReturnStmt extends Stmt {
    private final Expr returnExpr;

    public ReturnStmt(Expr returnExpr) {
        this.returnExpr = returnExpr;
    }

    public Expr getReturnExpr() {
        return returnExpr;
    }

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);
        return tabs + "return:\n" + returnExpr.toString(indent + 1);
    }
}
