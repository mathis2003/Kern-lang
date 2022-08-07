package com.example.kernlang.interpreter.frontend.ast_visitors;

import com.example.kernlang.interpreter.frontend.parser.expressions.*;
import com.example.kernlang.interpreter.frontend.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.interpreter.frontend.parser.expressions.literals.LiteralExpr;
import com.example.kernlang.interpreter.frontend.parser.expressions.literals.RecordLiteral;


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
