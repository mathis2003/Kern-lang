package com.example.kernlang.interpreter.frontend.parser.expressions;

import java.util.ArrayList;

public class FunctionCall extends Expr {
    private final Expr functionExpr;
    private final ArrayList<Expr> args;

    public FunctionCall(Expr functionExpr) {
        this.functionExpr = functionExpr;
        this.args = new ArrayList<>();
    }

    public void addArgument(Expr expr) {
        args.add(expr);
    }

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);

        String argsString = "\n";
        for (Expr expr : args) {
            argsString += expr.toString(indent + 2) + "\n";
        }

        return tabs + "function call:\n" +
                tabs + "\tfunction literal:\n" + functionExpr.toString(indent + 2) +
                tabs + "\targuments:\n" + argsString;
    }
}
