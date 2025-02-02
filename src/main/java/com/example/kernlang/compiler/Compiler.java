package com.example.kernlang.compiler;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Compiler {
    public static void compile(ArrayList<GraphNode> nodes) {

        //CompileProgressWindow compileProgressWindow = new CompileProgressWindow();

        List<String> errors = Collections.synchronizedList(new ArrayList<String>());
        ArrayList<CompileThread> compileThreads = new ArrayList<>();
        for (GraphNode node : nodes) {
            CompileThread t = new CompileThread(errors, node);
            compileThreads.add(t);
            t.start();
        }

        for (int i = 0; i < compileThreads.size(); i++) {
            Thread t = compileThreads.get(i);
            GraphNode node = nodes.get(i);
            try {
                t.join();
            } catch (InterruptedException e) {
                errors.add("node: " + node.getName() + " failed to compile");
            }
        }

        if (!errors.isEmpty()) {
            new CompileErrorPopup(errors);
        }
    }


    public static ParseError error(GraphNode node, int line, String message) {
        return new ParseError("node: " + node.getName() + " | " +
                "[line " + line + "] Error" + ": " + message);
    }

    public static class ParseError extends RuntimeException {
        public ParseError(String message) {
            super(message);
        }
    }
}
