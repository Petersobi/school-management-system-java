package com.myapp.school.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InformationHandler {
    public static void showInfo(String msg){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(msg);
            alert.setHeaderText("Information");
            alert.showAndWait();
    }
    public static void showConfirmation(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.setHeaderText("Confirmation");
        alert.showAndWait().ifPresent(response->{
            if (response == ButtonType.CANCEL){
                return;
            }
        });
    }
}
