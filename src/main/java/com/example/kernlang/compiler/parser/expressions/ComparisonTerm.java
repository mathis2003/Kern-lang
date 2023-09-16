package com.example.kernlang.compiler.parser.expressions;

import java.util.ArrayList;
import java.util.Arrays;

public class ComparisonTerm extends ExtendibleASTNode {

    public ComparisonTerm() {
        super(
                new ArrayList<>(Arrays.asList(
                        AddSub::new,
                        Term::new
                )),
                "ComparisonTerm failed to parse"
        );
    }


}
