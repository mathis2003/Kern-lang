package com.example.kernlang.interpreter.frontend.parser.expressions.literals;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.parser.expressions.Expr;
import com.example.kernlang.interpreter.frontend.parser.expressions.Literal;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordLiteral extends Expr implements Literal {

    private final ArrayList<RecordField> recordFields = new ArrayList<>();

    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);

        String fieldStrings = "";
        for (RecordField recordField : recordFields) {
            fieldStrings += "\n" + recordField.toString(indent + 1);
        }

        return tabs + "record:" + fieldStrings + tabs + "\n";
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
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

        public Literal getL() {
            return l;
        }

        public void setLiteral(Literal l) {
            this.l = l;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public String toString(int indent) {
            String tabs = Expr.getTabs(indent);

            return tabs + identifier + ":\n" + tabs + "\tvalue:\n" + l.toString(indent + 2);
        }
    }
}
