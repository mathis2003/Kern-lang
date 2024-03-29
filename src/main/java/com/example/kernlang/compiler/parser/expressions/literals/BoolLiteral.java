package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class BoolLiteral implements ASTNode {
    Boolean literal;

    @Override
    public String toString(String indent) {
        return "\n" + indent + literal;
    }

    @Override
    public ASTNode deepcopy() {
        BoolLiteral result = new BoolLiteral();
        result.literal = literal.booleanValue();
        return result;
    }

    public BoolLiteral() {}

    public BoolLiteral(Boolean literal) {
        this.literal = literal;
    }

    public Boolean getLiteral() {
        return literal;
    }


    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("true")) {
            input2 = input2.substring(4).stripLeading();
            literal = true;
            return new ParseResult(Optional.of(this), input2, "");
        } else if (input2.startsWith("false")) {
            input2 = input2.substring(5).stripLeading();
            literal = false;
            return new ParseResult(Optional.of(this), input2, "");
        }
        return new ParseResult(Optional.empty(), input, "failed to parse boolean literal");
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

        return this.literal.equals(((BoolLiteral) obj).literal);
    }
}
