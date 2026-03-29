package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = """
                SELECT id,user_id, first_name, last_name,class_name,
                first_name || ' ' || last_name AS student_name
                FROM students
                """;

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("class_name")
                );
                student.setStudentName(rs.getString("student_name"));
                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return students;
    }
    public void save(Student student){
        String sql = """
                Insert INTO students (user_id,first_name,last_name,class_name)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, student.getUserId());
            ps.setString(2,student.getFirstName());
            ps.setString(3,student.getLastName());
            ps.setString(4, student.getClassName());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()){
                student.setID(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteByID(int id){
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Student student){
        String sql = """
                UPDATE students
                SET first_name = ?, last_name = ?, class_name = ?
                WHERE id = ?
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, student.getFirstName());
            ps.setString(2,student.getLastName());
            ps.setString(3,student.getClassName());
            ps.setInt(4,student.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean checkUserIDExists(int userID){
        String sql = """
                SELECT 1
                FROM students
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
    public void printAllUsername(){
        String sql = """
                SELECT id FROM students
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement prs = conn.prepareStatement(sql)) {
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
    public int getStudentID(int userId){
        String sql = """
                SELECT id
                FROM students
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
            throw new RuntimeException("could not fetch studentID"+e);

        }


    }
}
