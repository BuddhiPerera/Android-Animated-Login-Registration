package com.example.sample.model;

import java.util.ArrayList;

public class SourceData {

    String sourcelink;
    boolean isartifact;
    String mineraltype;
    String makingtechnique;
    String roughrelativedating;
    String functionaldescription;

    public SourceData() {
    }

    public SourceData(String sourcelink, boolean isartifact, String mineraltype, String makingtechnique, String roughrelativedating, String functionaldescription) {
        this.sourcelink = sourcelink;
        this.isartifact = isartifact;
        this.mineraltype = mineraltype;
        this.makingtechnique = makingtechnique;
        this.roughrelativedating = roughrelativedating;
        this.functionaldescription = functionaldescription;
    }

    public String getSourcelink() {
        return sourcelink;
    }

    public void setSourcelink(String sourcelink) {
        this.sourcelink = sourcelink;
    }

    public boolean isIsartifact() {
        return isartifact;
    }

    public void setIsartifact(boolean isartifact) {
        this.isartifact = isartifact;
    }

    public String getMineraltype() {
        return mineraltype;
    }

    public void setMineraltype(String mineraltype) {
        this.mineraltype = mineraltype;
    }

    public String getMakingtechnique() {
        return makingtechnique;
    }

    public void setMakingtechnique(String makingtechnique) {
        this.makingtechnique = makingtechnique;
    }

    public String getRoughrelativedating() {
        return roughrelativedating;
    }

    public void setRoughrelativedating(String roughrelativedating) {
        this.roughrelativedating = roughrelativedating;
    }

    public String getFunctionaldescription() {
        return functionaldescription;
    }

    public void setFunctionaldescription(String functionaldescription) {
        this.functionaldescription = functionaldescription;
    }
}
