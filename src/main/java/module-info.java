module com.example.kernlang {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kernlang to javafx.fxml;
    exports com.example.kernlang;
    exports com.example.kernlang.codebase_viewer.graph;
    opens com.example.kernlang.codebase_viewer.graph to javafx.fxml;
    exports com.example.kernlang.codebase_viewer;
    opens com.example.kernlang.codebase_viewer to javafx.fxml;
    exports com.example.kernlang.codebase_viewer.popup_screens;
    opens com.example.kernlang.codebase_viewer.popup_screens to javafx.fxml;
    exports com.example.kernlang.interpreter;
    opens com.example.kernlang.interpreter to javafx.fxml;
    exports com.example.kernlang.interpreter.lexer;
    opens com.example.kernlang.interpreter.lexer to javafx.fxml;
    exports com.example.kernlang.interpreter.parser;
    opens com.example.kernlang.interpreter.parser to javafx.fxml;
}