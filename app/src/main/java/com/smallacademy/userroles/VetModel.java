package com.smallacademy.userroles;

import java.io.Serializable;

public class VetModel implements Serializable {
    private  String id;

    private String VetName;
    private String VetPhone;
    private String VetEmail;
    private String VetAddress;

    private VetModel(){}

    VetModel(String id, String VetName, String VetPhone, String VetEmail, String VetAddress){
        this.VetName       = VetName;
        this.VetPhone      = VetPhone;
        this.VetEmail      = VetEmail;
        this.VetAddress    = VetAddress;
        this.id            = id;
    }

    public String getVetName() {
        return VetName;
    }

    public void setVetName(String vetName) {
        VetName = vetName;
    }

    public String getVetPhone() {
        return VetPhone;
    }

    public void setVetPhone(String vetPhone) {
        VetPhone = vetPhone;
    }

    public String getVetEmail() {
        return VetEmail;
    }

    public void setVetEmail(String vetEmail) {
        VetEmail = vetEmail;
    }

    public String getVetAddress() {
        return VetAddress;
    }

    public void setVetAddress(String vetAddress) {
        VetAddress = vetAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
