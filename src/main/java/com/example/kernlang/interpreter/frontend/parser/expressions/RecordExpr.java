package com.example.kernlang.interpreter.frontend.parser.expressions;

import java.util.ArrayList;

public class RecordExpr implements Expr {

    private final ArrayList<RecordField> recordFields = new ArrayList<>();

    @Override
    public String toString(int indent) {
        String tabs = "";
        for (int i = 0; i < indent; i++) tabs += "\t";

        String fieldStrings = "";
        for (RecordField recordField : recordFields) {
            fieldStrings += "\n" + recordField.toString(indent + 1);
        }

        return tabs + "record:" + fieldStrings + tabs + "\n";
    }

    public void addRecordField(String identifier, Expr expr) {
        this.recordFields.add(new RecordField(identifier, expr));
    }

    private static record RecordField (String identifier, Expr expr) {
        public String toString(int indent) {
            String tabs = "";
            for (int i = 0; i < indent; i++) tabs += "\t";

            return tabs + identifier + ":\n" + tabs + "\tvalue:\n" + expr.toString(indent + 2);
        }
    }
}
