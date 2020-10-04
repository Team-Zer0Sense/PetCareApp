package com.smallacademy.userroles;

import android.widget.EditText;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class PetModel implements Serializable {

    private  String id;

    private String name;
    private String pet;
    private String breed;
    private String appearance;


    private PetModel(){}

    PetModel(String id, String name, String pet, String breed, String appearance){
        this.name       = name;
        this.pet        = pet;
        this.breed      = breed;
        this.appearance = appearance;
        this.id = id;
    }

    public PetModel(EditText name, EditText pet, EditText breed, EditText appearance) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }
}
