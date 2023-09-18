package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;

import java.util.HashMap;
import java.util.Optional;

public class ReturnStmt implements ASTNode {
    private ASTNode returnExpr;


    public ASTNode getReturnExpr() {
        return returnExpr;
    }


    @Override
    public String toString(String indent) {
        return "\n\t" + indent + "return statement: " + returnExpr.toString(indent + "\t");
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("return")) {
            input2 = input2.substring(6).stripLeading();
            ParseResult parseRes = new Expr().parse(input2);
            if (parseRes.syntaxNode().isPresent()) {
                returnExpr = parseRes.syntaxNode().get();
                input2 = parseRes.leftOverString().stripLeading();
                return new ParseResult(Optional.of(this), input2, "");
            }
        }
        return new ParseResult(Optional.empty(), input, "failed to parse return statement");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        // note: the expressions in the function literal, are to be evaluated in that function's context
        return returnExpr.interpret(contextNode, additionalContext);
    }

    @Override
    public ASTNode deepcopy() {
        // no one's gonna make a copy of this
        return this;
    }
}
