package com.myapp.school.controller;


import com.myapp.school.model.Role;
import com.myapp.school.model.User;
import com.myapp.school.service.*;
import com.myapp.school.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Set;

public class LoginController {
    private final AdminService adminService = new AdminService();
    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();
    private final UserService userService = new UserService();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();


        if(username.isEmpty()||password.isEmpty()){
            errorLabel.setText("Please enter username and password");
            return;
        }
        if (!userService.userNameExits(username)){
            errorLabel.setText("Username doesn't exist");
            return;
        }
        AuthService authService = new AuthService();

        if(authService.authenticate(username,password)){
            User user = new User(username,password);
            int userID = userService.findId(username);
            user.setId(userID);
            user.addRole(userService.findRolesbyUserId(userID));
            studentService.printId();

            Session.setCurrentUser(user);

            if (!studentService.checkIfUserIdExists(userID)&&!teacherService.checkIfUserIdExists(userID)&&!adminService.checkIfUserIdExists(userID)){
                try {
                    com.myapp.school.Main.loadScene("/fxml/createProfile2_view.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else  try{
                if (Session.currentUserhasRole(Role.ADMIN)) {
                com.myapp.school.Main.loadScene("/fxml/admin_layout_view.fxml");}
                else if (Session.currentUserhasRole(Role.TEACHER)){
                    com.myapp.school.Main.loadScene("/fxml/teacher_layout_view.fxml");
                } else if (Session.currentUserhasRole(Role.STUDENT)){
                    com.myapp.school.Main.loadScene("/fxml/student_layout_view.fxml");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }} else { errorLabel.setText("invalid Credentials");


        }
    } @FXML
    private void handleCreateProfileBtn() {
        try {
            com.myapp.school.Main.loadScene("/fxml/createProfile_view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
