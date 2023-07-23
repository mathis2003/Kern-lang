package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.ast_visitors.ExprVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class Term extends ExtendibleASTNode {

    public Term() {
        super(
                new ArrayList<>(Arrays.asList(
                        MulDiv::new,
                        Factor::new
                )),
                "failed to parse Term"
        );
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitTerm(this);
    }

}
