package com.example.playfulmath.model;

public class ProfileModel {
    private String userID, username, email;
    private int score;
    private boolean badge1, badge2, badge3, badge4, badge5, badge6;

    public ProfileModel() { }
    public ProfileModel(String username, int score) {
        this.username = username;
        this.score = score;
    }
    public ProfileModel(String userID, String username, String email) {
        this.userID = userID;
        this.username = username;
        this.email = email;
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

    public boolean isBadge1() {
        return badge1;
    }

    public void setBadge1(boolean badge1) {
        this.badge1 = badge1;
    }

    public boolean isBadge2() {
        return badge2;
    }

    public void setBadge2(boolean badge2) {
        this.badge2 = badge2;
    }

    public boolean isBadge3() {
        return badge3;
    }

    public void setBadge3(boolean badge3) {
        this.badge3 = badge3;
    }

    public boolean isBadge4() {
        return badge4;
    }

    public void setBadge4(boolean badge4) {
        this.badge4 = badge4;
    }

    public boolean isBadge5() {
        return badge5;
    }

    public void setBadge5(boolean badge5) {
        this.badge5 = badge5;
    }

    public boolean isBadge6() {
        return badge6;
    }

    public void setBadge6(boolean badge6) {
        this.badge6 = badge6;
    }
}
