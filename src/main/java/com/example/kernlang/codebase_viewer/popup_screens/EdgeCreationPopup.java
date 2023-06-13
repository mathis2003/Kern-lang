package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.GraphWindowState;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EdgeCreationPopup extends Stage {
    public EdgeCreationPopup(GraphWindowState graphWindowState, GraphNode node) {
        super();

        CheckBox isBoundCheckBox = new CheckBox("is bound");
        Button createButton = new Button("create edge");
        createButton.setOnAction(e -> {
            graphWindowState.setStateDraggingEdge(node, isBoundCheckBox.isSelected());
            this.close();
        });


        VBox layout = new VBox(isBoundCheckBox, createButton);
        layout.setSpacing(0.5);


        Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.showAndWait();
    }
}
