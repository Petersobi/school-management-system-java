package com.myapp.school.session;

import com.myapp.school.model.Role;
import com.myapp.school.model.User;

public class Session {
    private static User currentUser;
    public static void setCurrentUser(User user){
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public static int getCurrentUserId(){
        return currentUser.getId();
    }
    public static boolean currentUserhasRole(Role role){return currentUser.hasRole(role);}
    public static void clearUser(){
        currentUser =null;
    }
}
