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
    exports com.example.kernlang.interpreter.frontend.lexer;
    opens com.example.kernlang.interpreter.frontend.lexer to javafx.fxml;
    exports com.example.kernlang.interpreter.frontend.parser;
    opens com.example.kernlang.interpreter.frontend.parser to javafx.fxml;
    exports com.example.kernlang.interpreter.frontend.parser.expressions;
    opens com.example.kernlang.interpreter.frontend.parser.expressions to javafx.fxml;
    exports com.example.kernlang.interpreter.frontend.parser.statements;
    opens com.example.kernlang.interpreter.frontend.parser.statements to javafx.fxml;
}