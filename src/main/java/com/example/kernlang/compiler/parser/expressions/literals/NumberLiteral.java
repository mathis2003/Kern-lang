package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class NumberLiteral implements ASTNode {
    Double number;

    public NumberLiteral() {}

    public NumberLiteral(Double number) {
        this.number = number;
    }

    public Double getNumber() {
        return number;
    }

    @Override
    public String toString(String indent) {
        return "\n" + indent + number;
    }

    @Override
    public ASTNode deepcopy() {
        NumberLiteral result = new NumberLiteral();
        result.number = number.doubleValue();
        return result;
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        number = 0.0;
        if (!input2.equals("") && isDigit(input2.charAt(0))) {
            while (!input2.equals("") && isDigit(input2.charAt(0))) {
                number *= 10;
                number += input2.charAt(0) - '0';
                input2 = input2.substring(1);
            }
            if (!input2.equals("") && input2.charAt(0) == '.'){
                input2 = input2.substring(1);
                int divider = 10;
                double decimal;
                while (isDigit(input2.charAt(0))) {
                    decimal = input2.charAt(0) - '0';
                    number += decimal / divider;
                    divider *= 10;
                    input2 = input2.substring(1);
                }
            }
            return new ParseResult(Optional.of(this), input2, "");
        }
        return new ParseResult(Optional.empty(), input, "failed to parse number");
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        return this.number.equals(((NumberLiteral) obj).number);
    }
}
