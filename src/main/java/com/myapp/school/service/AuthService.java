package com.myapp.school.service;

import com.myapp.school.dao.UserDAO;
import com.myapp.school.model.Role;
import com.myapp.school.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();
    public boolean authenticate(String username, String password){
      return BCrypt.checkpw(password,userDAO.findPasswordByUsername(username));
    }
}
