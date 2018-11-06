package com.atomasg.azaol.data;

import java.io.Serializable;

public class Register implements Serializable {

    private String idContador = String.format("%d", (int) (Math.random() * ((100000 - 0) + 1)));
    private String street;
    private String num;
    private String owner;
    private String valueBefore;
    private String value;
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

    public String getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(String valueBefore) {
        this.valueBefore = valueBefore;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getIdContador() {
        return idContador;
    }

    public void setIdContador(String idContador) {
        this.idContador = idContador;
    }
}
