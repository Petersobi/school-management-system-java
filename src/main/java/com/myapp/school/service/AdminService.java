package com.myapp.school.service;

import com.myapp.school.dao.AdminDAO;
import com.myapp.school.model.Admin;

public class AdminService {
    private final AdminDAO adminDAO = new AdminDAO();
    public void save(Admin admin){
        adminDAO.save(admin);
    }
    public boolean checkIfUserIdExists(int userID){
        return adminDAO.checkUserIDExists(userID);
    }
}
