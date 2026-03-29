package com.myapp.school.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    int id;
    String username;
    String password;
    Set<Role> role = new HashSet<>();

    public User(int id,String username,String password){
        this.id = id; this.username = username; this.password = password; this.role = role;
    }
    public User(String username,String password){
        this.id = id; this.username = username; this.password = password; this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public Boolean hasRole(Role role) {
        return this.role.contains(role);
    }

    public int getId() {
        return id;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addRole(Set<Role> role) {
        this.role.addAll(role);
    }
    public void setRole(Role role) {
        this.role.add(role);
    }


    public void setUsername(String username) {
        this.username = username;
    }



}
