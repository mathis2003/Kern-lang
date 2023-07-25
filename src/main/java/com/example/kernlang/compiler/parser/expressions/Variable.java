package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;

import java.util.HashMap;
import java.util.Optional;

public class Variable implements ASTNode {
    String ident;

    @Override
    public String toString(String indent) {
        return "\n\t" + indent + "variable expr: " + ident;
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        String varName = "";
        if (isAlpha(input2.charAt(0))) varName += input2.charAt(0);
        else return new ParseResult(Optional.empty(), input, "failed to parse variable expression");

        int i = 1;
        while (isAlpha(input2.charAt(i)) || isDigit(input2.charAt(i))) varName += input2.charAt(i);

        this.ident = varName;

        return new ParseResult(Optional.of(this), input2.substring(i), "");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        if (additionalContext.containsKey(ident)) {
            // here, context and additionalContext will never be used since the expression is a Literal anyways
            return (additionalContext.get(ident)).interpret(contextNode, additionalContext);
        } else {
            for (GraphEdge edge : contextNode.getImports()) {
                GraphNode importNode = edge.getEndNode();
                if (importNode.getName().equals(ident)) {
                    // we give an empty hashmap, as the identifier's expression is to be evaluated in another context,
                    // but obviously we don't give arguments to an identifier expression
                    return importNode.getAST().interpret(importNode, new HashMap<>());
                }
            }
        }
        return null;
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}
