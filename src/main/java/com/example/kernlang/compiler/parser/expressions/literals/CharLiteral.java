package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class CharLiteral implements ASTNode {

    Character literal;

    @Override
    public String toString(String indent) {
        return "\n" + indent + "'" + literal + "'";
    }


    public Character getLiteral() {
        return literal;
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("'")) {
            literal = input.charAt(1);
            input2 = input2.substring(2);
            if (input2.startsWith(("'"))) {
                input2 = input2.substring(1).stripLeading();
                return new ParseResult(Optional.of(this), input2, "");
            }
        }
        return new ParseResult(Optional.empty(), input, "failed to parse char literal");
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

        return this.literal.equals(((CharLiteral) obj).literal);
    }
}
