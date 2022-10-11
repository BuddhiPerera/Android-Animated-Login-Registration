package com.example.sample.model;

public class PastDataModel {

    String name, category;
    public PastDataModel(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public PastDataModel() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
