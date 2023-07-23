package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;

import java.util.*;
import java.util.function.Supplier;

public class Expr extends ExtendibleASTNode {
    public Expr() {
        super(
                new ArrayList<>(Arrays.asList(
                        BoolExpr::new
                )),
                "expression failed to parse"
        );
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitExpr(this);
    }

}
