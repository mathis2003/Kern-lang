package com.example.kernlang.interpreter.frontend.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.interpreter.frontend.ast_visitors.ExprVisitor;
import com.example.kernlang.interpreter.frontend.parser.expressions.literals.LiteralExpr;

import java.util.HashMap;

public class IfExpr extends Expr{
    public final Expr condition;
    public final Expr trueCaseExpr;
    public final Expr falseCaseExpr;

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
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitIfExpr(this);
    }

    @Override
    public Literal interpret(GraphNode context, HashMap<String, Literal> additionalContext) {
        if ((Boolean)((LiteralExpr)condition.interpret(context, additionalContext)).getTok().literal()) {
            return trueCaseExpr.interpret(context, additionalContext);
        } else {
            return falseCaseExpr.interpret(context, additionalContext);
        }
    }
}
