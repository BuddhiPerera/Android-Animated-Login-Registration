package com.example.sample.model;

public class Results {
       String source_link;
       SourceData stone_details;

    public Results() {
    }

    public Results(String source_link, SourceData stone_details) {
        this.source_link = source_link;
        this.stone_details = stone_details;
    }

    @Override
    public String toString() {
        return "Results{" +
                "source_link='" + source_link + '\'' +
                ", stone_details=" + stone_details +
                '}';
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public SourceData getStone_details() {
        return stone_details;
    }

    public void setStone_details(SourceData stone_details) {
        this.stone_details = stone_details;
    }
}
