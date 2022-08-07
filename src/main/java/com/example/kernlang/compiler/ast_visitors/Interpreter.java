package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;

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

    @Override
    public Object visitLiteralExpr(LiteralExpr literalExpr) {
        return literalExpr.getTok().literal();
    }

    @Override
    public Object visitRecordLiteral(RecordLiteral recordLiteral) {
        return recordLiteral;
    }

    @Override
    public Object visitBinaryExpr(BinaryExpr binaryExpr) {
        return null;
    }

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
    public Object visitUnaryExpr(UnaryExpr unaryExpr) {
        return null;
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }
}
