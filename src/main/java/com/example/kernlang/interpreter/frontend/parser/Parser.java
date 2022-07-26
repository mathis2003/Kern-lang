package com.example.kernlang.interpreter.frontend.parser;

import com.example.kernlang.interpreter.Interpreter;
import com.example.kernlang.interpreter.frontend.lexer.Token;
import com.example.kernlang.interpreter.frontend.lexer.TokenType;
import com.example.kernlang.interpreter.frontend.parser.expressions.BinaryExpr;
import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;
import com.example.kernlang.interpreter.frontend.parser.expressions.LiteralExpr;
import com.example.kernlang.interpreter.frontend.parser.statements.Stmt;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /*public ASTNode parseFunctionNode() {

    }*/

    /*public void parseRecordNode() {

    }*/

    /*public void parseVariantNode() {

    }*/

    public LiteralExpr parsePrimitiveNode() {
        // a literal node will have two tokens: the token of the literal itself and a EOF token
        if (tokens.size() == 2) {
            return new LiteralExpr(tokens.get(0));
        } else {
            // error
            return null;
        }
    }


    /** Parse functions **/

    private Stmt parseAssignment() {
        Stmt result = null;

        return result;
    }

    private Expr parseExpression() {
        return parseEquality();
    }

    private Expr parseEquality() {
        Expr expr = parseComparison();

        while (match(TokenType.TOK_BANG_EQUAL, TokenType.TOK_EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = parseComparison();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseComparison() {
        Expr expr = parseTerm();

        while (match(TokenType.TOK_PLUS, TokenType.TOK_MINUS)) {
            Token operator = previous();
            Expr right = parseTerm();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseTerm() {
        Expr expr = parseFactor();

        while (match(TokenType.TOK_STAR, TokenType.TOK_SLASH)) {
            Token operator = previous();
            Expr right = parseFactor();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseFactor() {
        // expect a literal such as 5, 10, 7, ...
        Expr e = switch (peek().tokenType()) {
            case TOK_NUMBER -> new LiteralExpr(peek());
            case TOK_BANG -> new LiteralExpr(peek());
            case TOK_AMPERSAND -> new LiteralExpr(peek());
            case TOK_DOLLAR_SIGN -> new LiteralExpr(peek());
            /*case TOK_OPEN_PAREN -> {
                Expr expr = parseExpression();
                consume(TokenType.TOK_CLOSE_PAREN, "Expect ')' after expression.");
                e = expr;
            }*/
            default -> new Expr();
        };

        return e;
    }

    /*private Expr parseUnary() {

    }*/

    private Expr parseFunctionLiteral() {
        Expr result = null;

        return result;
    }



    /** HELPER FUNCTIONS **/

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().tokenType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().tokenType() == TokenType.TOK_EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private ParseError error(Token token, String message) {
        Interpreter.error(token.node(), token.line(), message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {}
}
