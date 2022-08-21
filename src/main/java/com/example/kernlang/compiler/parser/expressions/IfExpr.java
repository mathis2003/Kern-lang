package com.example.kernlang.compiler.parser.expressions;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.ast_visitors.ExprVisitor;
import com.example.kernlang.compiler.parser.expressions.literals.LiteralExpr;

import java.util.HashMap;

public class IfExpr implements Expr{
    public final Expr condition;
    public final Expr trueCaseExpr;
    public final Expr falseCaseExpr;

    public IfExpr(Expr condition, Expr trueCaseExpr, Expr falseCaseExpr) {
        this.condition = condition;
        this.trueCaseExpr = trueCaseExpr;
        this.falseCaseExpr = falseCaseExpr;
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
