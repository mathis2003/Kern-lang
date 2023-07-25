package com.example.kernlang.compiler.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.ParseResult;
import com.example.kernlang.compiler.parser.expressions.Literal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class RecordLiteral implements ASTNode {

    private final ArrayList<RecordField> recordFields = new ArrayList<>();

    @Override
    public String toString(String indent) {
        return "\n" + indent + "record:";
    }

    @Override
    public ParseResult parse(String input) {
        return new ParseResult(Optional.empty(), input, "");
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
        private Literal l;

        public RecordField(String identifier, Literal l) {
            this.identifier = identifier;
            this.l = l;
        }

        public ASTNode getL() {
            return l;
        }

        public void setLiteral(Literal l) {
            this.l = l;
        }

        public String getIdentifier() {
            return this.identifier;
        }

    }
}
