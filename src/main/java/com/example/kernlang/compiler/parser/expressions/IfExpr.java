package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.BoolLiteral;

import java.util.HashMap;
import java.util.Optional;

public class IfExpr implements ASTNode {
    public ASTNode condition;
    public ASTNode trueCaseExpr;
    public ASTNode falseCaseExpr;


    @Override
    public String toString(String indent) {
        return "\n\t" + indent + "ifexpr:" +
                "\n\t" + indent + "condition:" + condition.toString(indent + "\t") +
                "\n\t" + indent + "true case:" + trueCaseExpr.toString(indent + "\t") +
                "\n\t" + indent + "false case:" + falseCaseExpr.toString(indent + "\t");
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("if")) {
            input2 = input2.substring(2).stripLeading();
            ParseResult parseConditionResult = new BoolExpr().parse(input2);
            if (parseConditionResult.syntaxNode().isPresent()) {
                condition = parseConditionResult.syntaxNode().get();
                input2 = parseConditionResult.leftOverString().stripLeading();
                if (input2.startsWith("then")) {
                    input2 = input2.substring(4).stripLeading();
                    ParseResult parseTrueResult = new Expr().parse(input2);
                    if (parseTrueResult.syntaxNode().isPresent()) {
                        trueCaseExpr = parseTrueResult.syntaxNode().get();
                        input2 = parseTrueResult.leftOverString().stripLeading();
                        if (input2.startsWith("else")) {
                            input2 = input2.substring(4).stripLeading();
                            ParseResult parseFalseResult = new Expr().parse(input2);
                            if (parseFalseResult.syntaxNode().isPresent()) {
                                falseCaseExpr = parseFalseResult.syntaxNode().get();
                                return new ParseResult(Optional.of(this),
                                                        parseFalseResult.leftOverString().stripLeading(),
                                            "");
                            }
                        }
                    }
                }
            }

        }

        return new ParseResult(Optional.empty(), input, "failed to parse if-expression");
    }

    @Override
    public ASTNode interpret(GraphNode context, HashMap<String, ASTNode> additionalContext) {
        if (((BoolLiteral)condition.interpret(context, additionalContext)).getLiteral()) {
            return trueCaseExpr.interpret(context, additionalContext);
        } else {
            return falseCaseExpr.interpret(context, additionalContext);
        }
    }
}
