package com.myapp.school.service;

import com.myapp.school.dao.AttendanceDAO;
import com.myapp.school.model.Attendance;
import com.myapp.school.model.AttendanceStatus;
import com.myapp.school.model.AttendanceSummary;

import java.time.LocalDate;
import java.util.List;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public void markAttendance (int teacherId, int studentId, LocalDate date, AttendanceStatus status){
        if (status !=null){
        Attendance attendance = new Attendance(teacherId,studentId,date,status);
        try {
            attendanceDAO.save(attendance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        }

    }
    public List<Attendance> getAttendanceForTeacher(int teacherId,LocalDate date){
        return attendanceDAO.findByTeacherAndDate(teacherId,date);
    }
    public AttendanceSummary getStudentSummary(int studentID){
        return attendanceDAO.getSummaryForStudent(studentID);
    }
}
