package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.interpreter.frontend.parser.ASTNode;

public interface Expr extends ASTNode {
    public String toString(int indent);
}
