package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.parser.statements.Assignment;
import com.example.kernlang.interpreter.frontend.parser.statements.ReturnStmt;
import com.example.kernlang.interpreter.frontend.parser.statements.Stmt;

import java.util.ArrayList;

public class FunctionCall extends Expr {
    private final Expr functionExpr;
    private final ArrayList<Expr> args;

    public FunctionCall(Expr functionExpr) {
        this.functionExpr = functionExpr;
        this.args = new ArrayList<>();
    }

    public void addArgument(Expr expr) {
        args.add(expr);
    }

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);

        String argsString = "\n";
        for (Expr expr : args) {
            argsString += expr.toString(indent + 2) + "\n";
        }

        return tabs + "function call:\n" +
                tabs + "\tfunction literal:\n" + functionExpr.toString(indent + 2) +
                tabs + "\targuments:\n" + argsString;
    }

    @Override
    public Literal interpret(GraphNode context) {
        for (Stmt stmt : ((FunctionLiteral) functionExpr ).getStatements()) {
            if (stmt instanceof Assignment) {
                Assignment assignStmt = (Assignment) stmt;
                for (GraphEdge edge : context.getImports()) {
                    if (edge.getEndNode().getName().equals(assignStmt.getIdentifier())) {
                        edge.getEndNode().setAstExpr(assignStmt.getExpr().interpret(context));
                    }
                }
            }
            else if (stmt instanceof ReturnStmt) {
                return ((ReturnStmt) stmt).getReturnExpr().interpret(context);
            }
        }
        return null;
    }
}
