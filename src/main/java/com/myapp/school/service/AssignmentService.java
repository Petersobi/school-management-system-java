package com.myapp.school.service;

import com.myapp.school.dao.AssignmentDAO;
import com.myapp.school.model.Student;
import com.myapp.school.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssignmentService {
    private final AssignmentDAO assignmentDAO = new AssignmentDAO();

    public void assignStudent(int teacherID, Student student){
        assignmentDAO.assignStudentToTeacher(teacherID,student.getId());
    }
    public void removeStudent(int teacherID, Student student){
        assignmentDAO.removeStudentFromTeacher(teacherID,student.getId());
    }
    public ObservableList<Student> getStudentsForTeacher(int teacherID){
        return FXCollections.observableArrayList(assignmentDAO.findStudentsForTeacher(teacherID));
    }
    public ObservableList<Teacher> getTeachersForStudent(int studentID){
        return FXCollections.observableArrayList(assignmentDAO.findTeachersForStudent(studentID));
    }
}
