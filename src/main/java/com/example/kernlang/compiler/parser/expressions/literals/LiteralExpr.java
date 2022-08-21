package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.lexer.Token;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.expressions.Literal;

import java.util.HashMap;

public class LiteralExpr implements Expr, Literal {
    private final Token tok;

    public LiteralExpr(Token tok) {
        this.tok = tok;
    }

    public Token getTok() {
        return tok;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitLiteralExpr(this);
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
        return this;
    }
}
