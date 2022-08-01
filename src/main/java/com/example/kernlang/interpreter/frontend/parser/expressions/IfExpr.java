package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;

public class IfExpr extends Expr{
    private final Expr condition;
    private final Expr trueCaseExpr;
    private final Expr falseCaseExpr;

    public IfExpr(Expr condition, Expr trueCaseExpr, Expr falseCaseExpr) {
        this.condition = condition;
        this.trueCaseExpr = trueCaseExpr;
        this.falseCaseExpr = falseCaseExpr;
    }


    @Override
    public String toString(int indent) {
        String tabs = getTabs(indent);

        return tabs + "if expression:\n" +
                tabs + "\tcondition:\n" + condition.toString(indent + 2) +
                tabs + "\ttrue case expression:\n" + trueCaseExpr.toString(indent + 2) +
                tabs + "\tfalse case expression:\n" + falseCaseExpr.toString(indent + 2);
    }

    @Override
    public Literal interpret(GraphNode context) {
        if ((Boolean)((LiteralExpr)condition.interpret(context)).getTok().literal()) {
            return trueCaseExpr.interpret(context);
        } else {
            return falseCaseExpr.interpret(context);
        }
    }
}
