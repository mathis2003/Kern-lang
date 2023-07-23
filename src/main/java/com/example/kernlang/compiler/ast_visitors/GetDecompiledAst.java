package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.*;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.parser.statements.Statement;

public class GetDecompiledAst implements ExprVisitor<String>{
    @Override
    public String visitFunctionLiteral(FunctionLiteral functionLiteral) {
        return null;
    }

    /*@Override
    public String visitLiteralExpr(LiteralExpr literalExpr) {
        return null;
    }*/

    @Override
    public String visitRecordLiteral(RecordLiteral recordLiteral) {
        return null;
    }

    @Override
    public String visitBoolLiteral(BoolLiteral boolLiteral) {
        return null;
    }

    @Override
    public String visitCharLiteral(CharLiteral charLiteral) {
        return null;
    }

    @Override
    public String visitNumberLiteral(NumberLiteral numberLiteral) {
        return null;
    }

    @Override
    public String visitUnitLiteral(UnitLiteral unitLiteral) {
        return null;
    }

    @Override
    public String visitAddSub(AddSub addSub) {
        return null;
    }

    @Override
    public String visitBoolExpr(BoolExpr boolExpr) {
        return null;
    }

    @Override
    public String visitBoolTerm(BoolTerm boolTerm) {
        return null;
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

    /*@Override
    public String visitBinaryExpr(BinaryExpr binaryExpr) {
        return null;
    }*/

    @Override
    public String visitFunctionCall(FunctionCall functionCall) {
        return null;
    }

    @Override
    public String visitIdentifierExpr(IdentifierExpr identifierExpr) {
        return null;
    }

    @Override
    public String visitIfExpr(IfExpr ifExpr) {
        return null;
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
        return null;
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
}
