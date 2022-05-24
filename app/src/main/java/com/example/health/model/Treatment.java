package com.example.health.model;

public class Treatment {
    private String medicName;
    private String medicQuantity;
    private String malade;

    public Treatment() { }

    public Treatment(String medName, String medQuantity, String malade) {
        medicName = medName;
        medicQuantity = medQuantity;
        this.malade = malade;
    }

    public String getMedicName() {
        return medicName;
    }

    public void setMedicName(String medicName) {
        this.medicName = medicName;
    }

    public String getMedicQuantity() {
        return medicQuantity;
    }

    public void setMedicQuantity(String medicQuantity) {
        this.medicQuantity = medicQuantity;
    }

    public String getMalade() {
        return malade;
    }

    public void setMalade(String maladie) {
        this.malade = maladie;
    }
}
