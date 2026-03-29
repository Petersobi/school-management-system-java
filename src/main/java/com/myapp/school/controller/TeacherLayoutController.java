package com.myapp.school.controller;

import com.myapp.school.service.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

public class TeacherLayoutController {
    @FXML BorderPane rootPane;

    public void initialize(){
        NavigationService.setMainLayout(rootPane);


    }
   @FXML
   private void handleMyStudents(){
       NavigationService.load("assigned_students_view.fxml");
   }
    @FXML private void handleMarkAttendance(){
        NavigationService.load("attendance_view.fxml");
    }
    @FXML private void handleGiveAssignments(){

    }
    @FXML private void handleLogOut(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Click Ok to logout,Cancel to return.");
        alert.setHeaderText("Confirmation");
        alert.showAndWait().ifPresent(response->{
            if (response == ButtonType.OK){
                try {
                    com.myapp.school.Main.loadScene("/fxml/login_view.fxml");
                }   catch (Exception e) {
                    e.printStackTrace();
                }}
        });
    }

}
