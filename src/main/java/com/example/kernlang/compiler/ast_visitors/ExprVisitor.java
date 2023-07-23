package com.example.kernlang.compiler.ast_visitors;

import com.example.kernlang.compiler.parser.expressions.*;
import com.example.kernlang.compiler.parser.expressions.literals.*;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.parser.statements.Statement;


public interface ExprVisitor<R> {

    // literals
    R visitFunctionLiteral(FunctionLiteral functionLiteral);

    R visitRecordLiteral(RecordLiteral recordLiteral);

    R visitBoolLiteral(BoolLiteral boolLiteral);

    R visitCharLiteral(CharLiteral charLiteral);

    R visitNumberLiteral(NumberLiteral numberLiteral);

    R visitUnitLiteral(UnitLiteral unitLiteral);


    // composed expressions
    R visitAddSub(AddSub addSub);

    R visitBoolExpr(BoolExpr boolExpr);

    R visitBoolTerm(BoolTerm boolTerm);

    R visitComparison(Comparison comparison);

    R visitComparisonTerm(ComparisonTerm comparisonTerm);

    R visitEqualityExpr(EqualityExpr equalityExpr);

    R visitExpr(Expr expr);

    R visitFactor(Factor factor);

    R visitFunctionCall(FunctionCall functionCall);

    R visitIdentifierExpr(IdentifierExpr identifierExpr);

    R visitIfExpr(IfExpr ifExpr);

    R visitLiteral(Literal literal);

    R visitMulDiv(MulDiv mulDiv);

    R visitParenExpr(ParenExpr parenExpr);

    R visitRecordAccess(RecordAccess recordAccess);

    R visitTerm(Term term);

    R visitUnaryExpr(UnaryExpr unaryExpr);

    R visitVariable(Variable variable);

    R visitAssignment(Assignment assignment);

    R visitReturnStmt(ReturnStmt returnStmt);

    R visitStatement(Statement statement);

}
