package com.example.gbts;

class ExampleModel {


    String sname , cname;
    String LOCATION_NAME,LATITUDE,LONGITUDE,POLICE_STATION,FIRE_STATION,HOSPITAL;

    public ExampleModel(String LOCATION_NAME, String LATITUDE, String LONGITUDE, String POLICE_STATION, String FIRE_STATION, String HOSPITAL) {
        this.LOCATION_NAME = LOCATION_NAME;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.POLICE_STATION = POLICE_STATION;
        this.FIRE_STATION = FIRE_STATION;
        this.HOSPITAL = HOSPITAL;
    }




    public ExampleModel() {
    }

    public ExampleModel(String sname) {
        this.sname = sname;
    }

    public ExampleModel(String sname, String cname) {
        this.sname = sname;
        this.cname = cname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public void setLOCATION_NAME(String LOCATION_NAME) {
        this.LOCATION_NAME = LOCATION_NAME;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getPOLICE_STATION() {
        return POLICE_STATION;
    }

    public void setPOLICE_STATION(String POLICE_STATION) {
        this.POLICE_STATION = POLICE_STATION;
    }

    public String getFIRE_STATION() {
        return FIRE_STATION;
    }

    public void setFIRE_STATION(String FIRE_STATION) {
        this.FIRE_STATION = FIRE_STATION;
    }

    public String getHOSPITAL() {
        return HOSPITAL;
    }

    public void setHOSPITAL(String HOSPITAL) {
        this.HOSPITAL = HOSPITAL;
    }
}

