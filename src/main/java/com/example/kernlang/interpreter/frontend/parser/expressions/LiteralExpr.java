package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.lexer.Token;

public class LiteralExpr implements Expr {
    private final Token tok;

    public LiteralExpr(Token tok) {
        this.tok = tok;
    }

    public Token getTok() {
        return tok;
    }

    public String toString(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        return tabs + "expression (literal):\n" +
                tabs + "\ttype: " + tok.tokenType() + "\n" +
                tabs + "\tvalue: " + tok.literal() + "\n";
    }
}
