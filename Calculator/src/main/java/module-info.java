module com.main.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.main.calculator to javafx.fxml;
    exports com.main.calculator;
}
