package com.smallacademy.userroles;

import java.io.Serializable;

public class ChannelDoctorModel implements Serializable {
    private String id;
    private String doctorname;
    private String docotrpet;
    private String doctorbreed;
    private String doctordescription;
    private String docotornopet;

    private ChannelDoctorModel(){}

    ChannelDoctorModel(String id, String doctorname, String docotrpet, String doctorbreed, String doctordescription, String docotornopet ){
        this.doctorname       = doctorname;
        this.docotrpet      = docotrpet;
        this.doctorbreed      = doctorbreed;
        this.doctordescription    = doctordescription;
        this.docotornopet       = docotornopet;
        this.id            = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDocotrpet() {
        return docotrpet;
    }

    public void setDocotrpet(String docotrpet) {
        this.docotrpet = docotrpet;
    }

    public String getDoctorbreed() {
        return doctorbreed;
    }

    public void setDoctorbreed(String doctorbreed) {
        this.doctorbreed = doctorbreed;
    }

    public String getDoctordescription() {
        return doctordescription;
    }

    public void setDoctordescription(String doctordescription) {
        this.doctordescription = doctordescription;
    }

    public String getDocotornopet() {
        return docotornopet;
    }

    public void setDocotornopet(String docotornopet) {
        this.docotornopet = docotornopet;
    }
}
