package com.example.kernlang.compiler;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;
import javafx.application.Platform;

import java.util.List;

public class CompileThread extends Thread {
    GraphNode node;
    List<String> errors;
    public CompileThread(List<String> errors, GraphNode node) {
        this.node = node;
        this.errors = errors;
    }
    @Override
    public void run() {
        try {
            //ArrayList<Token> tokens = new Lexer(node.getCodeString(), node).lexCode(errors);

            if (node == null || node.isCompiled() || node.getCodeString().isBlank()) return;

            ParseResult res = new Expr().parse(node.getCodeString());// = //new Parser(tokens, node).parseLiteral();
            if (res.syntaxNode().isPresent()) {
                ASTNode astExpr = res.syntaxNode().get();
                node.setAstExpr(astExpr);
                node.setCompiled();
                //System.out.println("compiled node: " + node.getName());
                /*Platform.runLater(() -> {
                    compileProgressWindow.updateProgress();
                });*/
            } else {
                errors.add("node " + node.getName() + " : " + res.optionalErrMsg());
            }

        } catch (Compiler.ParseError parseError) {
            errors.add("node " + node.getName() + " : " + parseError.toString());
        }
    }
}
