package com.myapp.school.service;

import com.myapp.school.dao.StudentDAO;
import com.myapp.school.model.Student;
import com.myapp.school.ui.AppLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();

    public ObservableList<Student> getAllStudents(){
        return FXCollections.observableArrayList(studentDAO.findAll());
    }

    public void addStudent(Student student){
        try {
        studentDAO.save(student);} catch (Exception e) {
            AppLogger.logError("Failed to save student" ,e);
            throw e;
        }
    }
    public void removeStudent(Student student){
        if (student == null){
            return;
        }
        studentDAO.deleteByID(student.getId());
    }
    public void updateStudent(Student student){
        studentDAO.update(student);
    }
    public boolean checkIfUserIdExists(int userID){
      return   studentDAO.checkUserIDExists(userID);
    }
    public void printId(){
        studentDAO.printAllUsername();
    }
    public int fetchStudentID(int userID){
     return  studentDAO.getStudentID(userID);
    }
}
