package com.example.kernlang.interpreter.frontend;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.lexer.Lexer;
import com.example.kernlang.interpreter.frontend.lexer.Token;
import com.example.kernlang.interpreter.frontend.parser.ASTNode;
import com.example.kernlang.interpreter.frontend.parser.Parser;

import java.util.ArrayList;
// random comment
public class Compiler {
    public static ASTNode compile(GraphNode node) {
        ASTNode astNode = null;

        ArrayList<Token> tokens = new Lexer(node.getCodeString(), node).lexCode();


        switch (node.getNodeType()) {
            case UNIT, BOOL, CHAR, INT -> {
                astNode = new Parser(tokens).parsePrimitiveNode();
            }
            default -> {
                astNode = null;
            }
        }

        return astNode;
    }
}
