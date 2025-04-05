package com.example.kernlang;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new IDE(), 1000, 600);
        //scene.getRoot().setStyle("-fx-base:#000000");
        stage.setScene(scene);
        stage.show();
    }
}
