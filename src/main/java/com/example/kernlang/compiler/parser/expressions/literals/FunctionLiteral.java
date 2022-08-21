package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.expressions.Literal;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.statements.Stmt;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionLiteral implements Expr, Literal {
    private final ArrayList<Stmt> statements = new ArrayList<>();
    private final ArrayList<String> paramIdentifiers = new ArrayList<>();
    private final GraphNode functionContext;

    public FunctionLiteral(GraphNode functionContext) {
        this.functionContext = functionContext;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitFunctionLiteral(this);
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
        return this;
    }

    public void addParameter(String paramIdentifier) {
        paramIdentifiers.add(paramIdentifier);
    }

    public ArrayList<String> getParamIdentifiers() { return paramIdentifiers; }

    public void addStmt(Stmt stmt) {
        statements.add(stmt);
    }

    public ArrayList<Stmt> getStatements() {
        return statements;
    }

    public GraphNode getFunctionContext() {
        return functionContext;
    }
}
