package com.myapp.school.controller;

import com.myapp.school.model.Student;
import com.myapp.school.service.StudentService;
import com.myapp.school.ui.ErrorHandler;
import com.myapp.school.validation.StudentValidator;
import com.myapp.school.validation.ValidationResult;
import com.myapp.school.validation.Validator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentController {
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student,Integer> idColumn;
    @FXML
    private TableColumn<Student,String> firstNameColumn;
    @FXML
    private TableColumn<Student,String> lastNameColumn;
    @FXML
    private TableColumn<Student,String> classColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField firstField;
    @FXML
    private TextField lastField;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField searchField;
    private ObservableList<Student> masterList;
    private FilteredList<Student> filteredList;
    private SortedList<Student> sortedList;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Pagination pagination;
    private static final int Rows_Per_Page = 10;


    private final StudentService studentService = new StudentService();

    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(data ->new SimpleIntegerProperty(data.getValue().getId()).asObject());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("className"));


        studentTable.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));
        deleteBtn.disableProperty().bind(studentTable.getSelectionModel().selectedItemProperty().isNull());
        idField.setVisible(false);

        comboBox.setItems(FXCollections.observableArrayList("JSS1","JSS2","JSS3","SS1","SS2","SS3"));

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs,old,selected)->{
            if(selected!=null){
                firstField.setText(selected.getFirstName());
                lastField.setText(selected.getLastName());
                comboBox.setValue(selected.getClassName());
            }
        });
        masterList = FXCollections.observableArrayList(studentService.getAllStudents());
        filteredList = new FilteredList<>(masterList,p -> true);
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
        sortedList.addListener((ListChangeListener<Student>) c -> setupPagination() );
        setupPagination();

        studentTable.setItems(sortedList);

        searchField.textProperty().addListener((obs, old, text)->{
            String lower = text.toLowerCase();
            filteredList.setPredicate(student -> {
                if (text == null || text.isEmpty()){
                    return true;
                } return student.getFirstName().toLowerCase().contains(lower)||student.getLastName().toLowerCase().contains(lower)
                        ||student.getClassName().toLowerCase().contains(lower);
            });
        });
    }
    @FXML
    private void handleAddStudent(){
        String firstName = firstField.getText();
        String lastName = lastField.getText();
        String className = comboBox.getValue();
        Student student = new Student(firstName,lastName,className);

        Validator<Student> validator = new StudentValidator();
        ValidationResult result = validator.validate(student);

        if (!result.isValid()){
            ErrorHandler.show(result.getMessage());
            return;
        }

        masterList.add(student);
        studentService.addStudent(student);

        idField.clear(); firstField.clear(); lastField.clear(); comboBox.setValue(null);
    }
    @FXML
    private void handleDeleteStudent(){
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent==null){
            return;
        }
        masterList.remove(selectedStudent);
        studentService.removeStudent(selectedStudent);
        studentTable.getItems().remove(selectedStudent);
    }


    public void handleUpdateStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        Validator<Student> validator = new StudentValidator();
        ValidationResult result = validator.validate(selected);

        if (!result.isValid()){
            ErrorHandler.show(result.getMessage());
            return;
        }
        selected.setFirstName(firstField.getText().trim());
        selected.setLastName(lastField.getText().trim());
        selected.setClassName(comboBox.getValue().trim());


        studentService.updateStudent(selected);
        studentTable.refresh();
    }
    private void setupPagination(){
        int pageCount = (int) Math.ceil((double) sortedList.size()/Rows_Per_Page);

        pagination.setPageCount(Math.max(pageCount,1));
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * Rows_Per_Page;
        int toIndex = Math.min(fromIndex + Rows_Per_Page,sortedList.size());
        studentTable.setItems(FXCollections.observableArrayList(sortedList.subList(fromIndex,toIndex)));
       return studentTable;

    }

}
