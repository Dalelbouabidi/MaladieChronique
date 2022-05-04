package com.example.health.model;

public class UserModel {
    private String  userId, nomdutilisateur,datedenaissance,telephone ,email,motdepasse ,sexe;

    public UserModel() {

    }

    public UserModel(String userId, String nomdutilisateur ,String datedenaissance , String telephone, String email,
                     String motdepasse, String sexe){
        this.userId = userId;
        this.nomdutilisateur=nomdutilisateur;
        this.datedenaissance=datedenaissance;
        this.telephone=telephone;
        this.email=email;
        this.motdepasse=motdepasse;
        this.sexe =sexe;
    }
    public UserModel(String nomdutilisateur ,String datedenaissance , String telephone, String email,
                     String motdepasse ,String sexe){
        this.nomdutilisateur=nomdutilisateur;
        this.datedenaissance=datedenaissance;
        this.telephone=telephone;
        this.email=email;
        this.motdepasse=motdepasse;
        this.sexe=sexe;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getNomdutilisateur(){
        return nomdutilisateur;
    }
    public void setNomdutilisateur(String nomdutilisateur){
        this.nomdutilisateur=nomdutilisateur;
    }
    public String getDatedenaissance(){
        return datedenaissance;
    }
    public void setDatedenaissance(String datedenaissance){
        this.datedenaissance=datedenaissance;
    }
    public String getTelephone(){
        return telephone;
    }
    public void setTelephone(String telephone){
        this.telephone=telephone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMotdepasse(){
        return motdepasse;
    }
    public void setMotdepasse(String motdepasse){
        this.motdepasse=motdepasse;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        sexe = sexe;
    }
}

