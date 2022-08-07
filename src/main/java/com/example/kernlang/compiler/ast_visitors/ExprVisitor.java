package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;


public interface ExprVisitor<R> {

    // literals
    R visitFunctionLiteral(FunctionLiteral functionLiteral);
    R visitLiteralExpr(LiteralExpr literalExpr);
    R visitRecordLiteral(RecordLiteral recordLiteral);

    // composed expressions
    R visitBinaryExpr(BinaryExpr binaryExpr);
    R visitFunctionCall(FunctionCall functionCall);
    R visitIdentifierExpr(IdentifierExpr identifierExpr);
    R visitIfExpr(IfExpr ifExpr);
    R visitUnaryExpr(UnaryExpr unaryExpr);

}
