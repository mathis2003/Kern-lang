package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.lexer.Token;
import com.example.kernlang.interpreter.frontend.lexer.TokenType;

import java.util.HashMap;

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
