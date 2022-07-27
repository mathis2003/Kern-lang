package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.lexer.Token;

public class BinaryExpr implements Expr {

    private final Expr leftExpr;
    private final Token operator;
    private final Expr rightExpr;

    public BinaryExpr(Expr leftExpr, Token operator, Expr rightExpr) {
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
    }

    public String toString(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs + "expression (binary):\n" +
                tabs + "\toperator:" + "\n" +
                tabs + "\t\t" + operator.lexeme() + "\n" +
                tabs + "\tleft:\n" + leftExpr.toString(indent + 2) + "\n" +
                tabs + "\tright:\n" + rightExpr.toString(indent + 2) + "\n";
    }

    public Expr getLeftExpr() {
        return leftExpr;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getRightExpr() {
        return rightExpr;
    }
}
