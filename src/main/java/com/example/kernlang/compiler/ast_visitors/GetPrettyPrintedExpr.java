package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.*;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.parser.statements.Statement;

public class GetPrettyPrintedExpr implements ExprVisitor<String>{

    public static String of(ASTNode expr) {
        return indentSExpression(expr.accept(new GetPrettyPrintedExpr()));
    }

    public String print(ASTNode expr) {
        return indentSExpression(expr.accept(this));
    }

    private static String indentSExpression(String s) {
        String result = "";
        int indent = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                indent++;
                result += "\n";
                result += indentToTabs(indent);
            } else if (s.charAt(i) == ')') {
                indent--;
            }
            result += s.charAt(i);

            if (s.charAt(i) == '\n') {
                result += indentToTabs(indent);
            }
        }

        return result;
    }

    private static String indentToTabs(int amount) {
        String tabs = "";
        for (int i = 0; i < amount; i++) tabs += "\t";
        return tabs;
    }



    @Override
    public String visitFunctionLiteral(FunctionLiteral functionLiteral) {
        String args = "\n";
        for (String ident : functionLiteral.getParamIdentifiers()) {
            args += "\t\tidentifier: " + ident + "\n";
        }

        String stmts = "\n";
        for (Statement stmt : functionLiteral.getStatements()) {
            stmts += "\t\tstatement:\n" + stmt.toString() + "\n";
        }

        return "(" + "function:\n" + "\tparameters:" + args + "\tstatements" + stmts + ")";
    }

    @Override
    public String visitRecordLiteral(RecordLiteral recordLiteral) {
        String fieldStrings = "";
        for (RecordLiteral.RecordField recordField : recordLiteral.getRecordFields()) {
            fieldStrings += "\n" + recordField.toString();
        }

        return "(record:" + fieldStrings + ")";
    }

    @Override
    public String visitBoolLiteral(BoolLiteral boolLiteral) {
        return boolLiteral.getLiteral().toString();
    }

    @Override
    public String visitCharLiteral(CharLiteral charLiteral) {
        return charLiteral.getLiteral().toString();
    }

    @Override
    public String visitNumberLiteral(NumberLiteral numberLiteral) {
        return numberLiteral.getNumber().toString();
    }

    @Override
    public String visitUnitLiteral(UnitLiteral unitLiteral) {
        return "Unit";
    }

    @Override
    public String visitAddSub(AddSub addSub) {
        return parenthesize("" + addSub.getOperator(),
                    addSub.getLeft(), addSub.getRight());
    }

    @Override
    public String visitBoolExpr(BoolExpr boolExpr) {
        return boolExpr.accept(this);
    }

    @Override
    public String visitBoolTerm(BoolTerm boolTerm) {
        return boolTerm.accept(this);
    }

    @Override
    public String visitComparison(Comparison comparison) {
        return null;
    }

    @Override
    public String visitComparisonTerm(ComparisonTerm comparisonTerm) {
        return null;
    }

    @Override
    public String visitEqualityExpr(EqualityExpr equalityExpr) {
        return null;
    }

    @Override
    public String visitExpr(Expr expr) {
        return null;
    }

    @Override
    public String visitFactor(Factor factor) {
        return null;
    }

    @Override
    public String visitFunctionCall(FunctionCall functionCall) {
        String argsString = "\n";
        for (ASTNode expr : functionCall.getArgs()) {
            argsString += print(expr);
        }

        return "(function call:\n" +
                "\tfunction literal:\n" + print(functionCall.getFunctionExpr()) +
                "\targuments:\n" + argsString + ")";
    }

    @Override
    public String visitIdentifierExpr(IdentifierExpr identifierExpr) {
        return "identifier: " + identifierExpr.getIdentifier() + ")";
    }

    @Override
    public String visitIfExpr(IfExpr ifExpr) {
        return "(" + "if " + print(ifExpr.condition) +
                " then " + print(ifExpr.trueCaseExpr) +
                " else " +  print(ifExpr.falseCaseExpr) + ")";
    }

    @Override
    public String visitLiteral(Literal literal) {
        return null;
    }

    @Override
    public String visitMulDiv(MulDiv mulDiv) {
        return null;
    }

    @Override
    public String visitParenExpr(ParenExpr parenExpr) {
        return null;
    }

    @Override
    public String visitRecordAccess(RecordAccess recordAccess) {
        return null;
    }

    @Override
    public String visitTerm(Term term) {
        return null;
    }

    @Override
    public String visitUnaryExpr(UnaryExpr unaryExpr) {
        return parenthesize("" + unaryExpr.getOperator(),
                unaryExpr.getExpr());
    }

    @Override
    public String visitVariable(Variable variable) {
        return null;
    }

    @Override
    public String visitAssignment(Assignment assignment) {
        return null;
    }

    @Override
    public String visitReturnStmt(ReturnStmt returnStmt) {
        return null;
    }

    @Override
    public String visitStatement(Statement statement) {
        return null;
    }

    private String parenthesize(String name, ASTNode... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (ASTNode expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
}
