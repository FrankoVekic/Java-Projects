module com.main.inputwindowtransferapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.main.inputwindowtransferapp to javafx.fxml;
    exports com.main.inputwindowtransferapp;
}
