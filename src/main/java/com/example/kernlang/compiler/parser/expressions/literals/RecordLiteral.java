package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Expr;
import com.example.kernlang.compiler.parser.expressions.Literal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class RecordLiteral implements ASTNode {

    private final ArrayList<RecordField> recordFields = new ArrayList<>();

    @Override
    public String toString(String indent) {
        String result = "\n" + indent + "record:" + "\n";
        for (RecordField rf : recordFields) {
            result += rf.toString(indent + "\t") + "\n";
        }
        return result;
    }

    @Override
    public ParseResult parse(String input) {
        String originalInput = input;
        input = input.stripLeading();

        ParseResult result;

        if (input.startsWith("{")) {
            input = input.substring(1).stripLeading();
            while (!input.startsWith("}")) {
                String fieldName = "";
                if (isAlpha(input.charAt(0))) fieldName += input.charAt(0);
                else return new ParseResult(Optional.empty(), originalInput, "failed to parse record literal");

                int i = 1;
                while (isAlpha(input.charAt(i)) || isDigit(input.charAt(i))) {
                    fieldName += input.charAt(i);
                    i++;
                }
                input = input.substring(i).stripLeading();

                if (!input.startsWith("=")) return new ParseResult(Optional.empty(), originalInput, "failed to parse record literal");
                input = input.substring(1).stripLeading();

                ParseResult exprResult = new Expr().parse(input);
                if (exprResult.syntaxNode().isPresent()) {
                    input = exprResult.leftOverString().stripLeading();
                    recordFields.add(new RecordField(fieldName, exprResult.syntaxNode().get()));
                } else return new ParseResult(Optional.empty(), originalInput, "failed to parse record literal");

            }
            input = input.substring(1).stripLeading();
        }
        return new ParseResult(Optional.of(this), input, "");
    }

    @Override
    public ASTNode interpret(GraphNode context, HashMap<String, ASTNode> additionalContext) {
        return this;
    }

    public void addRecordField(String identifier, Literal expr) {
        this.recordFields.add(new RecordField(identifier, expr));
    }

    public ArrayList<RecordField> getRecordFields() {
        return recordFields;
    }

    public static class RecordField {
        private final String identifier;
        private ASTNode expr;

        public RecordField(String identifier, ASTNode expr) {
            this.identifier = identifier;
            this.expr = expr;
        }

        public String toString(String indent) {
            return indent + "recordfield:\n" + indent + "\t" + "identifier: " + identifier + "\n" +
                    indent + "\t" + "expression:" + expr.toString(indent + "\t\t");
        }

        public ASTNode getL() {
            return expr;
        }

        public String getIdentifier() {
            return this.identifier;
        }

    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}
