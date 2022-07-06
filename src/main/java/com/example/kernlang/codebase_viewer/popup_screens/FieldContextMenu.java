package com.example.kernlang.codebase_viewer.popup_screens;

import com.example.kernlang.codebase_viewer.CursorState;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FieldContextMenu extends ContextMenu {

    public FieldContextMenu(CursorState cs) {
        MenuItem newNode = new MenuItem("New Node");
        newNode.setOnAction(e -> new NodeCreationPopup(cs));
        getItems().addAll(newNode);
    }
}
