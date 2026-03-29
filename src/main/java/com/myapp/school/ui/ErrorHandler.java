package com.myapp.school.ui;

import javafx.scene.control.Alert;

public class ErrorHandler {
    public static void show(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
