package com.atomasg.azaol.data;

import java.io.Serializable;

public class Register implements Serializable {

    private String street;
    private String num;
    private String owner;
    private String meterBefore;
    private String meter;
    private String date;
    private String observations;
    private String url = "";

    public Register() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMeterBefore() {
        return meterBefore;
    }

    public void setMeterBefore(String meterBefore) {
        this.meterBefore = meterBefore;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
