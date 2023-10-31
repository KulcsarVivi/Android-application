package com.example.playfulmath.model;

public class ProfileModel {
    private String userID, username, email;
    private int score;

    public ProfileModel(){}
    public ProfileModel(String userID, String username, String email, String password, int score) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.score = 0;
    }

    public ProfileModel(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
