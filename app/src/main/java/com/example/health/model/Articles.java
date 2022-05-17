package com.example.health.model;

public class Articles {
   private String titre,description;
    public Articles(){}
    public Articles(String titre,String description){
        this.titre = titre;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
