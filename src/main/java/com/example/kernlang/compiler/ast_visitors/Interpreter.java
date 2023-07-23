package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.*;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.parser.statements.Statement;

/**
 * For now, the pattern used to interpret the ast is the "interpreter pattern" shown by the Gang of Four.
 * However, in the future we might want to delegate this fucntionality to the visitor pattern instead.
 * Hence this class is already setup, for now it does absolutely nothing but act as a reminder
 */

public class Interpreter implements ExprVisitor<Object> {
    @Override
    public Object visitFunctionLiteral(FunctionLiteral functionLiteral) {
        return functionLiteral;
    }

    /*@Override
    public Object visitLiteralExpr(LiteralExpr literalExpr) {
        return literalExpr.getTok().literal();
    }*/

    @Override
    public Object visitRecordLiteral(RecordLiteral recordLiteral) {
        return recordLiteral;
    }

    @Override
    public Object visitBoolLiteral(BoolLiteral boolLiteral) {
        return null;
    }

    @Override
    public Object visitCharLiteral(CharLiteral charLiteral) {
        return null;
    }

    @Override
    public Object visitNumberLiteral(NumberLiteral numberLiteral) {
        return null;
    }

    @Override
    public Object visitUnitLiteral(UnitLiteral unitLiteral) {
        return null;
    }

    @Override
    public Object visitAddSub(AddSub addSub) {
        return null;
    }

    @Override
    public Object visitBoolExpr(BoolExpr boolExpr) {
        return null;
    }

    @Override
    public Object visitBoolTerm(BoolTerm boolTerm) {
        return null;
    }

    @Override
    public Object visitComparison(Comparison comparison) {
        return null;
    }

    @Override
    public Object visitComparisonTerm(ComparisonTerm comparisonTerm) {
        return null;
    }

    @Override
    public Object visitEqualityExpr(EqualityExpr equalityExpr) {
        return null;
    }

    @Override
    public Object visitExpr(Expr expr) {
        return null;
    }

    @Override
    public Object visitFactor(Factor factor) {
        return null;
    }

    /*@Override
    public Object visitBinaryExpr(BinaryExpr binaryExpr) {
        return null;
    }*/

    @Override
    public Object visitFunctionCall(FunctionCall functionCall) {
        return null;
    }

    @Override
    public Object visitIdentifierExpr(IdentifierExpr identifierExpr) {
        return null;
    }

    @Override
    public Object visitIfExpr(IfExpr ifExpr) {
        return null;
    }

    @Override
    public Object visitLiteral(Literal literal) {
        return null;
    }

    @Override
    public Object visitMulDiv(MulDiv mulDiv) {
        return null;
    }

    @Override
    public Object visitParenExpr(ParenExpr parenExpr) {
        return null;
    }

    @Override
    public Object visitRecordAccess(RecordAccess recordAccess) {
        return null;
    }

    @Override
    public Object visitTerm(Term term) {
        return null;
    }

    @Override
    public Object visitUnaryExpr(UnaryExpr unaryExpr) {
        return null;
    }

    @Override
    public Object visitVariable(Variable variable) {
        return null;
    }

    @Override
    public Object visitAssignment(Assignment assignment) {
        return null;
    }

    @Override
    public Object visitReturnStmt(ReturnStmt returnStmt) {
        return null;
    }

    @Override
    public Object visitStatement(Statement statement) {
        return null;
    }

    private Object evaluate(ASTNode expr) {
        return expr.accept(this);
    }
}
