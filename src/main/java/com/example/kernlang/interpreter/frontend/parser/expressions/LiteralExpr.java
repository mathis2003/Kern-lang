package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.lexer.Token;

public class LiteralExpr extends Expr implements Literal {
    private final Token tok;

    public LiteralExpr(Token tok) {
        this.tok = tok;
    }

    public Token getTok() {
        return tok;
    }

    public String toString(int indent) {
        String tabs = getTabs(indent);

        return tabs + "expression (literal):\n" +
                tabs + "\ttype: " + tok.tokenType() + "\n" +
                tabs + "\tvalue: " + tok.literal() + "\n";
    }

    @Override
    public Literal interpret(GraphNode context) {
        return this;
    }
}
