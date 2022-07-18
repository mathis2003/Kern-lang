package com.example.kernlang;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TextEditor extends Pane {
    private final TextArea textArea;
    private GraphNode currentNode;

    public TextEditor() {
        final VBox layout;

        textArea = new TextArea("-- write code here");
        textArea.setPrefSize(460, 550);

        final Button saveButton = new Button("SAVE");
        saveButton.setOnAction(e -> {
            if (currentNode != null) {
                currentNode.setCodeString(textArea.getText());
            }
        });

        layout = new VBox(saveButton, textArea);
        this.getChildren().add(layout);
    }

    public void setCurrentNode(GraphNode node) {
        textArea.setText(node.getCodeString());
        this.currentNode = node;
    }

    public String getCodeText() {
        return textArea.getText();
    }
}
