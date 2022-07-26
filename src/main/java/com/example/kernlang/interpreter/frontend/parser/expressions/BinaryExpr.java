package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.lexer.Token;

public class BinaryExpr extends Expr {

    private final Expr leftExpr;
    private final Token operator;
    private final Expr rightExpr;

    public BinaryExpr(Expr leftExpr, Token operator, Expr rightExpr) {
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
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
