package com.example.kernlang;

import java.util.ArrayList;

public class Component {
    private final int cluster;
    private int parentCluster;
    private final int codeFileKey;
    private double x, y;

    private final ArrayList<String> imports;

    public Component(int cluster, int parentCluster, int codeFileKey) {
        this.cluster = cluster;
        this.parentCluster = parentCluster;
        this.codeFileKey = codeFileKey;
        this.imports = new ArrayList<>();
    }

    public int getCodeFileKey() {
        return this.codeFileKey;
    }

    public ArrayList<String> getImports() {
        return this.imports;
    }
}
