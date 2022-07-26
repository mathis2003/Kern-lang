package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.lexer.Token;

public class UnaryExpr extends Expr {
    private final Token operator;
    private final Expr expr;

    public UnaryExpr(Token operator, Expr expr) {
        this.operator = operator;
        this.expr = expr;
    }


    public Token getOperator() {
        return operator;
    }

    public Expr getExpr() {
        return expr;
    }
}
