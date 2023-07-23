package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.ast_visitors.ExprVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class BoolExpr extends ExtendibleASTNode {

    public BoolExpr() {
        super(
                new ArrayList<>(Arrays.asList(
                        EqualityExpr::new,
                        BoolTerm::new
                )),
                "Boolexpr failed to parse");
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitBoolExpr(this);
    }

}
