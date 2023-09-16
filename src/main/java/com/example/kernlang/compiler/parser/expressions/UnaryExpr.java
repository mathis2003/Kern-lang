package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.NumberLiteral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class UnaryExpr implements ASTNode {
    private char operator;
    private ASTNode expr;

    private ArrayList<Character> unaryOps = new ArrayList<>(Arrays.asList('!', '-', '&', '$'));


    @Override
    public String toString(String indent) {
        return "\n\t" + indent + "unary expr:" + expr.toString(indent + "\t");
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        operator = input2.charAt(0);
        if (!unaryOps.contains(operator))
            return new ParseResult(Optional.empty(), input, "failed to parse unary");

        input2 = input2.substring(1);
        ParseResult parseResult = new Factor().parse(input2);
        if (parseResult.syntaxNode().isPresent()) {
            expr = parseResult.syntaxNode().get();
            return new ParseResult(Optional.of(this), parseResult.leftOverString(), "");
        }

        return new ParseResult(Optional.empty(), input, "failed to parse unary");

    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, ASTNode> additionalContext) {
        switch (operator) {
            case '-' -> {
                Double num = -1 * ((NumberLiteral) (expr.interpret(context, additionalContext))).getNumber();
                return new Literal(new NumberLiteral(num));
            }
            case '$' -> { return null; }
            case '!' -> { return null; }
            case '&' -> { return null; }
            default -> { return null; }
        }
    }


    public char getOperator() {
        return operator;
    }

    public ASTNode getExpr() {
        return expr;
    }
}
