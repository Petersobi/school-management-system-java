package com.myapp.school.dao;

import com.myapp.school.db.DataBaseConnection;
import com.myapp.school.model.Role;
import com.myapp.school.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserRolesDAO {
    public void save(int userId,String role){
        String sql = """
                INSERT INTO user_roles (user_id,role)
                VALUES (?,?)
                """;
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement prs = conn.prepareStatement(sql)) {
            prs.setInt(1,userId);
            prs.setString(2,role);
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(" Failed to saver User Role "+e);
        }
    }
    public Set<Role> findRoleByUserID(int userId){
        Set<Role> roles = new  HashSet<>();
        String sql = """
                SELECT *
                FROM user_roles
                WHERE user_id = ?
                """;
        try (Connection conn = DataBaseConnection.getConnection();
        PreparedStatement prs = conn.prepareStatement(sql)){
            prs.setInt(1,userId);

            ResultSet resultSet = prs.executeQuery();
            while (resultSet.next()){
                roles.add(Role.valueOf(resultSet.getString("role")));
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
