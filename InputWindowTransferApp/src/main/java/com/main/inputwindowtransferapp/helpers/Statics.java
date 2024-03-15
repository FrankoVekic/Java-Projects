package com.main.inputwindowtransferapp.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Statics {
    
    public static void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
