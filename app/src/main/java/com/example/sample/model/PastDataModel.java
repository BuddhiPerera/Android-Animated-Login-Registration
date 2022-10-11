package com.example.sample.model;

public class PastDataModel {

    String name, type;

    public PastDataModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public PastDataModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
