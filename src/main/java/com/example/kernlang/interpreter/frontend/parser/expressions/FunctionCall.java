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
        // getting the actual function literal
        FunctionLiteral fLit = null;
        if (! (functionExpr instanceof FunctionLiteral)) {
            fLit = (FunctionLiteral) functionExpr.interpret(context);
        } else {
            fLit = (FunctionLiteral) functionExpr;
        }

        // evaluating the args given with the function call
        for (int i = 0; i < args.size(); i++) {
            String argName = fLit.getParamIdentifiers().get(i);
            for (GraphEdge edge : fLit.getFunctionContext().getImports()) {
                GraphNode importNode = edge.getEndNode();
                if (importNode.getName().equals(argName)) {
                    // note: the arguments given with the function call, are to be evaluated in the caller's context
                    importNode.setAstExpr(args.get(i).interpret(context));
                }
            }
        }

        // statements
        for (Stmt stmt : fLit.getStatements()) {
            if (stmt instanceof Assignment) {
                Assignment assignStmt = (Assignment) stmt;
                for (GraphEdge edge : context.getImports()) {
                    GraphNode importNode = edge.getEndNode();
                    if (importNode.getName().equals(assignStmt.getIdentifier())) {
                        // note: the expressions in the function literal, are to be evaluated in that function's context
                        importNode.setAstExpr(assignStmt.getExpr().interpret(fLit.getFunctionContext()));
                    }
                }
            }
            else if (stmt instanceof ReturnStmt) {
                // note: the expressions in the function literal, are to be evaluated in that function's context
                return ((ReturnStmt) stmt).getReturnExpr().interpret(fLit.getFunctionContext());
            }
        }
        return null;
    }
}
