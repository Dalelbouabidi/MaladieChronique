package com.example.health.model;

public class RendezVous {
        private String rendezvous;
    private String maladie;

    public RendezVous() {
    }

    public RendezVous(String rendezvous, String maladie) {
        this.rendezvous = rendezvous;
        this.maladie = maladie;
    }

    public String getRendezvous() {
        return rendezvous;
    }

    public void setRendezvous(String rendezvous) {
        this.rendezvous = rendezvous;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }
}



