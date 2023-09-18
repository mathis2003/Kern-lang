package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.literals.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Literal extends ExtendibleASTNode {

    public Literal (ASTNode literal) {
        this();
        super.expr = literal;
    }

    public Literal() {
        super(
                new ArrayList<>(Arrays.asList(
                        CharLiteral::new,
                        FunctionLiteral::new,
                        NumberLiteral::new,
                        RecordLiteral::new,
                        ArrayLiteral::new
                )),
                "failed to parse Factor"
        );
    }

    @Override
    public ASTNode deepcopy() {
        // why on earth would anyone make copies of unevaluated expressions
        return this;
    }

}
