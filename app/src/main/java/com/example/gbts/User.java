package com.example.gbts;


public class User {

   private String LOGIN_ID,EMAIL_ID,PASSWORD,PHONE_NO,ROLE,STATUS,DP,NAME,ADDRESS,EMERGENCY_CONTACT,CITY_ID,STATE_ID,FNAME,FID;

    public User() {
    }

    public User(String LOGIN_ID, String EMAIL_ID, String PASSWORD, String PHONE_NO, String ROLE, String STATUS, String DP, String NAME, String ADDRESS, String EMERGENCY_CONTACT, String CITY_ID, String STATE_ID, String FNAME, String FID) {
        this.LOGIN_ID = LOGIN_ID;
        this.EMAIL_ID = EMAIL_ID;
        this.PASSWORD = PASSWORD;
        this.PHONE_NO = PHONE_NO;
        this.ROLE = ROLE;
        this.STATUS = STATUS;
        this.DP = DP;
        this.NAME = NAME;
        this.ADDRESS = ADDRESS;
        this.EMERGENCY_CONTACT = EMERGENCY_CONTACT;
        this.CITY_ID = CITY_ID;
        this.STATE_ID = STATE_ID;
        this.FNAME = FNAME;
        this.FID = FID;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }

    public String getLOGIN_ID() {
        return LOGIN_ID;
    }

    public void setLOGIN_ID(String LOGIN_ID) {
        this.LOGIN_ID = LOGIN_ID;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDP() {
        return DP;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMERGENCY_CONTACT() {
        return EMERGENCY_CONTACT;
    }

    public void setEMERGENCY_CONTACT(String EMERGENCY_CONTACT) {
        this.EMERGENCY_CONTACT = EMERGENCY_CONTACT;
    }

    public String getCITY_ID() {
        return CITY_ID;
    }

    public void setCITY_ID(String CITY_ID) {
        this.CITY_ID = CITY_ID;
    }

    public String getSTATE_ID() {
        return STATE_ID;
    }

    public void setSTATE_ID(String STATE_ID) {
        this.STATE_ID = STATE_ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
}
