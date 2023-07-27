package com.example.kernlang.compiler.parser.language_extensions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;

import java.util.HashMap;
import java.util.Optional;

/**
 * needs to be added as a clause to Expr
 */

public class Quote implements ASTNode {
    ASTNode quotedExpr;
    @Override
    public String toString(String indent) {
        return "\n\tQuote: " + quotedExpr.toString(indent + "\t\t");
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("#")) {
            input2 = input2.substring(1).stripLeading();
            ParseResult parseResult = new Expr().parse(input2);
            if (parseResult.syntaxNode().isPresent()) {
                quotedExpr = parseResult.syntaxNode().get();
                return new ParseResult(Optional.of(this), parseResult.leftOverString().stripLeading(), "");
            }
        }
        return new ParseResult(Optional.empty(), input, "failed to parse quote expr");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return quotedExpr;
    }
}
