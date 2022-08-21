package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;

import java.util.HashMap;

public interface Expr extends ASTNode {

    public abstract <R> R accept(ExprVisitor<R> visitor);

    public abstract Literal interpret(GraphNode contextNode, HashMap<String, Literal> additionalContext);
}
