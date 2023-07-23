package com.example.kernlang.compiler.parser;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.expressions.Literal;

import java.util.HashMap;

public interface ASTNode {

    public abstract <R> R accept(ExprVisitor<R> visitor);
    ParseResult parse(String input);

    ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext);
}
