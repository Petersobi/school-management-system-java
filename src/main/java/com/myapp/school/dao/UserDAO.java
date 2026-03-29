package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.User;

import java.sql.*;

public class UserDAO {
    public void save(User user) {
        String sql = """
                INSERT INTO users (username,password_hash)
                VALUES (?,?)
                """;
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement prs = connection.prepareStatement(sql)){
            prs.setString(1,user.getUsername());
            prs.setString(2, user.getPassword());
            prs.executeUpdate();

            ResultSet keys = prs.getGeneratedKeys();
            if (keys.next()){
                user.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not save user" +e);
        }
    }
    public String findPasswordByUsername(String username){

        String sql = """
                SELECT password_hash
                FROM users
                WHERE username = ?
                """;
        try (Connection conn = DataBaseConnection.getConnection();
        PreparedStatement prs = conn.prepareStatement(sql)) {
            prs.setString(1,username);

            ResultSet resultSet = prs.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("password_hash");} return null;



        } catch (SQLException e) {
            throw new RuntimeException("Could not fetch password"+e);
        }
    }
    public boolean checkUsernameExists(String username){
        String sql = """
                SELECT 1
                FROM users
                WHERE username = ?
                """;
        try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement prs = connection.prepareStatement(sql)) {
            prs.setString(1,username);
            ResultSet rs = prs.executeQuery();
                return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("could not check for username"+e);

        }
    }
    public int findUserIdByUsername(String username){

        String sql = """
                SELECT id
                FROM users
                WHERE username = ?
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement prs = conn.prepareStatement(sql)) {
            prs.setString(1,username);

            ResultSet resultSet = prs.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("id");} return 0;



        } catch (SQLException e) {
            throw new RuntimeException("Could not fetch user ID"+e);
        }
    }
    public void printAllUsername(){
        String sql = """
                SELECT username FROM users
                """;
        try (Connection conn = DataBaseConnection.getConnection();
        PreparedStatement prs = conn.prepareStatement(sql)) {
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("username"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
