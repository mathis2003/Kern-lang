package com.example.kernlang.interpreter.frontend;

import com.example.kernlang.interpreter.frontend.parser.ASTNode;
import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;
import com.example.kernlang.interpreter.frontend.parser.expressions.LiteralExpr;
import com.example.kernlang.interpreter.frontend.parser.statements.Stmt;

public class PrettyPrinter {
    public static String prettyPrint(ASTNode astNode) {
        if (astNode == null) {
            return "no AST, possibly because of either syntax error or since this node isn't compiled yet";
        } else {
            if (astNode instanceof Expr) {
                String result = "expression:\n\t";
                if (astNode instanceof LiteralExpr) {
                    result += "type: " + ((LiteralExpr) astNode).getTok().tokenType() + "\n\t";
                    result += "value: " + ((LiteralExpr) astNode).getTok().literal();
                }

                return result;
            } else if (astNode instanceof Stmt) {

            }
            return "pretty printing";
        }
    }
}
