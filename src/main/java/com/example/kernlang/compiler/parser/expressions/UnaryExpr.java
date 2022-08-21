package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.lexer.Token;
import com.example.kernlang.compiler.lexer.TokenType;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;

import java.util.HashMap;

public class UnaryExpr implements Expr {
    private final Token operator;
    private final Expr expr;

    public UnaryExpr(Token operator, Expr expr) {
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitUnaryExpr(this);
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
        switch (operator.tokenType()) {
            case TOK_MINUS -> {
                Double negative =
                        -1 * ((Double) ((LiteralExpr) expr.interpret(context, additionalContext)).getTok().literal());
                return  new LiteralExpr(new Token(
                        TokenType.TOK_NUMBER, "", negative, context, -1
                ));
            }
            case TOK_DOLLAR_SIGN -> { return null; }
            case TOK_BANG -> { return null; }
            case TOK_AMPERSAND -> { return null; }
            default -> { return null; }
        }
    }


    public Token getOperator() {
        return operator;
    }

    public Expr getExpr() {
        return expr;
    }
}
