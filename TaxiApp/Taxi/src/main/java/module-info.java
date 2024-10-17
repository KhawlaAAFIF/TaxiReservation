module com.example.taxi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.taxi to javafx.fxml;
    exports com.example.taxi;
}