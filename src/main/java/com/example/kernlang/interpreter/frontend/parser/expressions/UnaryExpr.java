package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.lexer.Token;

public class UnaryExpr extends Expr {
    private final Token operator;
    private final Expr expr;

    public UnaryExpr(Token operator, Expr expr) {
        this.operator = operator;
        this.expr = expr;
    }

    public String toString(int indent) {
        String tabs = getTabs(indent);

        return tabs + "expression (unary):\n" +
                tabs + "\toperator: " + operator.lexeme() + "\n" +
                tabs + "\toperand: " + "\n" +
                expr.toString(indent + 2) + "\n";
    }


    public Token getOperator() {
        return operator;
    }

    public Expr getExpr() {
        return expr;
    }
}
