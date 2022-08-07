package com.example.kernlang.compiler.parser;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.Compiler;
import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.lexer.Token;
import com.example.kernlang.compiler.lexer.TokenType;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.Stmt;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private final GraphNode graphNode;
    private final ArrayList<String> parseErrors;
    private int current = 0;

    public Parser(List<Token> tokens, GraphNode graphNode) {
        this.tokens = tokens;
        this.graphNode = graphNode;
        this.parseErrors = new ArrayList<>();
    }


    /** Parse functions **/

    public Expr parseExpression() {
        if (tokens.get(0).tokenType() == TokenType.TOK_EOF) {
            throw Compiler.error(graphNode, peek().line(), "trying to compile empty node");
        }
        return parseBoolLogic();
    }

    private Expr parseBoolLogic() {
        Expr expr = parseEquality();

        while (match(TokenType.TOK_EQUAL_EQUAL, TokenType.TOK_BANG_EQUAL)) {
            Token operator = previous();
            Expr right = parseBoolLogic();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseEquality() {
        Expr expr = parseComparison();

        while (match(TokenType.TOK_LESS, TokenType.TOK_GREATER)) {
            Token operator = previous();
            Expr right = parseEquality();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseComparison() {
        Expr expr = parseTerm();

        while (match(TokenType.TOK_PLUS, TokenType.TOK_MINUS)) {
            Token operator = previous();
            Expr right = parseComparison();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseTerm() {
        Expr expr = parseFactor();

        while (match(TokenType.TOK_STAR, TokenType.TOK_SLASH)) {
            Token operator = previous();
            Expr right = parseTerm();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseFactor() {
        Expr expr = parseUnary();

        while (match(TokenType.TOK_DOT)) {
            Token operator = previous();
            Expr right = parseFactor();
            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr parseUnary() {
        Expr e = null;

        switch (peek().tokenType()) {
            case TOK_UNIT, TOK_TRUE, TOK_FALSE, TOK_CHAR, TOK_NUMBER -> { return (Expr) parseLiteral(); }
            case TOK_BANG, TOK_AMPERSAND, TOK_DOLLAR_SIGN -> {
                    Token operator = advance();
                    e = new UnaryExpr(operator, parseUnary());
            }
            case TOK_IF -> {
                return parseIfExpr();
            }
            case TOK_IDENTIFIER -> {
                e = new IdentifierExpr(peek().lexeme());
                advance();
            }
            case TOK_OPEN_CURLY -> { return parseRecord(); }
            case TOK_BACKSLASH -> { return parseFunctionLiteral(); }
            case TOK_PERCENT -> { return parseFunctionCall(); }
            case TOK_OPEN_PAREN -> {
                advance(); // skip over open paren (
                e = parseExpression();
                if (!match(TokenType.TOK_CLOSE_PAREN)) {
                    throw Compiler.error(graphNode, peek().line(), "closing parenthesis expected");
                }
            }
            default -> { throw Compiler.error(peek().node(), peek().line(), "unary expression or literal expected"); }
        }

        return e;
    }

    private Expr parseIfExpr() {
        advance(); // skip over "if" token

        Expr condExpr = parseExpression();

        if (!match(TokenType.TOK_THEN)) {
            throw Compiler.error(graphNode, peek().line(), "keyword \"then\" expected after if with conditional expression");
        }

        Expr trueCase = parseExpression();

        if (!match(TokenType.TOK_ELSE)) {
            throw Compiler.error(graphNode, peek().line(), "keyword \"else\" expected after if [expression] then [expression] ");
        }

        Expr falseCase = parseExpression();

        return new IfExpr(condExpr, trueCase, falseCase);
    }

    public Literal parseLiteral() {
        switch (peek().tokenType()) {
            case TOK_UNIT, TOK_TRUE, TOK_FALSE, TOK_CHAR, TOK_NUMBER -> { return parseLiteralExpr(); }
            case TOK_OPEN_CURLY -> { return parseRecord(); }
            case TOK_BACKSLASH -> { return parseFunctionLiteral(); }
            default -> { throw Compiler.error(graphNode, peek().line(), "unary expression or literal expected"); }
        }
    }

    private LiteralExpr parseLiteralExpr() {
        return new LiteralExpr(advance());
    }

    private RecordLiteral parseRecord() {
        advance(); // skip over open curly {

        RecordLiteral result = new RecordLiteral();

        while (match(TokenType.TOK_IDENTIFIER)) {
            String identifier = previous().lexeme();
            if (match(TokenType.TOK_LEFT_ARROW)) {
                result.addRecordField(identifier, parseLiteral());
            } else {
                throw Compiler.error(graphNode, peek().line(), "leftarrow expected after identifier in record definition");
            }
        }

        if (!match(TokenType.TOK_CLOSE_CURLY))
            throw Compiler.error(graphNode, peek().line(), "no closing curly bracket at end of record definition");

        return result;
    }

    private FunctionCall parseFunctionCall() {
        advance(); // skip over percent sign %

        FunctionCall functionCall = new FunctionCall(parseExpression());

        if (!match(TokenType.TOK_OPEN_PAREN))  throw Compiler.error(graphNode, peek().line(), "open parenthesis expected");

        // parse arguments, when a closing paren is found, it skips over that and stops parsing arguments
        while (!match(TokenType.TOK_CLOSE_PAREN)) functionCall.addArgument(parseExpression());

        return functionCall;
    }

    private FunctionLiteral parseFunctionLiteral() {
        advance(); // skip over backslash \

        FunctionLiteral result = new FunctionLiteral(tokens.get(0).node());

        // parse function parameters
        while (match(TokenType.TOK_IDENTIFIER)) result.addParameter(previous().lexeme());

        // expect "-> {" after parameters
        if (!match(TokenType.TOK_RIGHT_ARROW))
            throw Compiler.error(graphNode, peek().line(), "right arrow axpected after paramterlist of function literal");

        if (!match(TokenType.TOK_OPEN_CURLY))  throw Compiler.error(graphNode, peek().line(), "open curly bracket expected");

        // parse statements, when it finds a "}", it skips that token and stops parsing statements
        while (!match(TokenType.TOK_CLOSE_CURLY)) result.addStmt(parseStmt());

        return result;
    }

    private Stmt parseStmt() {
        Stmt result = null;
        switch (peek().tokenType()) {
            case TOK_RETURN -> {
                advance(); // skip over "return" keyword
                result = new ReturnStmt(parseExpression());
            }
            default -> {
                Expr expr = parseExpression();
                if (match(TokenType.TOK_LEFT_ARROW)) {
                    result = new Assignment(expr, parseExpression());
                } else {
                    throw Compiler.error(graphNode, peek().line(), "invalid statement");
                }
            }
        }

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

    private Token currentToken() {
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

        throw Compiler.error(graphNode, peek().line(), message);
    }
}
