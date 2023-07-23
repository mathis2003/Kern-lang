package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class RecordAccess implements ASTNode {
    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitRecordAccess(this);
    }

    @Override
    public ParseResult parse(String input) {
        return new ParseResult(Optional.empty(), input, "failed to parse record access");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return null;
    }
}
