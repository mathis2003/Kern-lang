package com.example.kernlang.compiler.parser.statements;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.ExtendibleASTNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class Statement implements ASTNode {

    protected ASTNode expr;

    private final ArrayList<Supplier<ASTNode>> clauses;

    public Statement() {
        clauses = new ArrayList<>(Arrays.asList(
                Assignment::new,
                ReturnStmt::new
        ));
    }



    public ASTNode getExpr() {
        return expr;
    }

    @Override
    public ParseResult parse(String input) {
        for (Supplier<ASTNode> clause : clauses) {
            ParseResult result = clause.get().parse(input);
            if (result.syntaxNode().isPresent()) {
                expr = result.syntaxNode().get();
                return new ParseResult(Optional.of(this), result.leftOverString(), "");
            }

        }
        return new ParseResult(Optional.empty(), input, "failed to parse statement");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        return expr.interpret(contextNode, additionalContext);
    }

    public ASTNode getStatement() {
        return expr;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitStatement(this);
    }
}
