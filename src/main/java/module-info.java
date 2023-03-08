module com.example.notesapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.notesapp to javafx.fxml;
    exports com.example.notesapp;
}