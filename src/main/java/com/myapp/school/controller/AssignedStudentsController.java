package com.myapp.school.controller;

import com.myapp.school.model.Student;
import com.myapp.school.service.AssignmentService;
import com.myapp.school.service.TeacherService;
import com.myapp.school.session.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssignedStudentsController {
    @FXML
    private TableView<Student> assignedStudentsTable;
    @FXML private TableColumn<Student,String> studentFirstNameCol;
    @FXML private TableColumn<Student,String> studentLastNameCol;
    @FXML private TableColumn<Student,String> studentClassCol;

    private final AssignmentService assignmentService = new AssignmentService();
    private final TeacherService teacherService = new TeacherService();

    public void initialize(){


        studentFirstNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        studentLastNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        studentClassCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClassName()));


        assignedStudentsTable.setItems(assignmentService.getStudentsForTeacher(teacherService.getTeacherId(Session.getCurrentUserId())));

    }
}
