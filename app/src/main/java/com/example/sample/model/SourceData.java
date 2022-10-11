package com.example.sample.model;

public class SourceData {

    private SourceFile sourcefile;

    public SourceData(SourceFile sourcefile) {
        this.sourcefile = sourcefile;
    }

    public SourceFile getSourcefile() {
        return sourcefile;
    }

    public void setSourcefile(SourceFile sourcefile) {
        this.sourcefile = sourcefile;
    }
}
