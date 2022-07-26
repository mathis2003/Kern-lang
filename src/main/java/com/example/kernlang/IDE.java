package com.example.kernlang;

import com.example.kernlang.codebase_viewer.CodebaseViewer;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class IDE extends BorderPane {
    public IDE() {
        TextEditor textEditor = new TextEditor();
        CodebaseViewer codebaseViewer = new CodebaseViewer(textEditor);

        // Menu stuff
        {
            // Project menu
            Menu projectMenu = new Menu("Project");

            MenuItem compileItem = new MenuItem("Compile");
            compileItem.setOnAction(e -> codebaseViewer.compileNodes());

            projectMenu.getItems().add(compileItem);

            // Menu bar
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(projectMenu);
            this.setTop(menuBar);
        }


        SplitPane splitPane = new SplitPane();
        this.setCenter(splitPane);

        splitPane.getItems().addAll(codebaseViewer, textEditor);
    }
}
