package com.example.health.model;

public class RendezVous {
        private String rendezvous;
    private String malade;

    public RendezVous() {
    }

    public RendezVous(String rendezvous, String maladie) {
        this.rendezvous = rendezvous;
        this.malade = maladie;
    }

    public String getRendezvous() {
        return rendezvous;
    }

    public void setRendezvous(String rendezvous) {
        this.rendezvous = rendezvous;
    }

    public String getMalade() {
        return malade;
    }

    public void setMalade(String malade) {
        this.malade = malade;
    }
}



