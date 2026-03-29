package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.Attendance;
import com.myapp.school.model.AttendanceStatus;
import com.myapp.school.model.AttendanceSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    public void save(Attendance attendance){
        String sql = """
               INSERT OR IGNORE INTO attendance (
               teacher_id, student_id, date, status)
               VALUES (?, ?, ?, ?)
               """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement prs = conn.prepareStatement(sql)) {
            prs.setInt(1, attendance.getTeacherID());
            prs.setInt(2,attendance.getStudentID());
            prs.setString(3,attendance.getDate().toString());
            prs.setString(4,attendance.getStatus().name());

            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Attendance> findByTeacherAndDate(int teacherID, LocalDate date){
        List<Attendance> records = new ArrayList<>();
        String sql = """
                SELECT a.id,
                a.teacher_id,
                a.student_id,
                a.date,
                a.status,
                s.first_name || ' ' || s.last_name AS student_name
                FROM attendance a
                JOIN students s ON a.student_id = s.id
                WHERE a.teacher_id = ? AND a.date = ?
                ORDER BY student_name
                """;

        try (Connection conn = DataBaseConnection.getConnection();
        PreparedStatement prs = conn.prepareStatement(sql)) {
            prs.setInt(1,teacherID);
            prs.setString(2,date.toString() );

           ResultSet rs = prs.executeQuery();

           while (rs.next()) {
               Attendance attendance = new Attendance(
                       rs.getInt("id"),
                       rs.getInt("teacher_id"),
                       rs.getInt("student_id"),
                       LocalDate.parse(rs.getString("date")),
                       AttendanceStatus.valueOf(rs.getString("status"))

               );
               attendance.setStudentName(rs.getString("student_name"));
               records.add(attendance);
           }
           System.out.println("Record Size = "+records.size());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return records;
    }
    public AttendanceSummary getSummaryForStudent(int studentID){
        String sql = """
                SELECT
                COUNT(*) AS total,
                SUM(CASE WHEN status = 'PRESENT' THEN 1 ELSE 0 END) AS present,
                SUM(CASE WHEN status = 'ABSENT' THEN 1 ELSE 0 END) AS absent
                FROM attendance
                WHERE student_id = ?
                """;
        try (Connection conn = DataBaseConnection.getConnection();
       PreparedStatement prs = conn.prepareStatement(sql) ) {
            prs.setInt(1,studentID);
         ResultSet rs =   prs.executeQuery();

         if (rs.next()){
             return new AttendanceSummary(
                     rs.getInt("total"),
                     rs.getInt("present"),
                     rs.getInt("absent")
             );
         }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new AttendanceSummary(0,0,0);
    }
}
