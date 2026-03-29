package com.myapp.school.service;

import com.myapp.school.dao.UserDAO;
import com.myapp.school.dao.UserRolesDAO;
import com.myapp.school.model.Role;
import com.myapp.school.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Set;

public class UserService {
    private final UserDAO userDAO = new UserDAO();
    private final UserRolesDAO userRolesDAO = new UserRolesDAO();

    public void createUser(User user){
        String username = user.getUsername();
        String passwordHash = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        User newUser = new User(username,passwordHash);
        userDAO.save(newUser);
    }
    public String findPassword(String username){
       return userDAO.findPasswordByUsername(username);
    }


    public boolean userNameExits(String username){
        return userDAO.checkUsernameExists(username);
    }


    public int findId(String username){
        return userDAO.findUserIdByUsername(username);
    }


    public void saveUserIDandRole(int userId, String role){
        userRolesDAO.save(userId,role);
    }

    public Set<Role> findRolesbyUserId(int userId){
        return userRolesDAO.findRoleByUserID(userId);
    }

}
