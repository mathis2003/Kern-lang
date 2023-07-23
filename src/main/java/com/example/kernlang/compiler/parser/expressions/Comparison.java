package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.BoolLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.NumberLiteral;

import java.util.HashMap;
import java.util.Optional;

public class Comparison implements ASTNode {
    ASTNode left, right;
    char operator;
    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitComparison(this);
    }

    @Override
    public ParseResult parse(String input) {
        input = input.stripLeading();
        ParseResult parseLeftResult = new ComparisonTerm().parse(input);
        if (parseLeftResult.syntaxNode().isPresent()) {
            left = parseLeftResult.syntaxNode().get();
            String leftOverString1 = parseLeftResult.leftOverString().stripLeading();
            if (leftOverString1.startsWith("<") || leftOverString1.startsWith(">")) {
                operator = leftOverString1.charAt(0);
                ParseResult parseRightResult = new ComparisonTerm().parse(leftOverString1.substring(1));
                if (parseRightResult.syntaxNode().isPresent()) {
                    right = parseRightResult.syntaxNode().get();
                    return new ParseResult(Optional.of(this), parseRightResult.leftOverString(), "");
                }
            }
        }
        return new ParseResult(Optional.empty(), "", "parsing not equals failed");

    }

    @Override
    public BoolLiteral interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        if (operator == '>')
            return new BoolLiteral(
                    ((NumberLiteral)left.interpret(contextNode, additionalContext)).getNumber() >
                            ((NumberLiteral)right.interpret(contextNode, additionalContext)).getNumber());

        return new BoolLiteral(
                ((NumberLiteral)left.interpret(contextNode, additionalContext)).getNumber() <
                        ((NumberLiteral)right.interpret(contextNode, additionalContext)).getNumber());
    }
}
