package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.ast_visitors.GetPrettyPrintedExpr;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.expressions.RecordAccess;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.compiler.parser.expressions.IdentifierExpr;
import com.example.kernlang.compiler.parser.expressions.literals.UnitLiteral;

import java.util.HashMap;
import java.util.Optional;

public class Assignment implements ASTNode {

    private ASTNode assignedObj;
    private ASTNode expr;


    public String toString() {
        return "(assignment:\n" +
                "\tidentifier: " + GetPrettyPrintedExpr.of(assignedObj) + "\n" +
                "\tassigned expression:" + "\n" +
                GetPrettyPrintedExpr.of(expr) + ")";
    }

    public ASTNode getAssignedObj() {
        return assignedObj;
    }

    public ASTNode getExpr() {
        return expr;
    }

    public void assign(GraphNode assignedNode, GraphNode context, HashMap<String, ASTNode> additionalContext) {
        if (assignedObj instanceof IdentifierExpr) {
            assignedNode.setAstExpr(expr.interpret(context, additionalContext));
        } else if (assignedObj instanceof RecordAccess) {
            //assignRecord((RecordLiteral) assignedNode.getAST(), context, additionalContext, (RecordAccess) assignedObj);
        }
    }


    private void assignRecord(RecordLiteral assignedRecord, GraphNode context, HashMap<String, ASTNode> additionalContext, RecordAccess assignedObjExpr) {
        /*if (assignedObjExpr.getRightExpr() instanceof IdentifierExpr) {
            for (RecordLiteral.RecordField field : assignedRecord.getRecordFields()) {
                if (field.getIdentifier().equals(((IdentifierExpr)assignedObjExpr.getRightExpr()).getIdentifier())) {
                    field.setLiteral((Literal) expr.interpret(context, additionalContext));
                }
            }
        } else {
            for (RecordLiteral.RecordField field : assignedRecord.getRecordFields()) {
                if (field.getIdentifier().equals(((IdentifierExpr)((BinaryExpr)(assignedObjExpr.getRightExpr())).getLeftExpr()).getIdentifier())) {
                    assignRecord((RecordLiteral) field.getL(), context, additionalContext, (BinaryExpr) assignedObjExpr.getRightExpr());
                }
            }

        }*/

    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitAssignment(this);
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
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
                    return new ParseResult(Optional.of(this), exprParseRes.leftOverString(), "");
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
        } else if (assignedObj instanceof RecordAccess) {
            // this means that we assign to a record field
            //ident = ((IdentifierExpr)((BinaryExpr)assignStmt.getAssignedObj()).getLeftExpr()).getIdentifier();
        }
        for (GraphEdge edge : contextNode.getImports()) {
            if (edge.getEndNode().name.equals(ident)) {
                // note: the expressions in the function literal, are to be evaluated in that function's (callee's) context
                assign(edge.getEndNode(), contextNode, additionalContext);
            }
        }
        return new UnitLiteral();
    }
}
