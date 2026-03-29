package com.myapp.school.model;

import java.time.LocalDate;

public class Attendance {
   private String studentName;
   private int id;
   private int teacherID;
   private int studentID;
   private LocalDate date;
   private AttendanceStatus status;

    public Attendance(int teacherID,int studentID,LocalDate date,AttendanceStatus status){
        this.teacherID = teacherID; this.studentID = studentID; this.date = date; this.status = status;
    }

    public Attendance(int id,int teacherID,int studentID,LocalDate date,AttendanceStatus status){
       this.id = id; this.teacherID = teacherID; this.studentID = studentID; this.date = date; this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getId() {
        return id;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getStudentID() {
        return studentID;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}

