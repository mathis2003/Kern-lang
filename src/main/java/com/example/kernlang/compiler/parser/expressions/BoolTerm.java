package com.example.kernlang.compiler.parser.expressions;

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


}
