package com.myapp.school.controller;

import com.myapp.school.model.Role;
import com.myapp.school.model.User;
import com.myapp.school.service.AdminService;
import com.myapp.school.service.StudentService;
import com.myapp.school.service.TeacherService;
import com.myapp.school.service.UserService;
import com.myapp.school.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class CreateProfileController {
    private final UserService userService = new UserService();

    private final AdminService adminService = new AdminService();

    @FXML
    TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML PasswordField confirmPasswordField;

    @FXML
    Label usernameErrorLabel;
    @FXML Label confirmPasswordErrorLabel;

    @FXML
    Button createAccountBtn;

    @FXML
   private ToggleGroup roleGroup = new ToggleGroup();

    @FXML public void initialize(){
        if (userService.userNameExits(usernameField.getText())){
            usernameErrorLabel.setText("Username exists!");
            usernameErrorLabel.setVisible(true);
        }
        if (!userService.userNameExits(usernameField.getText())){
            usernameErrorLabel.setVisible(false);
        }
        confirmPasswordField.textProperty().addListener((obs,oldval,newval)->checkPasswords());
        if (!userService.userNameExits(usernameField.getText())&&passwordField.getText().equals(confirmPasswordField.getText())&&roleGroup.getSelectedToggle()!=null){
            createAccountBtn.setDisable(false);
        }

        createAccountBtn.disableProperty().bind(
                usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()).or(
                        passwordField.textProperty().isNotEqualTo(confirmPasswordField.textProperty()).or(roleGroup.selectedToggleProperty().isNull())
                )
        );

}
@FXML private void handleCreateAccount(){
        User user = new User(usernameField.getText(), passwordField.getText());
        Role role = Role.valueOf(getSelectedRole());
    user.setRole(role);
        userService.createUser(user);
        userService.saveUserIDandRole(userService.findId(user.getUsername()),getSelectedRole());
    Session.setCurrentUser(user);

    try {
        com.myapp.school.Main.loadScene("/fxml/createProfile2_view.fxml");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML private void gotoLogin(){
    try {
        com.myapp.school.Main.loadScene("/fxml/login_view.fxml");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML private String getSelectedRole(){
        Toggle selected = roleGroup.getSelectedToggle();

        if(selected==null) return null;
        return selected.getUserData().toString();

}
private void checkPasswords(){
    if(!passwordField.getText().equals(confirmPasswordField.getText())){
        confirmPasswordErrorLabel.setText("Passwords do not march!");
        confirmPasswordErrorLabel.setVisible(true);}
    else confirmPasswordErrorLabel.setVisible(false);
}
}
