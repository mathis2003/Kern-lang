package com.example.kernlang.compiler.parser.expressions;

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


}
