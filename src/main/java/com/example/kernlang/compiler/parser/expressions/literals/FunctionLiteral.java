package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.expressions.IdentifierExpr;
import com.example.kernlang.compiler.parser.expressions.Literal;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.statements.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class FunctionLiteral implements ASTNode {
    private final ArrayList<Statement> statements = new ArrayList<>();
    private final ArrayList<String> paramIdentifiers = new ArrayList<>();
    private GraphNode functionContext = null;

    @Override
    public String toString(String indent) {
        String result = "\n" + indent + "function:\n" +
                indent + "\t" + "parameters:";

        for (String param : paramIdentifiers) result += indent + "\n\t" + param;

        result += indent + "\n\t" + "statements:";

        for (Statement statement : statements) result += statement.toString(indent + "\t");

        return result;

    }

    public FunctionLiteral() {}

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitFunctionLiteral(this);
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("\\")) {
            input2 = input2.substring(1).stripLeading();
            if (!input2.startsWith("->")) {
                ParseResult parseArgResult = new IdentifierExpr().parse(input2);
                if (parseArgResult.syntaxNode().isPresent()) {
                    paramIdentifiers.add(((IdentifierExpr)parseArgResult.syntaxNode().get()).getIdentifier());
                    input2 = parseArgResult.leftOverString().stripLeading();
                } else {
                    return new ParseResult(Optional.empty(), input, "failed to parse function literal");
                }
                while(input2.startsWith(",")) {
                    input2 = input2.substring(1).stripLeading();
                    parseArgResult = new IdentifierExpr().parse(input2);
                    if (parseArgResult.syntaxNode().isPresent()) {
                        paramIdentifiers.add(((IdentifierExpr)parseArgResult.syntaxNode().get()).getIdentifier());
                        input2 = parseArgResult.leftOverString().stripLeading();
                    } else {
                        return new ParseResult(Optional.empty(), input, "failed to parse function literal");
                    }
                }
            }
            if (input2.startsWith("->")) {
                input2 = input2.substring(2).stripLeading();
                if (input2.startsWith("{")) {
                    input2 = input2.substring(1);
                    while (!input2.startsWith("}")) {
                        ParseResult parseStmtRes = new Statement().parse(input2);
                        if (parseStmtRes.syntaxNode().isPresent()) {
                            statements.add((Statement)parseStmtRes.syntaxNode().get());
                            input2 = parseStmtRes.leftOverString().stripLeading();
                        } else return new ParseResult(Optional.empty(), input, "failed to parse function literal");
                    }
                    input2 = input2.substring(1).stripLeading();
                    return new ParseResult(Optional.of(this), input2, "");
                }
            }
        }
        return new ParseResult(Optional.empty(), input, "failed to parse function literal");
    }

    @Override
    public ASTNode interpret(GraphNode context, HashMap<String, ASTNode> additionalContext) {
        functionContext = context;
        return this;
    }

    public ArrayList<String> getParamIdentifiers() { return paramIdentifiers; }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public GraphNode getFunctionContext() {
        return functionContext;
    }
}
