package com.example.kernlang.operating_system;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.RecordLiteral;

import java.util.ArrayList;
import java.util.HashMap;

public class OS {
    ArrayList<KernProcess> processes = new ArrayList<>();

    public OS() {

    }

    public void addNewProcess(GraphNode g) {
        RecordLiteral appRecord = (RecordLiteral) (g.getAST().interpret(g, new HashMap<>()));
        ASTNode startData = appRecord.getField("start_data");
        FunctionLiteral update = (FunctionLiteral) appRecord.getField("update");
        FunctionLiteral render = (FunctionLiteral) appRecord.getField("render");
        FunctionLiteral terminal = (FunctionLiteral) appRecord.getField("terminal");
        //processes.add(new KernProcess(startData, update, render, terminal, g));
        processes.add(new KernProcess(startData, update, null, terminal, g));
    }
}
