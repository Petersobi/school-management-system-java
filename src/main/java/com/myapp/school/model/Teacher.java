package com.myapp.school.model;

public class Teacher {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String subject;

    public Teacher(String firstName, String lastName, String subject){
        this.firstName = firstName;this.lastName = lastName; this.subject = subject;
    }
    public Teacher ( int userId ,String firstName,String lastName,String subject){
        this.userId = userId;  this.firstName = firstName;this.lastName = lastName; this.subject = subject;
    }
    public Teacher ( int id, int userId ,String firstName,String lastName,String subject){
       this.id = id; this.userId = id;  this.firstName = firstName;this.lastName = lastName; this.subject = subject;
    }

    public void setID(int id){this.id = id;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSubject(){return subject;}

    public int getUserId() {
        return userId;
    }
}
