package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.compiler.ast_visitors.GetPrettyPrintedExpr;
import com.example.kernlang.compiler.parser.expressions.Expr;

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

    public String toString() {
        return "(return:\n" + GetPrettyPrintedExpr.of(returnExpr) + ")";
    }
}
