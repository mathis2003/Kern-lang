package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.Stmt;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionCall implements Expr {
    private final Expr functionExpr;
    private final ArrayList<Expr> args;

    public FunctionCall(Expr functionExpr) {
        this.functionExpr = functionExpr;
        this.args = new ArrayList<>();
    }

    public void addArgument(Expr expr) {
        args.add(expr);
    }

    public ArrayList<Expr> getArgs() {
        return args;
    }

    public Expr getFunctionExpr() {
        return functionExpr;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitFunctionCall(this);
    }

    @Override
    public Literal interpret(GraphNode contextNode, HashMap<String, Literal> additionalContext) {
        // getting the actual function literal
        FunctionLiteral fLit = null;
        if (! (functionExpr instanceof FunctionLiteral)) {
            fLit = (FunctionLiteral) functionExpr.interpret(contextNode, additionalContext);
        } else {
            fLit = (FunctionLiteral) functionExpr;
        }

        HashMap<String, Literal> argumentsHm = new HashMap<>();
        // evaluating the args given with the function call
        for (int i = 0; i < args.size(); i++) {
            String argName = fLit.getParamIdentifiers().get(i);
            // note: the arguments given with the function call, are to be evaluated in the caller's context
            argumentsHm.put(argName, args.get(i).interpret(contextNode, additionalContext));
        }

        // statements
        for (Stmt stmt : fLit.getStatements()) {
            if (stmt instanceof Assignment assignStmt) {
                String ident = null;
                if (assignStmt.getAssignedObj() instanceof IdentifierExpr) {
                    ident = ((IdentifierExpr)assignStmt.getAssignedObj()).getIdentifier();
                } else if (assignStmt.getAssignedObj() instanceof BinaryExpr) {
                    // this means that we assign to a record field
                    ident = ((IdentifierExpr)((BinaryExpr)assignStmt.getAssignedObj()).getLeftExpr()).getIdentifier();
                }
                for (GraphEdge edge : contextNode.getImports()) {
                    if (edge.getEndNode().name.equals(ident)) {
                        // note: the expressions in the function literal, are to be evaluated in that function's (callee's) context
                        assignStmt.assign(edge.getEndNode(), fLit.getFunctionContext(), argumentsHm);
                    }
                }
            }
            else if (stmt instanceof ReturnStmt) {
                // note: the expressions in the function literal, are to be evaluated in that function's context
                return ((ReturnStmt) stmt).getReturnExpr().interpret(fLit.getFunctionContext(), argumentsHm);
            }
        }
        return null;
    }
}
