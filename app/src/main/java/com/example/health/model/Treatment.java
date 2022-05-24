package com.example.health.model;

public class Treatment {
    private String medicName;
    private String medicQuantity;
    private String maladie;

    public Treatment() { }

    public Treatment(String medName, String medQuantity, String maladie) {
        medicName = medName;
        medicQuantity = medQuantity;
        this.maladie = maladie;
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

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }
}
