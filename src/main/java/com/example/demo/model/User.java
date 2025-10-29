package com.example.demo.model;

public class User {

    private  Long id;
    private String firstName;

    public User(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
}
