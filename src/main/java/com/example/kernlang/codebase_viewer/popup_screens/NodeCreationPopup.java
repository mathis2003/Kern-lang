package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.CursorState;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NodeCreationPopup extends Stage {
    public NodeCreationPopup(CursorState cursorState) {
        super();

        Pane layout = new Pane();

        TextField nameField = new TextField();
        nameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                cursorState.drawCircle(nameField.getText());
                this.close();
            }
        });

        layout.getChildren().add(nameField);


        Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();
    }
}
