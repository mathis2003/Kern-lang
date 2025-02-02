package com.example.kernlang.compiler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class CompileErrorPopup extends Stage {
    public CompileErrorPopup(List<String> errors) {
        final Button closeButton = new Button("close");
        closeButton.setOnAction(e -> this.close());

        final TextArea textArea = new TextArea(String.join("\n\n", errors));

        final VBox layout = new VBox(textArea, closeButton);
        final Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();
    }
}
