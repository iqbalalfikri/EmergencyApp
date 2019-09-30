package com.example.emergencyapp;

public class UserProfile {
    public String userFullName;
    public String userEmail;
    public String userPhone;
    public String emergencyNumber;

    public UserProfile(String userFullName, String userEmail, String userPhone, String emergencyNumber) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        if(emergencyNumber == null){
            emergencyNumber = "";
        }
        else
        {
            this.emergencyNumber = emergencyNumber;
        }

    }
}
