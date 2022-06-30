package com.example.covid19.model;

public class Step {
    private int imgStep;
    private String tvTitle;

    public Step(int imgStep, String tvTitle) {
        this.imgStep = imgStep;
        this.tvTitle = tvTitle;
    }

    public int getImgStep() {
        return imgStep;
    }

    public void setImgStep(int imgStep) {
        this.imgStep = imgStep;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }
}
