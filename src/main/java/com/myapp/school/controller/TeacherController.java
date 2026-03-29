package com.myapp.school.controller;

import com.myapp.school.model.Student;
import com.myapp.school.model.Teacher;
import com.myapp.school.service.AssignmentService;
import com.myapp.school.service.NavigationService;
import com.myapp.school.service.StudentService;
import com.myapp.school.service.TeacherService;
import com.myapp.school.session.Session;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class TeacherController
{
    @FXML private TableView<Teacher> teacherTable;
    @FXML private TableColumn<Teacher,Integer> idColumn;
    @FXML private TableColumn<Teacher,String> firstNameColumn;
    @FXML private TableColumn<Teacher,String> lastNameColumn;
    @FXML private TableColumn<Teacher,String> subjectColumn;

    @FXML private TableView<Student> assignedStudentsTable;
    @FXML private TableColumn<Student,String> studentFirstNameCol;
    @FXML private TableColumn<Student,String> studentLastNameCol;
    @FXML private TableColumn<Student,String> studentClassCol;

    @FXML private ComboBox<Student> studentComboBox;

    @FXML private TextField searchField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> subjectBox;

    private final int Rows_Per_Page = 10;
    @FXML
    private Pagination pagination;

    private final AssignmentService assignmentService = new AssignmentService();
    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();

    private ObservableList<Teacher> masterList;
    private FilteredList<Teacher> filteredList;
    private SortedList<Teacher> sortedList;

    @FXML
    public void initialize(){
        subjectBox.setItems(FXCollections.observableArrayList("Mathematics","English","Physics","Chemistry","Biology","French","Further Maths"));
        idColumn.setCellValueFactory(data ->new SimpleIntegerProperty(data.getValue().getId()).asObject());
        firstNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));

        lastNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));

        subjectColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubject()));

        teacherTable.getSelectionModel().selectedItemProperty().addListener((obs,old,selected)->{
            if(selected!=null){
                firstNameField.setText(selected.getFirstName());
                lastNameField.setText(selected.getLastName());
                subjectBox.setValue(selected.getSubject());

                assignedStudentsTable.setItems(assignmentService.getStudentsForTeacher(selected.getId()));
            } else assignedStudentsTable.getItems().clear();

        });

        masterList = teacherService.getAllTeachers();
        filteredList = new FilteredList<>(masterList,p -> true);
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(teacherTable.comparatorProperty());
        sortedList.addListener((ListChangeListener<Teacher>) c -> setupPagination() );
        setupPagination();

        setUpSearch();

        studentFirstNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
        studentLastNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
        studentClassCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClassName()));

        studentComboBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student == null ? "" : student.getStudentName();
            }

            @Override
            public Student fromString(String s) {
                return null;
            }
        });
        studentComboBox.setCellFactory(cb -> new ListCell<>(){
            @Override
            protected void updateItem(Student student,boolean empty) {
                super.updateItem(student, empty);
                setText(empty || student==null ? "" : student.getStudentName());
            }
        });
        studentComboBox.setItems(studentService.getAllStudents());

        teacherTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        System.out.println(Session.getCurrentUserId());
    }
    private void setUpSearch(){
        searchField.textProperty().addListener((obs,oldVal,newVal)->{
            String keyword = newVal.toLowerCase().trim();
            filteredList.setPredicate(teacher -> {
                if (keyword.isEmpty())
                return true;
                return teacher.getFirstName().toLowerCase().contains(keyword)
                        || teacher.getLastName().toLowerCase().contains(keyword)|| teacher.getSubject().contains(keyword);
            });
        });
    }
    @FXML
    private void handleAddTeacher(){
        Teacher teacher = new Teacher(firstNameField.getText(),lastNameField.getText(),subjectBox.getValue());
        teacherService.addTeacher(teacher);
        masterList.add(teacher);
        clearFields();
    }
    @FXML
    private void handleDeleteTeacher(){
        Teacher selected = teacherTable.getSelectionModel().getSelectedItem();
        if (selected==null) return;

        teacherService.deleteTeacher(selected);
        masterList.remove(selected);
        clearFields();
    }
    @FXML
    private void handleUpdateTeacher(){
        Teacher selected = teacherTable.getSelectionModel().getSelectedItem();

        selected.setFirstName(firstNameField.getText().trim());
        selected.setLastName(lastNameField.getText().trim());
        selected.setSubject(subjectBox.getValue().trim());

        teacherService.updateTeacher(selected);
        teacherTable.refresh();
        clearFields();

    }
    @FXML
    private void handleAssignStudent(){
        Teacher teacher = teacherTable.getSelectionModel().getSelectedItem();
        Student student = studentComboBox.getValue();

        if (teacher==null|| student==null) return;

        assignmentService.assignStudent(teacher.getId(),student);
        assignedStudentsTable.getItems().add(student);

    }
    @FXML
    private void handleRemoveStudent(){
        Teacher teacher = teacherTable.getSelectionModel().getSelectedItem();
        Student student = assignedStudentsTable.getSelectionModel().getSelectedItem();

        if (teacher==null|| student==null) return;

        assignmentService.removeStudent(teacher.getId(),student);
        assignedStudentsTable.getItems().remove(student);

    }

    private void setupPagination(){
        int pageCount = (int) Math.ceil((double) sortedList.size()/Rows_Per_Page);

        pagination.setPageCount(Math.max(pageCount,1));
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * Rows_Per_Page;
        int toIndex = Math.min(fromIndex + Rows_Per_Page,sortedList.size());
        teacherTable.setItems(FXCollections.observableArrayList(sortedList.subList(fromIndex,toIndex)));
        return teacherTable;

    }
    private void clearFields(){
        firstNameField.clear();
        lastNameField.clear();
        subjectBox.setValue(null);
    }
    @FXML
    private void handleShowAttendance(){
        NavigationService.load("attendance_view.fxml");
    }
    @FXML
    private void handleShowAttendanceHistory(){
        NavigationService.load("attendance_history_view.fxml");
    }

}
