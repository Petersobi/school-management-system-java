package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.Student;
import com.myapp.school.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {
    public void assignStudentToTeacher(int teacherID,int studentID){
        String sql = """
                INSERT OR IGNORE INTO teachers_students (teacher_id, student_id)
                VALUES (?, ?)
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1,teacherID);
            ps.setInt(2, studentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("failed to assign student to teacher",e);
        }
    }
    public void removeStudentFromTeacher(int teacherID, int studentID){
        String sql = """
                DELETE FROM teachers_students
                WHERE teacher_id = ? AND student_id = ?
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ) {
            ps.setInt(1,teacherID);
            ps.setInt(2,studentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove student from teacher",e);
        }
    }
    public List<Student> findStudentsForTeacher(int teacherID) {
        List<Student> students = new ArrayList<>();
        String sql = """
                SELECT s.id, s.first_name, s.last_name, s.class_name
                FROM students s
                JOIN teachers_students ts ON s.id = ts.student_id
                WHERE ts.teacher_id = ?
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, teacherID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                students.add(new Student(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("class_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch students for Teacher", e);
        }
        return students;
    }
    public List<Teacher> findTeachersForStudent(int studentID) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = """
                SELECT t.id, t.first_name, t.last_name, t.subject
                FROM teachers t
                JOIN teachers_students ts ON t.id = ts.teacher_id
                WHERE ts.student_id = ?
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                teachers.add(new Teacher(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("subject")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch Teachers for Student", e);
        }
        return teachers;
    }

}
