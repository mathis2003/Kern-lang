package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class ParenExpr implements ASTNode {
    ASTNode expr;
    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitParenExpr(this);
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("(")) {
            ParseResult parseExprResult = new Expr().parse(input2.substring(1).stripLeading());
            if (parseExprResult.syntaxNode().isPresent()) {
                expr = parseExprResult.syntaxNode().get();
                input2 = parseExprResult.leftOverString().stripLeading();
                if (input2.startsWith(")")) {
                    return new ParseResult(Optional.of(this), input2.substring(1).stripLeading(), "");
                }
            }
        }
        return new ParseResult(Optional.empty(), "", "failed to parse parenthesized expression");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return expr.interpret(contextNode, additionalContext);
    }
}
