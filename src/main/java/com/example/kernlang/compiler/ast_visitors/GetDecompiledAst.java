package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;

public class GetDecompiledAst implements ExprVisitor<String>{
    @Override
    public String visitFunctionLiteral(FunctionLiteral functionLiteral) {
        return null;
    }

    @Override
    public String visitLiteralExpr(LiteralExpr literalExpr) {
        return null;
    }

    @Override
    public String visitRecordLiteral(RecordLiteral recordLiteral) {
        return null;
    }

    @Override
    public String visitBinaryExpr(BinaryExpr binaryExpr) {
        return null;
    }

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
    public String visitUnaryExpr(UnaryExpr unaryExpr) {
        return null;
    }
}
