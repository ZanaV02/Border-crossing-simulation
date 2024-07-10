module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens Main to javafx.fxml;
    exports Main;
}