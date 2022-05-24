package com.example.health.model;

public class UserModel {
    private String userId, nomdutilisateur, datedenaissance, telephone, email, motdepasse, usertype;

    public UserModel() {

    }

    public UserModel(String nomdutilisateur, String datedenaissance, String telephone, String email,
                     String motdepasse, String usertype) {
        this.nomdutilisateur = nomdutilisateur;
        this.datedenaissance = datedenaissance;
        this.telephone = telephone;
        this.usertype = usertype;
        this.email = email;
        this.motdepasse = motdepasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        return userId.equals(userModel.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNomdutilisateur() {
        return nomdutilisateur;
    }

    public void setNomdutilisateur(String nomdutilisateur) {
        this.nomdutilisateur = nomdutilisateur;
    }

    public String getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(String datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}