package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.lexer.Token;
import com.example.kernlang.interpreter.frontend.lexer.TokenType;

public class BinaryExpr extends Expr {

    private final Expr leftExpr;
    private final Token operator;
    private final Expr rightExpr;

    public BinaryExpr(Expr leftExpr, Token operator, Expr rightExpr) {
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
    }

    public String toString(int indent) {
        String tabs = getTabs(indent);

        return tabs + "expression (binary):\n" +
                tabs + "\toperator:" + "\n" +
                tabs + "\t\t" + operator.lexeme() + "\n" +
                tabs + "\tleft:\n" + leftExpr.toString(indent + 2) + "\n" +
                tabs + "\tright:\n" + rightExpr.toString(indent + 2) + "\n";
    }

    @Override
    public Literal interpret(GraphNode context) {
        Double left = ((Double)((LiteralExpr) leftExpr.interpret(context)).getTok().literal());
        Double right = ((Double)((LiteralExpr) rightExpr.interpret(context)).getTok().literal());
        switch (operator.tokenType()) {
            case TOK_PLUS -> {
                return new LiteralExpr(
                        new Token(
                                TokenType.TOK_NUMBER, "", left + right, context, -1
                        )
                );
            }
            case TOK_MINUS -> {
                return new LiteralExpr(
                    new Token(
                            TokenType.TOK_NUMBER, "", left - right, context, -1
                    )
            ); }
            case TOK_STAR -> {
                return new LiteralExpr(
                        new Token(
                            TokenType.TOK_NUMBER, "", left * right, context, -1
                        )
                );
            }
            case TOK_SLASH -> {
                return new LiteralExpr(
                    new Token(
                            TokenType.TOK_NUMBER, "", left / right, context, -1
                    )
                );
            }
            default -> { return null; }
        }
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
