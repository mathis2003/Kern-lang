package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.compiler.parser.statements.Stmt;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;

public class GetPrettyPrintedExpr implements ExprVisitor<String>{

    public static String of(Expr expr) {
        return expr.accept(new GetPrettyPrintedExpr());
    }

    public String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitFunctionLiteral(FunctionLiteral functionLiteral) {
        String args = "\n";
        for (String ident : functionLiteral.getParamIdentifiers()) {
            args += "\t\tidentifier: " + ident + "\n";
        }

        String stmts = "\n";
        for (Stmt stmt : functionLiteral.getStatements()) {
            stmts += "\t\tstatement:\n" + stmt.toString() + "\n";
        }

        return "(" + "function:\n" + "\tparameters:" + args + "\tstatements" + stmts + ")";
    }

    @Override
    public String visitLiteralExpr(LiteralExpr literalExpr) {
        return "(literal:\n" +
                "\ttype: " + literalExpr.getTok().tokenType() + "\n" +
                "\tvalue: " + literalExpr.getTok().literal() + ")";
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
    public String visitBinaryExpr(BinaryExpr binaryExpr) {
        return parenthesize(binaryExpr.getOperator().lexeme(),
                binaryExpr.getLeftExpr(), binaryExpr.getRightExpr());
    }

    @Override
    public String visitFunctionCall(FunctionCall functionCall) {
        String argsString = "\n";
        for (Expr expr : functionCall.getArgs()) {
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
    public String visitUnaryExpr(UnaryExpr unaryExpr) {
        return parenthesize(unaryExpr.getOperator().lexeme(),
                unaryExpr.getExpr());
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
}
