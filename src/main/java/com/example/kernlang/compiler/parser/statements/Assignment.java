package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.GetPrettyPrintedExpr;
import com.example.kernlang.compiler.parser.expressions.BinaryExpr;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.expressions.Literal;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.compiler.parser.expressions.IdentifierExpr;

import java.util.HashMap;

public class Assignment extends Stmt {

    private final Expr assignedObj;
    private final Expr expr;

    public Assignment(Expr assignedObj, Expr expr) {
        this.assignedObj = assignedObj;
        this.expr = expr;
    }

    public String toString() {
        return "(assignment:\n" +
                "\tidentifier: " + GetPrettyPrintedExpr.of(assignedObj) + "\n" +
                "\tassigned expression:" + "\n" +
                GetPrettyPrintedExpr.of(expr) + ")";
    }

    public Expr getAssignedObj() {
        return assignedObj;
    }

    public Expr getExpr() {
        return expr;
    }

    public void assign(GraphNode assignedNode, GraphNode context, HashMap<String, Literal> additionalContext) {
        if (assignedObj instanceof IdentifierExpr) {
            assignedNode.setAstExpr(expr.interpret(context, additionalContext));
        } else if (assignedObj instanceof BinaryExpr) {
            assignRecord((RecordLiteral) assignedNode.getAST(), context, additionalContext, (BinaryExpr) assignedObj);
        }
    }

    private void assignRecord(RecordLiteral assignedRecord, GraphNode context, HashMap<String, Literal> additionalContext, BinaryExpr assignedObjExpr) {
        if (assignedObjExpr.getRightExpr() instanceof IdentifierExpr) {
            for (RecordLiteral.RecordField field : assignedRecord.getRecordFields()) {
                if (field.getIdentifier().equals(((IdentifierExpr)assignedObjExpr.getRightExpr()).getIdentifier())) {
                    field.setLiteral(expr.interpret(context, additionalContext));
                }
            }
        } else {
            for (RecordLiteral.RecordField field : assignedRecord.getRecordFields()) {
                if (field.getIdentifier().equals(((IdentifierExpr)((BinaryExpr)(assignedObjExpr.getRightExpr())).getLeftExpr()).getIdentifier())) {
                    assignRecord((RecordLiteral) field.getL(), context, additionalContext, (BinaryExpr) assignedObjExpr.getRightExpr());
                }
            }

        }

    }
}
