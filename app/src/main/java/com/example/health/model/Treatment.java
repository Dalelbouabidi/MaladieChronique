package com.example.health.model;

public class Treatment {
    private String medicName;
    private String medicQuantity;

    public Treatment() { }

    public Treatment(String medName, String medQuantity) {
        medicName = medName;
        medicQuantity = medQuantity;
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
}
