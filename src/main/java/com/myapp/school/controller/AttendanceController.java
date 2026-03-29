package com.myapp.school.controller;

import com.myapp.school.model.AttendanceStatus;
import com.myapp.school.model.Student;
import com.myapp.school.service.AttendanceService;
import com.myapp.school.service.NavigationService;
import com.myapp.school.service.StudentService;
import com.myapp.school.session.Session;
import com.myapp.school.ui.ErrorHandler;
import com.myapp.school.ui.InformationHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class AttendanceController {
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student,Integer> idCol;
    @FXML
    private TableColumn<Student,String> nameCol;
    @FXML
    private TableColumn<Student, AttendanceStatus> statusCol;

    @FXML
    private Button saveBtn;

    private final AttendanceService attendanceService = new AttendanceService();
    private final StudentService studentService = new StudentService();

    @FXML
    public void initialize(){
        idCol.setCellValueFactory( new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentTable.setEditable(true);
        statusCol.setEditable(true);

        setupStatusColumn();

        studentTable.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));

    }
    private void setupStatusColumn(){
        statusCol.setCellValueFactory(c -> c.getValue().attendanceStatusProperty());
        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(AttendanceStatus.values())));
    }
    @FXML
    private void handleSave(){
        boolean anyMarked = false;
        int teacherId = Session.getCurrentUserId();
        LocalDate date = LocalDate.now();

        for (Student student : studentTable.getItems()){
            AttendanceStatus status = student.getAttendanceStatus();

            if(status==null) continue;
            anyMarked = true;

            attendanceService.markAttendance(teacherId,student.getId(),date,status);
        }
        if (!anyMarked){
            ErrorHandler.show("No Attendance Marked");
            return;
        }
        InformationHandler.showInfo("Attendance saved successfully");
        saveBtn.setDisable(true);
        for (Student student : studentTable.getItems()){
            student.setAttendanceStatus(null);
        } studentTable.refresh();
    }
    @FXML private void handleAttendanceHistory(){
        NavigationService.load("attendance_history_view.fxml");
    }
}
