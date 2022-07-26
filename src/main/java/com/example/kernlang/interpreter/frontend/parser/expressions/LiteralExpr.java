package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.lexer.Token;

public class LiteralExpr extends Expr {
    private final Token tok;

    public LiteralExpr(Token tok) {
        this.tok = tok;
    }

    public Token getTok() {
        return tok;
    }
}
