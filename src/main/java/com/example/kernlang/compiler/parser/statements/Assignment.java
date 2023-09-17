package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.ArrayAccess;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.expressions.RecordAccess;
import com.example.kernlang.compiler.parser.expressions.IdentifierExpr;
import com.example.kernlang.compiler.parser.expressions.literals.UnitLiteral;

import java.util.HashMap;
import java.util.Optional;

public class Assignment implements ASTNode {

    private ASTNode assignedObj;
    private ASTNode expr;

    public ASTNode getAssignedObj() {
        return assignedObj;
    }

    public ASTNode getExpr() {
        return expr;
    }

    @Override
    public String toString(String indent) {
        return "\n\t" + indent + "assignment statement:" +
                "\n\t" + indent + "assigned object: " + assignedObj.toString(indent + "\t") +
                "\n\t" + indent + "assigned expr: " + expr.toString(indent + "\t") ;
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        ParseResult recordAccessParseRes = new RecordAccess().parse(input2);
        if (recordAccessParseRes.syntaxNode().isPresent()) {
            assignedObj = recordAccessParseRes.syntaxNode().get();
            input2 = recordAccessParseRes.leftOverString().stripLeading();
            if (input2.startsWith("<-")) {
                input2 = input2.substring(2).stripLeading();
                ParseResult exprParseRes = new Expr().parse(input2);
                if (exprParseRes.syntaxNode().isPresent()) {
                    expr = exprParseRes.syntaxNode().get();
                    input2 = exprParseRes.leftOverString().stripLeading();
                    return new ParseResult(Optional.of(this), input2, "");
                }
            }
        } else {
            ParseResult arrayParseRes = new ArrayAccess().parse(input2);
            if (arrayParseRes.syntaxNode().isPresent()) {
                assignedObj = arrayParseRes.syntaxNode().get();
                input2 = arrayParseRes.leftOverString().stripLeading();
                if (input2.startsWith("<-")) {
                    input2 = input2.substring(2).stripLeading();
                    ParseResult exprParseRes = new Expr().parse(input2);
                    if (exprParseRes.syntaxNode().isPresent()) {
                        expr = exprParseRes.syntaxNode().get();
                        input2 = exprParseRes.leftOverString().stripLeading();
                        return new ParseResult(Optional.of(this), input2, "");
                    }
                }
            } else {
                ParseResult identParseRes = new IdentifierExpr().parse(input2);
                if (identParseRes.syntaxNode().isPresent()) {
                    assignedObj = identParseRes.syntaxNode().get();
                    input2 = identParseRes.leftOverString().stripLeading();
                    if (input2.startsWith("<-")) {
                        input2 = input2.substring(2).stripLeading();
                        ParseResult exprParseRes = new Expr().parse(input2);
                        if (exprParseRes.syntaxNode().isPresent()) {
                            expr = exprParseRes.syntaxNode().get();
                            input2 = exprParseRes.leftOverString().stripLeading();
                            return new ParseResult(Optional.of(this), input2, "");
                        }
                    }
                }
            }
        }


        return new ParseResult(Optional.empty(), input,"failed to parse assignment");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        String ident = null;
        if (assignedObj instanceof IdentifierExpr) {
            ident = ((IdentifierExpr)assignedObj).getIdentifier();
            for (GraphEdge edge : contextNode.getImports()) {
                if (edge.getEndNode().name.equals(ident)) {
                    // note: the expressions in the function literal, are to be evaluated in that function's (callee's) context
                    edge.getEndNode().setAstExpr(expr.interpret(contextNode, additionalContext));
                }
            }
        } else if (assignedObj instanceof RecordAccess recordAccess) {
            // this means that we assign to a record field
            // thus, we're not changing the ASTNode of a GraphNode for a new one
            // we're making an update to the ASTNode's content
            recordAccess.assignValue(expr.interpret(contextNode, additionalContext), contextNode, additionalContext);
        } else if (assignedObj instanceof ArrayAccess arrayAccess) {
            // this means that we assign to a record field
            // thus, we're not changing the ASTNode of a GraphNode for a new one
            // we're making an update to the ASTNode's content
            arrayAccess.assignValue(expr.interpret(contextNode, additionalContext), contextNode, additionalContext);
        }

        return new UnitLiteral();
    }
}
