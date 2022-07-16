package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.CursorState;
import com.example.kernlang.codebase_viewer.graph.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NodeCreationPopup extends Stage {
    public NodeCreationPopup(CursorState cursorState) {
        super();

        ObservableList<Types> options =
                FXCollections.observableArrayList(
                        Types.UNIT,
                        Types.BOOL,
                        Types.CHAR,
                        Types.INT,
                        Types.FUNCTION,
                        Types.RECORD,
                        Types.VARIANT,
                        Types.REFERENCE
                );
        final ComboBox<Types> comboBox = new ComboBox<>(options);

        final TextField nameField = new TextField();


        Button createButton = new Button("create node");
        createButton.setOnAction(e -> {
            cursorState.drawCircle(nameField.getText(), comboBox.getSelectionModel().getSelectedItem());
            this.close();
        });

        VBox layout = new VBox(comboBox, nameField, createButton);

        Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();
    }
}
