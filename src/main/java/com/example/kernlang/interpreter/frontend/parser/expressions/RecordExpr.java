package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;

import java.util.ArrayList;

public class RecordExpr extends Expr {

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
    public Literal interpret(GraphNode context) {
        return null;
    }

    public void addRecordField(String identifier, Expr expr) {
        this.recordFields.add(new RecordField(identifier, expr));
    }

    private static record RecordField (String identifier, Expr expr) {
        public String toString(int indent) {
            String tabs = expr.getTabs(indent);

            return tabs + identifier + ":\n" + tabs + "\tvalue:\n" + expr.toString(indent + 2);
        }
    }
}
