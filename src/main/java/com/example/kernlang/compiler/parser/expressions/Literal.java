package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.literals.CharLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.NumberLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;

import java.util.ArrayList;
import java.util.Arrays;

public class Literal extends ExtendibleASTNode {

    public Literal (ASTNode literal) {
        this();
        super.expr = literal;
    }

    public Literal() {
        super(
                new ArrayList<>(Arrays.asList(
                        CharLiteral::new,
                        FunctionLiteral::new,
                        NumberLiteral::new,
                        RecordLiteral::new
                )),
                "failed to parse Factor"
        );
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}
