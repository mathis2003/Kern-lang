package com.example.kernlang.operating_system;

import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KernProcess extends Stage {
    ASTNode startData;
    FunctionLiteral update;
    FunctionLiteral render;

    public KernProcess(ASTNode startData, FunctionLiteral update, FunctionLiteral render, String appTitle) {
        this.startData = startData;
        this.update = update;
        this.render = render;

        this.setTitle(appTitle);

        Label label = new Label("this is to be replaced with the apps data");
        VBox container = new VBox(label);

        this.setScene(new Scene(container));
        this.show();
    }


}
