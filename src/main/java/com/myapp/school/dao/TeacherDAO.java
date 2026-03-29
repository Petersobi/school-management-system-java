package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.Student;
import com.myapp.school.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "Select * FROM teachers";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("subject")
                );
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch teachers",e);
        } return teachers;
    }
    public void save(Teacher teacher){
        String sql = """
                Insert INTO teachers (user_id,first_name,last_name,subject)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,teacher.getUserId());
            ps.setString(2,teacher.getFirstName());
            ps.setString(3,teacher.getLastName());
            ps.setString(4, teacher.getSubject());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()){
                teacher.setID(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save Teacher",e);
        }
    }
    public void deleteByID(int id){
        String sql = "DELETE FROM teachers WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete teacher",e);
        }
    }
    public void update(Teacher teacher) {
        String sql = """
                UPDATE teachers
                SET first_name = ?, last_name = ?, subject = ?
                WHERE id = ?
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, teacher.getFirstName());
            ps.setString(2, teacher.getLastName());
            ps.setString(3, teacher.getSubject());
            ps.setInt(4, teacher.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Couldn't update teacher",e);
        }
    }
    public boolean checkUserIDExists(int userID){
        String sql = """
                SELECT 1
                FROM teachers
                WHERE user_id = ?
                """;
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement prs = connection.prepareStatement(sql)) {
            prs.setInt(1,userID);
            ResultSet rs = prs.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("could not check for userID"+e);

        }
    }
    public void printTeachers() {
        String sql = """
                SELECT user_Id FROM teachers
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement prs = conn.prepareStatement(sql)) {
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("user_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getTeacherID(int userId){
        String sql = """
                SELECT id
                FROM teachers
                WHERE user_id = ?
                """;
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement prs = connection.prepareStatement(sql)) {
            prs.setInt(1,userId);
            ResultSet rs = prs.executeQuery();

            if(rs.next()){

            return rs.getInt("id");}
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("could not fetch TeacherID"+e);

        }


    }
}
