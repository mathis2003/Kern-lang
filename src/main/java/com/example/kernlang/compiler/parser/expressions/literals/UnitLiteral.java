package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class UnitLiteral implements ASTNode {
    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return null;
    }

    @Override
    public String toString(String indent) {
        return "\n" + indent + "unit";
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("unit")) return new ParseResult(Optional.of(this), input2.substring(4).stripLeading(), "");

        else return new ParseResult(Optional.empty(), input, "failed to parse unit literal");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return this;
    }
}
