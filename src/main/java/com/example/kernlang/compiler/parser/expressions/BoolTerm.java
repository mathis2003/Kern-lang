package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.ast_visitors.ExprVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class BoolTerm extends ExtendibleASTNode {

    public BoolTerm() {
        super(
                new ArrayList<>(Arrays.asList(
                        Comparison::new,
                        ComparisonTerm::new
                )),
                "Boolterm failed to parse"
        );
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitBoolTerm(this);
    }

}
