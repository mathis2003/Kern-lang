package com.example.kernlang.interpreter.frontend;

import com.example.kernlang.interpreter.frontend.parser.ASTNode;
import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;
import com.example.kernlang.interpreter.frontend.parser.statements.Stmt;

public class PrettyPrinter {
    public static String prettyPrint(ASTNode astNode) {
        if (astNode == null) {
            return "No AST, possibly because of either syntax error or since this node isn't compiled yet.";
        } else {
            if (astNode instanceof Expr) {
                return ((Expr)astNode).toString(1);
            } else if (astNode instanceof Stmt) {

            }
            return "pretty printing";
        }
    }
}
