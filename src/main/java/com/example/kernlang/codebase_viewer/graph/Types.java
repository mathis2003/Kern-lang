package com.example.kernlang.codebase_viewer.graph;

public enum Types {
    UNIT("Unit"), BOOL("Bool"), CHAR("Char"), INT("Int"),
    FUNCTION("Function"), RECORD("Record"), VARIANT("Variant"),
    REFERENCE("Reference");

    private final String typeName;

    Types(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
