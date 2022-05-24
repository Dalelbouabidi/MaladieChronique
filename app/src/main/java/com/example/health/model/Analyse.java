package com.example.health.model;

public class Analyse {
    private String analyse;
    private String maladie;

    public Analyse(){ }

    public Analyse(String analyse, String maladie) {
        this.analyse = analyse;
        this.maladie = maladie;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }
}
