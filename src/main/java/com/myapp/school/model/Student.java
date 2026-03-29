package com.myapp.school.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Student {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String studentName;
    private String className;
    private AttendanceStatus attendanceStatus;

    public Student (String firstName,String lastName,String className){
         this.firstName = firstName;this.lastName = lastName; this.className = className;
    }
    public Student ( int userId ,String firstName,String lastName,String className){
      this.userId = userId;  this.firstName = firstName;this.lastName = lastName; this.className = className;
    }

    public Student ( int id,int userId ,String firstName,String lastName,String studentName,String className){
        this.id = id; this.userId = userId;  this.firstName = firstName;this.lastName = lastName; this.className = className;
    }
    public Student ( int id,int userId ,String firstName,String lastName,String className){
        this.id = id; this.userId = userId;  this.firstName = firstName;this.lastName = lastName; this.className = className;
    }

    public void setID(int id){this.id = id;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClassName(){return className;}

    public int getUserId() {
        return userId;
    }

    private final ObjectProperty<AttendanceStatus> attendanceStatusO =
            new SimpleObjectProperty<>();
    public ObjectProperty<AttendanceStatus> attendanceStatusProperty(){
        return attendanceStatusO;
    }
    public AttendanceStatus getAttendanceStatus(){
        return attendanceStatusO.get();
    }
    public void setAttendanceStatus(AttendanceStatus status){
        attendanceStatusO.set(status);
    }

}
