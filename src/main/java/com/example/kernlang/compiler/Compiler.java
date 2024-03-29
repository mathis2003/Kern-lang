package com.example.kernlang.compiler;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;

import java.util.ArrayList;

public class Compiler {
    public static void compile(ArrayList<GraphNode> nodes) {
        ArrayList<String> errors = new ArrayList<>();

        for (GraphNode node : nodes) {
            try {
                //ArrayList<Token> tokens = new Lexer(node.getCodeString(), node).lexCode(errors);

                if (node == null || node.isCompiled() || node.getCodeString().strip().equals("")) continue;

                ParseResult res = new Expr().parse(node.getCodeString());// = //new Parser(tokens, node).parseLiteral();
                if (res.syntaxNode().isPresent()) {
                    ASTNode astExpr = res.syntaxNode().get();
                    node.setAstExpr(astExpr);
                    node.setCompiled();
                } else {
                    errors.add("node " + node.getName() + " : " + res.optionalErrMsg());
                }

            } catch (ParseError parseError) {
                errors.add("node " + node.getName() + " : " + parseError.toString());
            }
        }

        if (errors.size() == 0) {
            // show popup saying the compilation was successful
        } else {
            // show popup with errors
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
