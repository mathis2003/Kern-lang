package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayLiteral implements ASTNode {
    ArrayList<ASTNode> elements = new ArrayList<>();

    @Override
    public String toString(String indent) {
        return indent + "array:\n" + elements
                .stream()
                .map(el -> el == null ? indent + "\tnull" : el.toString(indent + "\t"))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public ParseResult parse(String input) {
        String originalInput = input;
        input = input.stripLeading();
        if (input.startsWith("[")) {
            input = input.substring(1).stripLeading();
            while (!input.startsWith("]")) {
                ParseResult parseRes = new Expr().parse(input);
                if (parseRes.syntaxNode().isPresent()) {
                    elements.add(parseRes.syntaxNode().get());
                    input = parseRes.leftOverString().stripLeading();
                    if (input.startsWith(",")) input = input.substring(1).stripLeading();
                } else {
                    return new ParseResult(Optional.empty(),
                                            originalInput,
                                "failed to parse array literal");
                }
            }
            input = input.substring(1).stripLeading();
        } else return new ParseResult(Optional.empty(),
                                    originalInput,
                        "failed to parse array literal");
        return new ParseResult(Optional.of(this), input, "");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return this;
    }

    public ASTNode getElement(NumberLiteral idx) {
        return elements.get(idx.getNumber().intValue());
    }

    public void setElement(NumberLiteral idx, ASTNode value) {
        elements.set(idx.getNumber().intValue(), value);
    }
}
