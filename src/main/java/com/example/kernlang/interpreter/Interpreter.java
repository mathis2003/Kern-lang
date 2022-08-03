package com.example.kernlang.interpreter;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.lexer.Lexer;
import com.example.kernlang.interpreter.frontend.lexer.Token;

import java.util.ArrayList;

public class Interpreter {
    private GraphNode currentNode;
    static boolean hadError = false;


    public void runFunction(GraphNode currentNode) {
        this.currentNode = currentNode;
        Lexer lexer = new Lexer(currentNode.getCodeString(), currentNode);
        ArrayList<Token> tokens = lexer.lexCode();

        // for debugging purposes:
        for (Token tok : tokens) System.out.println(tok + "\n");
    }

    public static void error(GraphNode graphNode, int line, String message) {
        report(graphNode, line, "", message);
    }

    static private void report(GraphNode node, int line, String where, String message) {
        System.err.println(
                "node: " + node.getName() + " | " + "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
