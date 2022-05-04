package com.example.health.model;

public class TypeMalady {

    private String nameMalady;
    private String nameDoctor;

    public TypeMalady() {
    }

    public TypeMalady(String nameMalady, String nameDoctor) {
        this.nameMalady = nameMalady;
        this.nameDoctor = nameDoctor;
    }

    public String getNameMalady() {
        return nameMalady;
    }

    public void setNameMalady(String nameMalady) {
        this.nameMalady = nameMalady;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }
}
