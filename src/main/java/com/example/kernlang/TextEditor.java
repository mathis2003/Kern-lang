package com.example.kernlang;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TextEditor extends Pane {
    private final VBox layout;
    private final TextArea textArea;

    public TextEditor() {
        textArea = new TextArea("-- write code here");
        textArea.setPrefSize(460, 550);

        layout = new VBox(textArea);
        this.getChildren().add(layout);
    }

    public String getCodeText() {
        return textArea.getText();
    }
}
