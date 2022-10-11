package com.example.sample.model;


public class SourceFile {
    String name;
    String source_link;
    String category;
    String _id;
    String date;

    public SourceFile() {
    }

    public SourceFile(String name, String source_link, String category, String _id, String date) {
        this.name = name;
        this.source_link = source_link;
        this.category = category;
        this._id = _id;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sourcefile{" +
                "name='" + name + '\'' +
                ", source_link='" + source_link + '\'' +
                ", category='" + category + '\'' +
                ", _id='" + _id + '\'' +
                ", date=" + date +
                '}';
    }
}
