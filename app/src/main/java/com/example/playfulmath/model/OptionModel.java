package com.example.playfulmath.model;

public class OptionModel {

    private int optionValue;
    private String optionImage;

    public OptionModel() {
    }

    public OptionModel(int optionValue, String optionImage) {
        this.optionValue = optionValue;
        this.optionImage = optionImage;
    }

    public int getOptionValue() {
        return optionValue;
    }

    public String getOptionImage() {
        return optionImage;
    }
}
