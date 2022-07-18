package com.example.kernlang.interpreter.lexer;

import com.example.kernlang.codebase_viewer.graph.GraphNode;

public record Token(TokenType tokenType, String lexeme, Object literal, GraphNode node, int line) {
    public String getString() {
        return "type: " + tokenType.name() + "\n" +
                "lexeme: " + lexeme +
                "in node: " + node +
                "at line: " + line;
    }
}
