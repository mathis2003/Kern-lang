package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.GraphWindowState;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Function;

public class DeletePopup extends Stage {
    public DeletePopup(GraphWindowState gws, GraphNode node) {
        super();

        Label label = new Label("Are you sure you want to delete node: " + node.getName() + "?");

        Button yesButton = new Button("yes");
        yesButton.setOnAction(e -> {
            gws.deleteNode(node);
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
