package com.example.kernlang.compiler;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class CompileProgressWindow extends Stage {
    private final Label progressField;
    private final Label currentCompilingTaskName;
    AtomicInteger progressInt = new AtomicInteger(0);
    public CompileProgressWindow() {
        progressField = new Label("");
        currentCompilingTaskName = new Label("");

        final VBox layout = new VBox(progressField, currentCompilingTaskName);
        final Scene scene = new Scene(layout, 500, 300);
        this.setScene(scene);
        this.show();
    }

    public void closeWindow() {
        this.close();
    }

    public void updateProgress() {
        int progress = progressInt.addAndGet(1);
        Float progressPercentage = progress / 10f;
        progressField.setText(progressPercentage.toString());
    }

    public void setCompileTaskName(String name) {
        currentCompilingTaskName.setText(name);
    }
}
