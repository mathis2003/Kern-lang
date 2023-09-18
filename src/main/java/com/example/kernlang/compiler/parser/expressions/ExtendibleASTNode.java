package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class ExtendibleASTNode implements ASTNode {
    protected ASTNode expr;

    @Override
    public String toString(String indent) {
        return "\n" + expr.toString(indent);
    }

    private final ArrayList<Supplier<ASTNode>> clauses;

    private final String parseErrorString;

    public ASTNode getExpr() {
        return expr;
    }

    public ExtendibleASTNode(ArrayList<Supplier<ASTNode>> clauses, String parseErrorString) {
        this.clauses = clauses;
        this.parseErrorString = parseErrorString;
    }

    @Override
    public ParseResult parse(String input) {
        for (Supplier<ASTNode> clause : clauses) {
            ParseResult result = clause.get().parse(input);
            if (result.syntaxNode().isPresent()) {
                return result;
            }

        }
        return new ParseResult(Optional.empty(), input, parseErrorString);
    }
    @Override
    public ASTNode deepcopy() {
        // why on earth would anyone make copies of unevaluated expressions
        return this;
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        // not even necessary because this will never be called from anywhere
        return expr.interpret(contextNode, additionalContext);
    }
}
