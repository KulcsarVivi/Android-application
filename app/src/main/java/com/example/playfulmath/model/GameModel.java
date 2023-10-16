package com.example.playfulmath.model;

import java.util.List;

public class GameModel {

    private int question1;
    private String question1Image;
    private String operator;
    private int question2;
    private String question2Image;
    private List<OptionModel> options;

    public int getQuestion1() {
        return question1;
    }

    public void setQuestion1(int question1) {
        this.question1 = question1;
    }

    public String getQuestion1Image() {
        return question1Image;
    }

    public void setQuestion1Image(String question1Image) {
        this.question1Image = question1Image;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getQuestion2() {
        return question2;
    }

    public void setQuestion2(int question2) {
        this.question2 = question2;
    }

    public String getQuestion2Image() {
        return question2Image;
    }

    public void setQuestion2Image(String question2Image) {
        this.question2Image = question2Image;
    }

    public List<OptionModel> getOptions() {
        return options;
    }

    public void setOptions(List<OptionModel> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        if ("+".equals(operator)) {
            return question1 + question2;
        } else if ("-".equals(operator)) {
            return question1 - question2;
        }
        return 0;
    }
}
