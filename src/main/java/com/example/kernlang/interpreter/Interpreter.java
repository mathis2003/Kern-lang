package com.example.kernlang.interpreter;

import com.example.kernlang.codebase_viewer.graph.GraphNode;

public class Interpreter {
    private GraphNode currentNode;
    public void runFunction(GraphNode currentNode) {
        this.currentNode = currentNode;
    }
}
