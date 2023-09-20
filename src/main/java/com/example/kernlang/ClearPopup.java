package com.example.kernlang;

import com.example.kernlang.codebase_viewer.CodebaseViewer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClearPopup extends Stage {
    public ClearPopup(CodebaseViewer cbv) {
        super();

        Label label = new Label("Are you sure you want to remove all nodes?");

        Button yesButton = new Button("yes");
        yesButton.setOnAction(e -> {
            cbv.removeAllNodes();
            this.close();
        });

        Button noButton = new Button("no");
        noButton.setOnAction(e -> {
            this.close();
        });

        HBox buttons = new HBox(yesButton, noButton);

        VBox layout = new VBox(label, buttons);
        layout.setSpacing(0.5);


        Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();

    }
}
