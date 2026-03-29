package com.myapp.school;
import com.myapp.school.dao.AssignmentDAO;
import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.db.DataBaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {
    private static Stage primaryStage;
    public void start(Stage stage) throws Exception{
        primaryStage=stage;
        loadScene("/fxml/login_view.fxml");

        stage.setTitle("School Management System");
        stage.show();
        DataBaseInitializer.initialize();
        try (Connection conn = DataBaseConnection.getConnection()){
            System.out.println("Database Connected");
        }



    }
    public static void loadScene(String fxmlPath) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(root,1000,600);
        scene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    public static void main(String[] args){
        launch(args);
    }
}
