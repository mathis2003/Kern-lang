package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.NumberLiteral;

import java.util.HashMap;
import java.util.Optional;

public class MulDiv implements ASTNode {
    ASTNode left, right;
    char operator;

    @Override
    public String toString(String indent) {
        return "\n" + indent + "muldiv:" +
                "\n" + indent + "\toperator: " + operator +
                "\n" + left.toString(indent + "\t") +
                "\n" + right.toString(indent + "\t");
    }


    public char getOperator() {
        return operator;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }


    @Override
    public ParseResult parse(String input) {
        input = input.stripLeading();
        ParseResult parseLeftResult = new Factor().parse(input);
        if (parseLeftResult.syntaxNode().isPresent()) {
            left = parseLeftResult.syntaxNode().get();
            String leftOverString1 = parseLeftResult.leftOverString().stripLeading();
            if (leftOverString1.startsWith("*") || leftOverString1.startsWith("/")) {
                operator = leftOverString1.charAt(0);
                ParseResult parseRightResult = new Term().parse(leftOverString1.substring(1));
                if (parseRightResult.syntaxNode().isPresent()) {
                    right = parseRightResult.syntaxNode().get();
                    return new ParseResult(Optional.of(this), parseRightResult.leftOverString(), "");
                }
            }
        }
        return new ParseResult(Optional.empty(), "", "parsing Addition or Subtraction failed");

    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        if (operator == '*')
            return new NumberLiteral(
                    ((NumberLiteral)left.interpret(contextNode, additionalContext)).getNumber() *
                            ((NumberLiteral)right.interpret(contextNode, additionalContext)).getNumber());

        return new NumberLiteral(
                ((NumberLiteral)left.interpret(contextNode, additionalContext)).getNumber() /
                        ((NumberLiteral)right.interpret(contextNode, additionalContext)).getNumber());
    }

    @Override
    public ASTNode deepcopy() {
        // why on earth would anyone make copies of unevaluated expressions
        return this;
    }
}
