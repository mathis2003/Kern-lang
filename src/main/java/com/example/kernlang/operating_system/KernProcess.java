package com.example.kernlang.operating_system;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.compiler.parser.ASTNode;
import com.example.kernlang.compiler.parser.expressions.literals.ArrayLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.CharLiteral;
import com.example.kernlang.compiler.parser.expressions.literals.FunctionLiteral;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collector;

public class KernProcess extends Stage {
    ASTNode processData;

    GraphNode processNode;
    FunctionLiteral update;
    FunctionLiteral render;

    FunctionLiteral terminal;

    private Queue<Character> keyBuffer = new ConcurrentLinkedQueue<>();

    public KernProcess(ASTNode startData, FunctionLiteral update, FunctionLiteral render, FunctionLiteral terminal, GraphNode processNode) {
        this.processData = startData;
        this.update = update;
        this.render = render;
        this.terminal = terminal;

        this.processNode = processNode;

        this.setTitle(processNode.getName());

        final Label label = new Label("this is to be replaced with the apps data");
        VBox container = new VBox(label);

        Scene scene = new Scene(container);

        scene.setOnKeyPressed(event -> {
            String eventString = event.getText();
            if (eventString != null && eventString.length() > 0) {
                char character = eventString.charAt(0);
                keyBuffer.offer(character); // Add the character to the buffer
            }
        });



        this.setScene(scene);
        this.show();


        // Start a new thread for your while loop
        Thread loopThread = new Thread(() -> {
            StringBuilder s = new StringBuilder();
            while (!Thread.currentThread().isInterrupted()) {
                StringBuilder currentS = new StringBuilder(s);

                // Process characters from the buffer
                Character character = null;
                //int i =
                while (!keyBuffer.isEmpty()) {
                    character = keyBuffer.poll();
                    currentS.append(character); // Append character to the current string
                }

                // process the input with the input function provided
                CharLiteral inputKey = new CharLiteral();
                if (character == null) inputKey.setLiteral('_'); // provide a useless character
                else inputKey.setLiteral(character);
                HashMap<String, ASTNode> args = new HashMap<>();
                args.put("app_data", processData);
                args.put("input_key", inputKey);
                processData = update.callWithArgs(args);

                // Update the label's text on the JavaFX application thread
                Platform.runLater(() -> {
                    if (render == null) {
                        // use terminal
                        HashMap<String, ASTNode> terminalArgs = new HashMap<>();
                        terminalArgs.put("app_data", processData);
                        ArrayLiteral terminalOutput = (ArrayLiteral) terminal.callWithArgs(terminalArgs);
                        String outputText = terminalOutput.getElements().stream().map(
                                        el -> ((CharLiteral) el).getLiteral())
                                                    .collect(Collector.of(
                                                        StringBuilder::new,
                                                        StringBuilder::append,
                                                        StringBuilder::append,
                                                        StringBuilder::toString));
                        label.setText(outputText);

                    } else {
                        // use GUI
                    }
                    //label.setText(currentS.toString()); // Convert StringBuilder to String
                });

                // Set the updated value back to 's'
                s = currentS;

                // Optionally add a sleep to control the loop speed
                try {
                    Thread.sleep(10); // Sleep for 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
        });

        this.setOnCloseRequest((WindowEvent event) -> {
            // Shutdown the loopThread when the stage is closed
            if (loopThread != null && loopThread.isAlive()) {
                loopThread.interrupt();
            }
        });

        // Start the loop thread
        loopThread.start();
    }


}
