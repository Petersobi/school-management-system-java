package com.myapp.school.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class NavigationService {
    private static BorderPane mainLayout;
    public static void setMainLayout(BorderPane layout){
        mainLayout = layout;
    }
    public static void load(String fxml){
        try {
            Parent view = FXMLLoader.load(NavigationService.class.getResource("/fxml/" + fxml));
            mainLayout.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
