package com.example.emergencyapp;

public class Feedback {
    public String userId;
    public String userName;
    public String message;

    public Feedback(String userId, String userName, String message) {
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }
}
