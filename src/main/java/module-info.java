module com.example.kernlang {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kernlang to javafx.fxml;
    exports com.example.kernlang;
    exports com.example.kernlang.codebase_viewer.graph;
    opens com.example.kernlang.codebase_viewer.graph to javafx.fxml;
    exports com.example.kernlang.codebase_viewer;
    opens com.example.kernlang.codebase_viewer to javafx.fxml;
    exports com.example.kernlang.codebase_viewer.popup_screens;
    opens com.example.kernlang.codebase_viewer.popup_screens to javafx.fxml;
    exports com.example.kernlang.compiler.parser;
    opens com.example.kernlang.compiler.parser to javafx.fxml;
    exports com.example.kernlang.compiler.parser.expressions;
    opens com.example.kernlang.compiler.parser.expressions to javafx.fxml;
    exports com.example.kernlang.compiler.parser.statements;
    opens com.example.kernlang.compiler.parser.statements to javafx.fxml;
    exports com.example.kernlang.compiler.parser.expressions.literals;
    opens com.example.kernlang.compiler.parser.expressions.literals to javafx.fxml;
    exports com.example.kernlang.compiler.ast_visitors;
    opens com.example.kernlang.compiler.ast_visitors to javafx.fxml;
}