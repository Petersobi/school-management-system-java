package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public void save(Admin admin){
        String sql = """
                INSERT INTO admins(user_id,first_name,last_name)
                VALUES (?,?,?)
                """;
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement prs = connection.prepareStatement(sql)) {
            prs.setInt(1,admin.getUserId());
            prs.setString(2,admin.getFirstname());
            prs.setString(3, admin.getLastname());
            prs.executeUpdate();
            ResultSet keys = prs.getGeneratedKeys();
            if (keys.next()){
                admin.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not save Admin"+e);
        }

        }
    public boolean checkUserIDExists(int userID){
        String sql = """
                SELECT 1
                FROM admins
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

}
