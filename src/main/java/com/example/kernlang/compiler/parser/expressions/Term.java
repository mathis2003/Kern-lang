package com.example.kernlang.compiler.parser.expressions;

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


}
