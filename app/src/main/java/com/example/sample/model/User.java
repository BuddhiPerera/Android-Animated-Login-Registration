package com.example.sample.model;

public class User {
    public String name;
    public String email;
    public String mobileNumber;


    public User() {
    }

    public User(String name, String mobileNumber, String email) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }
}
