package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.ast_visitors.ExprVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class Factor extends ExtendibleASTNode {

    public Factor() {
        super(
                new ArrayList<>(Arrays.asList(
                        UnaryExpr::new,
                        RecordAccess::new,
                        IfExpr::new,
                        FunctionCall::new,
                        ParenExpr::new,
                        IdentifierExpr::new,
                        Literal::new
                )),
                "failed to parse Factor"
        );
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitFactor(this);
    }

}
