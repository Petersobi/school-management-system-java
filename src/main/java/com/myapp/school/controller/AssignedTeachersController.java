package com.myapp.school.controller;

import com.myapp.school.model.Teacher;
import com.myapp.school.service.AssignmentService;
import com.myapp.school.service.StudentService;
import com.myapp.school.session.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssignedTeachersController {
    @FXML
    private TableView<Teacher> assignedTeachersTable;
    @FXML private TableColumn<Teacher,String> teacherFirstNameCol;
    @FXML private TableColumn<Teacher,String> teacherLastNameCol;
    @FXML private TableColumn<Teacher,String> teacherSubjectCol;

    private final AssignmentService assignmentService = new AssignmentService();
    private final StudentService studentService = new StudentService();
    public void initialize(){
        teacherFirstNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        teacherLastNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        teacherSubjectCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubject()));


        assignedTeachersTable.setItems(assignmentService.getTeachersForStudent(studentService.fetchStudentID(Session.getCurrentUserId())));

    }
}
