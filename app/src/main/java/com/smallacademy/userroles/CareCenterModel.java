package com.smallacademy.userroles;

import android.location.Address;

import java.io.Serializable;

public class CareCenterModel implements Serializable {
    private String id;
    private String CenterName;
    private String FirstName;
    private String LastName;
    private String Mobile;
    private String Email;
    private String Address;


    private CareCenterModel(){}

    CareCenterModel(String id, String CenterName, String FirstName, String LastName, String Mobile, String Email, String Address ){
        this.CenterName = CenterName;
        this.FirstName  = FirstName;
        this.LastName   = LastName;
        this.Mobile     = Mobile;
        this.Email      = Email;
        this.Address    = Address;
        this.id         = id;
    }


    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
