package com.example.sample.model;

import java.util.ArrayList;

public class SourceData {
     boolean isArtifact;
     String mineralType;
     String makingTechnique;
     String roughRelativeDating;
     String functionalDescription;

    public SourceData() {
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "isArtifact=" + isArtifact +
                ", mineralType='" + mineralType + '\'' +
                ", makingTechnique='" + makingTechnique + '\'' +
                ", roughRelativeDating='" + roughRelativeDating + '\'' +
                ", functionalDescription='" + functionalDescription + '\'' +
                '}';
    }

    public boolean isArtifact() {
        return isArtifact;
    }

    public void setArtifact(boolean artifact) {
        isArtifact = artifact;
    }

    public String getMineralType() {
        return mineralType;
    }

    public void setMineralType(String mineralType) {
        this.mineralType = mineralType;
    }

    public String getMakingTechnique() {
        return makingTechnique;
    }

    public void setMakingTechnique(String makingTechnique) {
        this.makingTechnique = makingTechnique;
    }

    public String getRoughRelativeDating() {
        return roughRelativeDating;
    }

    public void setRoughRelativeDating(String roughRelativeDating) {
        this.roughRelativeDating = roughRelativeDating;
    }

    public String getFunctionalDescription() {
        return functionalDescription;
    }

    public void setFunctionalDescription(String functionalDescription) {
        this.functionalDescription = functionalDescription;
    }

    public SourceData(boolean isArtifact, String mineralType, String makingTechnique, String roughRelativeDating, String functionalDescription) {
        this.isArtifact = isArtifact;
        this.mineralType = mineralType;
        this.makingTechnique = makingTechnique;
        this.roughRelativeDating = roughRelativeDating;
        this.functionalDescription = functionalDescription;
    }
}
