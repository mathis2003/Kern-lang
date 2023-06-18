package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.GraphWindowState;
import com.example.kernlang.codebase_viewer.graph.Types;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NodeCreationPopup extends Stage {
    public NodeCreationPopup(GraphWindowState graphWindowState) {
        super();


        final TextField nameField = new TextField();
        nameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                graphWindowState.addNode(nameField.getText(), Types.UNIT);
                this.close();
            }
        });


        Button createButton = new Button("create node");
        createButton.setOnAction(e -> {
            graphWindowState.addNode(nameField.getText(), Types.UNIT);
            this.close();
        });

        VBox layout = new VBox(nameField, createButton);

        Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();
    }
}
