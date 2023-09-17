package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.ArrayLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.NumberLiteral;

import java.util.HashMap;
import java.util.Optional;

public class ArrayAccess implements ASTNode {
    ASTNode arrayExpr;
    ASTNode indexExpr;
    @Override
    public String toString(String indent) {
        return indent + "arrayaccess:\n" +
                indent + "\tarray expr:\n" + arrayExpr.toString(indent + "\t") + "\n" +
                indent + "\tindex expr:\n" + indexExpr.toString(indent + "\t") + "\n";
    }

    @Override
    public ParseResult parse(String input) {
        String originalInput = input;
        input = input.stripLeading();

        // parse the array expression of which an element is to be accessed
        ParseResult res;
        if (input.startsWith("[")) res = new ArrayLiteral().parse(input);
        else res = new IdentifierExpr().parse(input);

        if (res.syntaxNode().isPresent()) {
            arrayExpr = res.syntaxNode().get();
            input = res.leftOverString().stripLeading();
        } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");

        // parse the indexing
        if (input.startsWith("[")) {
            input = input.substring(1).stripLeading();
            res = new ComparisonTerm().parse(input);
            if (res.syntaxNode().isPresent()) {
                this.indexExpr = res.syntaxNode().get();
                input = res.leftOverString().stripLeading();
            } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");

            if (!input.startsWith("]")) return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");
            input = input.substring(1).stripLeading();

            return new ParseResult(Optional.of(this), input, "");

        }

        return new ParseResult(Optional.empty(), originalInput, "failed to parse record access");

    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return ((ArrayLiteral) this.arrayExpr.interpret(contextNode, additionalContext))
                .getElement((NumberLiteral) this.indexExpr.interpret(contextNode, additionalContext));
    }

    public void assignValue(ASTNode value, GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        ArrayLiteral arrayLit = (ArrayLiteral) arrayExpr.interpret(contextNode, additionalContext);

        arrayLit.setElement((NumberLiteral) this.indexExpr.interpret(contextNode, additionalContext), value);
    }
}
