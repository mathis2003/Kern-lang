package com.example.kernlang;

import com.example.kernlang.codebase_viewer.CodebaseViewer;
import com.example.kernlang.codebase_viewer.DBManager;
import com.example.kernlang.db.DataAccessException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class IDE extends BorderPane {
    public IDE() {
        TextEditor textEditor = new TextEditor();
        CodebaseViewer codebaseViewer = new CodebaseViewer(textEditor);
        DBManager dbManager = new DBManager(codebaseViewer.getGraphWindowState());

        // Menu stuff
        {
            // Project menu
            Menu projectMenu = new Menu("Project");
            {

                MenuItem compileItem = new MenuItem("Compile");
                compileItem.setOnAction(e -> codebaseViewer.compileNodes());

                MenuItem openItem = new MenuItem("Open");
                openItem.setOnAction(e -> {
                    File file = new FileChooser().showOpenDialog(getScene().getWindow());
                    if (file != null) {
                        try {
                            dbManager.openDB(file);
                        } catch (DataAccessException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                MenuItem exportItem = new MenuItem("Export");
                exportItem.setOnAction(e -> {
                    File file = new FileChooser().showSaveDialog(getScene().getWindow());
                    //File file = new FileChooser().showOpenDialog(getScene().getWindow());
                    if (file != null) {
                        try {
                            dbManager.exportToDB(file);
                        } catch (DataAccessException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                MenuItem clearItem = new MenuItem("Clear");
                clearItem.setOnAction(e -> new ClearPopup(codebaseViewer));

                projectMenu.getItems().addAll(compileItem, openItem, exportItem, clearItem);
            }

            // statistics menu
            Menu statisticsMenu = new Menu("Statistics");
            {}

            // language extensions menu
            Menu langExtMenu = new Menu("Language Extensions");
            {}

            // Menu bar
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(projectMenu, statisticsMenu, langExtMenu);
            this.setTop(menuBar);
        }


        SplitPane splitPane = new SplitPane();
        this.setCenter(splitPane);

        splitPane.getItems().addAll(codebaseViewer, textEditor);
    }
}
