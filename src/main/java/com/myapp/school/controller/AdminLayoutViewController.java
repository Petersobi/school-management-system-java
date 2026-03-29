package com.myapp.school.controller;

import com.myapp.school.service.NavigationService;
import com.myapp.school.ui.ErrorHandler;
import com.myapp.school.ui.InformationHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AdminLayoutViewController {
    @FXML
    BorderPane rootPane;
    public void initialize(){
        NavigationService.setMainLayout(rootPane);
    }

  @FXML private void handleTeachers(){

      NavigationService.load("teachers_view.fxml");

  }
  @FXML private void handleStudents(){
      NavigationService.load("students_view.fxml");
  }
  @FXML private void handleBackup(){
      FileChooser chooser = new FileChooser();
      chooser.setTitle("Backup DataBase");
      chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQLite DB","*.db"));

      File target = chooser.showSaveDialog(rootPane.getScene().getWindow());
      if(target == null) return;
      try {
          Files.copy(Path.of("school.db"),target.toPath(), StandardCopyOption.REPLACE_EXISTING);
          InformationHandler.showInfo("Database Backup Successful");
      } catch (IOException e) {
          ErrorHandler.show("Backup Failed");
      }
  }
  @FXML private void handleRestore(){
      FileChooser chooser = new FileChooser();
      chooser.setTitle("Restore DataBase");
      chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQLite DB","*.db"));

      File source = chooser.showOpenDialog(rootPane.getScene().getWindow());
      if (source==null) return;

      try {
          Files.copy(source.toPath(),Path.of("school.db"),StandardCopyOption.REPLACE_EXISTING);

          InformationHandler.showInfo("Database restored, restart application");
          Platform.exit();
      } catch (IOException e) {
          ErrorHandler.show("Restoration Failed");
      }

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

  } }

