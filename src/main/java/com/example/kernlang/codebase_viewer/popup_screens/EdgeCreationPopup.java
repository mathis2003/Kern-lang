package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.CursorState;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EdgeCreationPopup extends Stage {
    public EdgeCreationPopup(CursorState cursorState, GraphNode node) {
        super();

        TextField nameField = new TextField();
        CheckBox isBoundCheckBox = new CheckBox("is bound");
        Button createButton = new Button("create edge");
        createButton.setOnAction(e -> {
            cursorState.setStateDraggingEdge(node, nameField.getText(), isBoundCheckBox.isSelected());
            this.close();
        });


        VBox layout = new VBox(nameField, isBoundCheckBox, createButton);
        layout.setSpacing(0.5);


        Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();
    }
}
