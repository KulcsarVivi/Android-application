package com.example.playfulmath.model;

public class ProfileModel {
    private String username, email, password;
    private int score;

    public ProfileModel(){}
    public ProfileModel(String username, String email, String password, int score) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = score;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
