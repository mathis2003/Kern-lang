package com.example.kernlang.interpreter;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.lexer.Lexer;
import com.example.kernlang.interpreter.lexer.Token;

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
        report(line, "", message);
    }

    static private void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
