package com.example.playfulmath.model;

import java.util.List;

public class GameModel {

    private int question1;
    private String operator;
    private int question2;
    private int option1;
    private int option2;
    private int option3;
    private int option4;

    public int getQuestion1() {
        return question1;
    }

    public void setQuestion1(int question1) {
        this.question1 = question1;
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

    public int getOption1() {
        return option1;
    }

    public void setOption1(int option1) {
        this.option1 = option1;
    }

    public int getOption2() {
        return option2;
    }

    public void setOption2(int option2) {
        this.option2 = option2;
    }

    public int getOption3() {
        return option3;
    }

    public void setOption3(int option3) {
        this.option3 = option3;
    }

    public int getOption4() {
        return option4;
    }

    public void setOption4(int option4) {
        this.option4 = option4;
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
