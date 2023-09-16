package com.example.kernlang.compiler.parser.language_extensions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.literals.NumberLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.UnitLiteral;
import com.example.kernlang.compiler.parser.statements.Statement;

import java.util.HashMap;
import java.util.Optional;

/**
 * needs to be added as a clause to Statement
 */

public class ForLoop implements ASTNode {
    int start, end;
    Statement stmt;
    @Override
    public String toString(String indent) {
        return "\n\t" + indent + "for loop:\n" + stmt.toString(indent + "\t");
    }

    @Override
    public ParseResult parse(String input) {
        String input2 = input.stripLeading();
        if (input2.startsWith("for")) {
            input2 = input2.substring(3).stripLeading();
            ParseResult startRes = new NumberLiteral().parse(input2);
            if (startRes.syntaxNode().isPresent()) {
                start = ((NumberLiteral)(startRes.syntaxNode().get())).getNumber().intValue();
                input2 = startRes.leftOverString().stripLeading();
                if (input2.startsWith("to")) {
                    input2 = input2.substring(2).stripLeading();
                    ParseResult endRes = new NumberLiteral().parse(input2);
                    if (endRes.syntaxNode().isPresent()) {
                        end = ((NumberLiteral)(endRes.syntaxNode().get())).getNumber().intValue();
                        input2 = endRes.leftOverString().stripLeading();
                        ParseResult stmtResult = new Statement().parse(input2);
                        if (stmtResult.syntaxNode().isPresent()) {
                            stmt = (Statement) stmtResult.syntaxNode().get();
                            return new ParseResult(
                                    Optional.of(this),
                                    stmtResult.leftOverString(),
                                    ""
                            );
                        }
                    }
                }
            }
        }

        return new ParseResult(Optional.empty(), input, "failed to parse for loop");
    }

    @Override
    public ASTNode interpret(GraphNode contextNode, HashMap<String, ASTNode> additionalContext) {
        for (int i = start; i <= end; i++) {
            stmt.interpret(contextNode, additionalContext);
        }
        return new UnitLiteral();
    }
}
