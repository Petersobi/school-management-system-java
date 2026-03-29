package com.myapp.school.service;

import com.myapp.school.dao.TeacherDAO;
import com.myapp.school.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TeacherService {
    private final TeacherDAO teacherDAO = new TeacherDAO();

    public ObservableList<Teacher> getAllTeachers(){
        return FXCollections.observableArrayList(teacherDAO.findAll());
    }
    public void addTeacher(Teacher teacher){ teacherDAO.save(teacher);}

    public void deleteTeacher(Teacher teacher){teacherDAO.deleteByID(teacher.getId());}

    public void updateTeacher(Teacher teacher){teacherDAO.update(teacher);}

    public boolean checkIfUserIdExists(int userId){
        return teacherDAO.checkUserIDExists(userId);
    }
    public void printAllTeachers(){
        teacherDAO.printTeachers();
    }
    public int getTeacherId(int userId){
        return teacherDAO.getTeacherID(userId);
    }

}
