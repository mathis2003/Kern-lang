package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.BoolLiteral;

import java.util.HashMap;
import java.util.Optional;

public class EqualityExpr implements ASTNode {
    ASTNode left, right;
    String operator;

    @Override
    public String toString(String indent) {
        return "\n" + indent + "equalityexpr:" +
                "\n\t" + "operator: " + operator +
                "\n" + left.toString(indent + "\t") +
                "\n" + right.toString(indent + "\t") ;
    }

    @Override
    public ParseResult parse(String input) {
        input = input.stripLeading();
        ParseResult parseLeftResult = new BoolTerm().parse(input);
        if (parseLeftResult.syntaxNode().isPresent()) {
            left = parseLeftResult.syntaxNode().get();
            String leftOverString1 = parseLeftResult.leftOverString().stripLeading();
            if (leftOverString1.startsWith("==") || leftOverString1.startsWith("!=")) {
                operator = leftOverString1.substring(0,2);
                ParseResult parseRightResult = new BoolExpr().parse(leftOverString1.substring(2));
                if (parseRightResult.syntaxNode().isPresent()) {
                    right = parseRightResult.syntaxNode().get();
                    return new ParseResult(Optional.of(this), parseRightResult.leftOverString(), "");
                }

            }
        }
        return new ParseResult(Optional.empty(), "", "parsing equals failed");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        if (operator.equals("=="))
            return new BoolLiteral(left.interpret(contextNode, additionalContext)
                .equals(right.interpret(contextNode, additionalContext)));

        return new BoolLiteral(left.interpret(contextNode, additionalContext)
                .equals(right.interpret(contextNode, additionalContext)));
    }
}
