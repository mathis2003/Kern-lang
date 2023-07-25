package com.example.kernlang.compiler.parser.expressions;

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


}
