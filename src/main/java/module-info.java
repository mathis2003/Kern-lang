module com.example.kernlang {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kernlang to javafx.fxml;
    exports com.example.kernlang;
}