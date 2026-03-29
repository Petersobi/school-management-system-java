package com.myapp.school.controller;

import com.myapp.school.model.Admin;
import com.myapp.school.model.Role;
import com.myapp.school.model.Student;
import com.myapp.school.model.Teacher;
import com.myapp.school.service.AdminService;
import com.myapp.school.service.StudentService;
import com.myapp.school.service.TeacherService;
import com.myapp.school.service.UserService;
import com.myapp.school.session.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateProfile2Controller {
@FXML
  VBox adminBox;
@FXML VBox studentBox;
@FXML VBox teacherBox;

@FXML
  TextField firstnameField;
@FXML TextField lastnameField;
@FXML TextField tokenField;

@FXML
  HBox teacherHBox;
@FXML HBox studentHBox;

@FXML
  ComboBox<String> chooseClass;
@FXML ComboBox<String> chooseSubject;

@FXML
  Button completeProfileBtn;

private final UserService userService = new UserService();
private final TeacherService teacherService = new TeacherService();
private final StudentService studentService = new StudentService();
private final AdminService adminService = new AdminService();


  @FXML
  public void initialize(){
    chooseClass.setItems(FXCollections.observableArrayList("JSS1","JSS2","JSS3","SS1","SS2","SS3"));
    chooseSubject.setItems(FXCollections.observableArrayList("English","Mathematics","Physics","Chemistry","Biology","French","Further Maths"));

    if (Session.currentUserhasRole(Role.ADMIN)) {
      adminBox.setVisible(true);
    completeProfileBtn.disableProperty().bind(
            firstnameField.textProperty().isEmpty().or(lastnameField.textProperty().isEmpty())
    );} else
    if (Session.currentUserhasRole(Role.STUDENT)) {
      studentHBox.setVisible(true);
        studentBox.setVisible(true);
      completeProfileBtn.disableProperty().bind(
              firstnameField.textProperty().isEmpty().or(lastnameField.textProperty().isEmpty()).or(chooseClass.valueProperty().isNull())
      );} else if (Session.currentUserhasRole(Role.TEACHER)) {
      teacherBox.setVisible(true);
      teacherHBox.setVisible(true);
      completeProfileBtn.disableProperty().bind(
              firstnameField.textProperty().isEmpty().or(lastnameField.textProperty().isEmpty()).or(chooseSubject.valueProperty().isNull())
      );}


    }
    @FXML private void handleCompleteProfile(){
      int userId = userService.findId(Session.getCurrentUser().getUsername());
    if(Session.currentUserhasRole(Role.STUDENT)) {
      Student student = new Student(userId,firstnameField.getText(),lastnameField.getText(),chooseClass.getValue());
      studentService.addStudent(student);
    } else
      if(Session.currentUserhasRole(Role.TEACHER)){
        Teacher teacher = new Teacher(userId,firstnameField.getText(),lastnameField.getText(),chooseSubject.getValue());
        teacherService.addTeacher(teacher);
      } else if (Session.currentUserhasRole(Role.ADMIN)) {
        Admin admin = new Admin(userId,firstnameField.getText(),lastnameField.getText());
        adminService.save(admin);
      }
      try { if (Session.currentUserhasRole(Role.ADMIN)) {
        com.myapp.school.Main.loadScene("/fxml/admin_layout_view.fxml");}
      else if (Session.currentUserhasRole(Role.TEACHER)){
        com.myapp.school.Main.loadScene("/fxml/teacher_layout_view.fxml");
      } else if (Session.currentUserhasRole(Role.STUDENT)){
        com.myapp.school.Main.loadScene("/fxml/student_layout_view.fxml");
      }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
}
