package com.example.kernlang;

import javafx.scene.control.SplitPane;

public class IDE extends SplitPane {
    public IDE() {
        this.getItems().addAll(new CodebaseViewer(), new TextEditor());
    }
}
