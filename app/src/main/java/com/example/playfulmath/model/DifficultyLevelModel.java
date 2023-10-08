package com.example.playfulmath.model;

public class DifficultyLevelModel {
    private String difficultyLevelID;
    private String difficultyLevelName;

    public DifficultyLevelModel() { }

    public DifficultyLevelModel(String difficultyLevelID, String difficultyLevelName) {
        this.difficultyLevelID = difficultyLevelID;
        this.difficultyLevelName = difficultyLevelName;
    }

    public String getDifficultyLevelID() {
        return difficultyLevelID;
    }

    public void setDifficultyLevelID(String difficultyLevelID) {
        this.difficultyLevelID = difficultyLevelID;
    }

    public String getDifficultyLevelName() {
        return difficultyLevelName;
    }

    public void setDifficultyLevelName(String difficultyLevelName) {
        this.difficultyLevelName = difficultyLevelName;
    }
}
