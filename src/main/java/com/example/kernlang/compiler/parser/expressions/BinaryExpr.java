package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.lexer.Token;
import com.example.kernlang.compiler.lexer.TokenType;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;

import java.util.HashMap;

public class BinaryExpr implements Expr {

    private final Expr leftExpr;
    private final Token operator;
    private final Expr rightExpr;

    public BinaryExpr(Expr leftExpr, Token operator, Expr rightExpr) {
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitBinaryExpr(this);
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
        switch (operator.tokenType()) {
            case TOK_PLUS -> {
                Double left = ((Double)((LiteralExpr) leftExpr.interpret(context, additionalContext)).getTok().literal());
                Double right = ((Double)((LiteralExpr) rightExpr.interpret(context, additionalContext)).getTok().literal());
                return new LiteralExpr(
                        new Token(
                                TokenType.TOK_NUMBER, "", left + right, context, -1
                        )
                );
            }
            case TOK_MINUS -> {
                Double left = ((Double)((LiteralExpr) leftExpr.interpret(context, additionalContext)).getTok().literal());
                Double right = ((Double)((LiteralExpr) rightExpr.interpret(context, additionalContext)).getTok().literal());
                return new LiteralExpr(
                    new Token(
                            TokenType.TOK_NUMBER, "", left - right, context, -1
                    )
            ); }
            case TOK_STAR -> {
                Double left = ((Double)((LiteralExpr) leftExpr.interpret(context, additionalContext)).getTok().literal());
                Double right = ((Double)((LiteralExpr) rightExpr.interpret(context, additionalContext)).getTok().literal());
                return new LiteralExpr(
                        new Token(
                            TokenType.TOK_NUMBER, "", left * right, context, -1
                        )
                );
            }
            case TOK_SLASH -> {
                Double left = ((Double)((LiteralExpr) leftExpr.interpret(context, additionalContext)).getTok().literal());
                Double right = ((Double)((LiteralExpr) rightExpr.interpret(context, additionalContext)).getTok().literal());
                return new LiteralExpr(
                    new Token(
                            TokenType.TOK_NUMBER, "", left / right, context, -1
                    )
                );
            }
            case TOK_DOT -> {
                RecordLiteral left = (RecordLiteral) leftExpr.interpret(context, additionalContext);
                String ident = "";
                if (rightExpr instanceof BinaryExpr) {
                    ident = ((IdentifierExpr) ((BinaryExpr) rightExpr).leftExpr).getIdentifier();
                } else {
                    ident = ((IdentifierExpr) rightExpr).getIdentifier();
                }

                for (RecordLiteral.RecordField field : left.getRecordFields()) {
                    if (field.getIdentifier().equals(ident)) {
                        return ((Expr)field.getL()).interpret(context, additionalContext);
                    }
                }

                return null;
            }
            case TOK_GREATER -> {
                Double left = ((Double)((LiteralExpr) leftExpr.interpret(context, additionalContext)).getTok().literal());
                Double right = ((Double)((LiteralExpr) rightExpr.interpret(context, additionalContext)).getTok().literal());
                return new LiteralExpr(
                        new Token(
                                TokenType.TOK_BOOL, "", left > right, context, -1
                        )
                );
            }
            case TOK_LESS -> {
                Double left = ((Double)((LiteralExpr) leftExpr.interpret(context, additionalContext)).getTok().literal());
                Double right = ((Double)((LiteralExpr) rightExpr.interpret(context, additionalContext)).getTok().literal());
                return new LiteralExpr(
                        new Token(
                                TokenType.TOK_BOOL, "", left < right, context, -1
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