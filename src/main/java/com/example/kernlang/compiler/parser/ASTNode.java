package com.example.kernlang.compiler.parser;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.expressions.Literal;

import java.util.HashMap;

public interface ASTNode {

    public String toString(String indent);

    public ASTNode deepcopy();

    ParseResult parse(String input);

    ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext);
}
