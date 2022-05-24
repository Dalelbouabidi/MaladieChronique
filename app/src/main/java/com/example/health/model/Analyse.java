package com.example.health.model;

public class Analyse {
    private String analyse;
    private String malade;

    public Analyse(){ }

    public Analyse(String analyse, String malade) {
        this.analyse = analyse;
        this.malade = malade;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public String getMalade() {
        return malade;
    }

    public void setMalade(String malade) {
        this.malade = malade;
    }
}
