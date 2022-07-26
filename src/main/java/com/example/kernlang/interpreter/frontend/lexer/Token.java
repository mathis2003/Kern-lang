package com.example.kernlang.interpreter.frontend.lexer;

import com.example.kernlang.codebase_viewer.graph.GraphNode;

public record Token(TokenType tokenType, String lexeme, Object literal, GraphNode node, int line) {
    public String toString() {
        return "type: " + tokenType.name() + "\n" +
                "lexeme: " + lexeme +
                "in node: " + node +
                "at line: " + line;
    }
}
