package com.myapp.school.controller;

import com.myapp.school.model.Attendance;
import com.myapp.school.model.AttendanceStatus;
import com.myapp.school.model.AttendanceSummary;
import com.myapp.school.model.Student;
import com.myapp.school.service.AttendanceService;
import com.myapp.school.service.StudentService;
import com.myapp.school.session.Session;
import com.myapp.school.ui.ErrorHandler;
import com.myapp.school.ui.InformationHandler;
import com.myapp.school.util.CSVExporter;
import com.myapp.school.util.PDFExporter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.time.LocalDate;

public class AttendanceHistoryController {
    @FXML private DatePicker datePicker;

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance,String> studentCol;
    @FXML private TableColumn<Attendance, String> statusCol;
    @FXML private Label totalLabel;
    @FXML private Label presentLabel;
    @FXML private Label absentLabel;
    @FXML private Label percentLabel;
    @FXML private ComboBox<Student> studentBox;
    @FXML private PieChart attendancePieChart;

    private final AttendanceService attendanceService = new AttendanceService();
    private final StudentService studentService = new StudentService();

    @FXML public void initialize(){
        studentCol.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getStudentName()));
        statusCol.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getStatus().name()));
        datePicker.setValue(LocalDate.now());

        studentBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student == null ? "" : student.getStudentName();
            }

            @Override
            public Student fromString(String s) {
                return null;
            }
        });
        studentBox.setCellFactory(cb -> new ListCell<>(){
            @Override
            protected void updateItem(Student student,boolean empty) {
                super.updateItem(student, empty);
                setText(empty || student==null ? "" : student.getStudentName());
            }
        });
        studentBox.setItems(studentService.getAllStudents());

    }
    @FXML
    private void handleLoad() {
        LocalDate date = datePicker.getValue();
        int teacherId = Session.getCurrentUserId();

        attendanceTable.setItems(FXCollections.observableArrayList(attendanceService.getAttendanceForTeacher(teacherId,date)));
    }
    @FXML
    private void handleExportCSV(){
        if (attendanceTable.getItems().isEmpty()){
            ErrorHandler.show("No attendance data to export");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Attendance CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(attendanceTable.getScene().getWindow());

        if (file== null) return;
        CSVExporter.exportAttendance(attendanceTable.getItems(),file.getAbsolutePath());
        InformationHandler.showInfo("Attendance exported successfully");
    }
    @FXML
    private void handleExportPDF(){
        if (attendanceTable.getItems().isEmpty()){
            ErrorHandler.show("No attendance to export!!");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Attendance PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File file = fileChooser.showSaveDialog(attendanceTable.getScene().getWindow());

        if (file== null) return;
        if(!file.getName().endsWith(".pdf")){
            file = new File(file.getAbsolutePath()+ ".pdf");
        }

        PDFExporter.exportAttendance(attendanceTable.getItems(),file.getAbsolutePath(),datePicker.getValue().toString());
        InformationHandler.showInfo("Attendance exported to PDF");
    }

    @FXML private void handleSummary(){
        Student selected = studentBox.getValue();
        if (selected == null) return;

        AttendanceSummary attendanceSummary = attendanceService.getStudentSummary(selected.getId());

        totalLabel.setText(String.valueOf("Total Attendance\n" +attendanceSummary.getTotalDays()));
        presentLabel.setText(String.valueOf("Total Presence\n" +attendanceSummary.getPresentDays()));
        absentLabel.setText(String.valueOf("Total Absence\n" +attendanceSummary.getAbsentDays()));
        percentLabel.setText(String.format("Percentage Attendance\n" +"%.1f%%",attendanceSummary.getPercentage()));
    updatePieChart(attendanceSummary);}

    @FXML private void updatePieChart(AttendanceSummary summary){
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Present",summary.getPresentDays()),
                new PieChart.Data("Absent",summary.getAbsentDays())
        );
        attendancePieChart.setData(data);
        attendancePieChart.setTitle("Attendance Chart");
    }

}
