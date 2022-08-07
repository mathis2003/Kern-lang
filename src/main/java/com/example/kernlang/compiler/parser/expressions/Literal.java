package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.compiler.parser.ASTNode;

// just a placeholder interface to tie together LiteralExpr and FunctionLiteral by supertype
public interface Literal extends ASTNode {
    public String toString(int indent);
}
