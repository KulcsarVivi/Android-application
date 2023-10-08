package com.example.playfulmath.model;

public class ProfileModel {
    private String userID, userName, email;
    private int score;

    public ProfileModel(){}
    public ProfileModel(String userID, String userName, String email, String password, int score) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.score = 0;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
