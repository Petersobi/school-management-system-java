package com.myapp.school.model;

public class Admin {
    private int id;
    private int userId;
    private String firstname;
    private String lastname;

    public Admin(String firstname,String lastname){
        this.firstname = firstname; this.lastname = lastname;
    }
    public Admin(int userId,String firstname,String lastname){
      this.userId = userId;  this.firstname = firstname; this.lastname = lastname;
    }
    public Admin(int id,int userId,String firstname,String lastname){
       this.id = id; this.userId = userId;  this.firstname = firstname; this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

