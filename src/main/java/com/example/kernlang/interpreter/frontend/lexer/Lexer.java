package com.example.kernlang.interpreter.frontend.lexer;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
    private int start = 0;
    private int current = 0;
    private int line = 0;
    private String source;
    private GraphNode graphNode;

    private final ArrayList<Token> tokens = new ArrayList<>();

    public Lexer(String source, GraphNode graphNode) {
        this.source = source;
        this.graphNode = graphNode;
    }

    public ArrayList<Token> lexCode() {
        while (!isAtEnd()) {
            start = current;
            lexToken();
        }

        tokens.add(new Token(TokenType.TOK_EOF, "", null, null, line));
        return tokens;
    }

    private void lexToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.TOK_OPEN_PAREN); break;
            case ')': addToken(TokenType.TOK_CLOSE_PAREN); break;
            case '{': addToken(TokenType.TOK_OPEN_CURLY); break;
            case '}': addToken(TokenType.TOK_CLOSE_CURLY); break;
            case '.': addToken(TokenType.TOK_DOT); break;
            case '-': {
                if (match('-')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.TOK_MINUS);
                }
                break;
            }
            case '\'': {
                char charLiteral = advance();
                if (match('\'')) {
                    addToken(TokenType.TOK_CHAR, charLiteral);
                } else {
                    Interpreter.error(graphNode, line, "char literal not closed by a quote");
                }
                break;
            }
            case '+': addToken(TokenType.TOK_PLUS); break;
            case ';': addToken(TokenType.TOK_SEMI_COLON); break;
            case '*': addToken(TokenType.TOK_STAR); break;
            case '<': addToken(TokenType.TOK_LESS);  break;
            case '>': addToken(TokenType.TOK_GREATER); break;
            case '&': addToken(TokenType.TOK_AMPERSAND); break;
            case '$': addToken(TokenType.TOK_DOLLAR_SIGN); break;
            case '!':
                addToken(match('=') ? TokenType.TOK_BANG_EQUAL : TokenType.TOK_BANG);
                break;
            case '=': {
                addToken(match('=') ? TokenType.TOK_EQUAL_EQUAL : TokenType.TOK_EQUAL);
                break;
            }

            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Interpreter.error(graphNode, line, "Unexpected character.");
                }
                break;
        }
    }

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    TokenType.TOK_AND);
        keywords.put("else",   TokenType.TOK_ELSE);
        keywords.put("false",  TokenType.TOK_FALSE);
        keywords.put("if",     TokenType.TOK_IF);
        keywords.put("or",     TokenType.TOK_OR);
        keywords.put("return", TokenType.TOK_RETURN);
        keywords.put("this",   TokenType.TOK_THIS);
        keywords.put("true",   TokenType.TOK_TRUE);
        keywords.put("unit",   TokenType.TOK_UNIT);
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.TOK_IDENTIFIER;
        addToken(type);

    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(TokenType.TOK_NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, graphNode, line));
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }
}
