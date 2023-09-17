package com.example.kernlang.compiler.parser.expressions;


import java.util.ArrayList;
import java.util.Arrays;

public class Factor extends ExtendibleASTNode {

    public Factor() {
        super(
                new ArrayList<>(Arrays.asList(
                        UnaryExpr::new,
                        RecordAccess::new,
                        ArrayAccess::new,
                        IfExpr::new,
                        FunctionCall::new,
                        ParenExpr::new,
                        IdentifierExpr::new,
                        Literal::new
                )),
                "failed to parse Factor"
        );
    }


}
