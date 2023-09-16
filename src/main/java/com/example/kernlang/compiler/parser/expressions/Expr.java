package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.parser.language_extensions.Quote;

import java.util.*;
import java.util.function.Supplier;

public class Expr extends ExtendibleASTNode {
    public Expr() {
        super(
                new ArrayList<>(Arrays.asList(
                        BoolExpr::new,
                        Quote::new
                )),
                "expression failed to parse"
        );
    }


}
