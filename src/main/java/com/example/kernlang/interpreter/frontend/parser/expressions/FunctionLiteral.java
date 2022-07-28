package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.parser.statements.Stmt;

import java.util.ArrayList;

public class FunctionLiteral extends Expr {
    private final ArrayList<Stmt> statements = new ArrayList<>();
    private final ArrayList<String> paramIdentifiers = new ArrayList<>();

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);

        String args = "\n";
        for (String ident : paramIdentifiers) {
            args += tabs + "\t\tidentifier: " + ident + "\n";
        }

        String stmts = "\n";
        for (Stmt stmt : statements) {
            stmts += tabs + "\t\tstatement:\n" + stmt.toString(indent + 3) + "\n";
        }

        return "function:\n" + "\tparameters:" + args + "\tstatements" + stmts;
    }

    public void addParameter(String paramIdentifier) {
        paramIdentifiers.add(paramIdentifier);
    }

    public void addStmt(Stmt stmt) {
        statements.add(stmt);
    }

}
