package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.UnitLiteral;
import com.example.kernlang.compiler.parser.statements.ReturnStmt;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.statements.Assignment;
import com.example.kernlang.compiler.parser.statements.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class FunctionCall implements ASTNode {
    private ASTNode functionExpr;
    private final ArrayList<ASTNode> args = new ArrayList<>();

    public void addArgument(ASTNode expr) {
        args.add(expr);
    }

    public ArrayList<ASTNode> getArgs() {
        return args;
    }

    public ASTNode getFunctionExpr() {
        return functionExpr;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitFunctionCall(this);
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("%")) {
            input2 = input2.substring(1).stripLeading();
            ParseResult res = new Factor().parse(input2);
            if (res.syntaxNode().isPresent()) {
                functionExpr = res.syntaxNode().get();
                input2 = res.leftOverString().stripLeading();
                if (input2.startsWith("(")) {
                    // parse arguments
                    input2 = input2.substring(1).stripLeading();
                    if (input2.startsWith(")")) {
                        return new ParseResult(Optional.of(this), input2.substring(1).stripLeading(), "");
                    }
                    do {
                        ParseResult parseArgResult = new Expr().parse(input2);
                        if (parseArgResult.syntaxNode().isPresent()) {
                            addArgument(parseArgResult.syntaxNode().get());
                            input2 = parseArgResult.leftOverString().stripLeading();
                        } else {
                            return new ParseResult(Optional.empty(), input, "failed to parse function call");
                        }

                    } while(input2.startsWith(","));
                    if (input2.startsWith(")")) {
                        return new ParseResult(Optional.of(this), input2.substring(1).stripLeading(), "");
                    }
                }

            }

        }

        return new ParseResult(Optional.empty(), input, "failed to parse function call");
    }

    /**
     *
     * @param contextNode: the function literal as a node
     * @param additionalContext: the arguments passed for the function call
     * @return
     */
    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        // getting the actual function literal
        FunctionLiteral fLit = null;
        if (functionExpr instanceof FunctionLiteral) {
            fLit = (FunctionLiteral) functionExpr;
        } else {
            // for instance, the functionExpr is actually a composition of functions, or an identifier of a function
            fLit = (FunctionLiteral) functionExpr.interpret(contextNode, additionalContext);
        }

        HashMap<String, ASTNode> argumentsHm = new HashMap<>();
        // evaluating the args given with the function call
        for (int i = 0; i < args.size(); i++) {
            String argName = fLit.getParamIdentifiers().get(i);
            // note: the arguments given with the function call, are to be evaluated in the caller's context
            // no clue what additionalContext is doing here, might have to replace that with null
            argumentsHm.put(argName, (Literal) args.get(i).interpret(contextNode, additionalContext));
        }

        // statements
        for (Statement stmt : fLit.getStatements()) {
            if (stmt.getStatement() instanceof ReturnStmt) {
                // note: the expressions in the function literal, are to be evaluated in that function's context
                return stmt.interpret(fLit.getFunctionContext(), argumentsHm);
            } else {
                stmt.interpret(fLit.getFunctionContext(), argumentsHm);
            }
        }
        return new UnitLiteral();
    }
}
