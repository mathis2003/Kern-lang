package com.example.kernlang.interpreter.frontend;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.lexer.Lexer;
import com.example.kernlang.interpreter.frontend.lexer.Token;
import com.example.kernlang.interpreter.frontend.parser.ASTNode;
import com.example.kernlang.interpreter.frontend.parser.Parser;
import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;

import java.util.ArrayList;

public class Compiler {
    public static Expr compile(GraphNode node) {

        ArrayList<Token> tokens = new Lexer(node.getCodeString(), node).lexCode();

        Expr astExpr = new Parser(tokens).parseExpression();


        /*switch (node.getNodeType()) {
            case UNIT, BOOL, CHAR, INT -> {
                astNode = new Parser(tokens).parsePrimitiveNode();
            }
            default -> {
                astNode = null;
            }
        }*/

        return astExpr;
    }
}
