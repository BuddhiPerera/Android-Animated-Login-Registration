package com.example.sample.model;

public class PastDataModel {

    String mineralType, roughRelativeDating,makingTechnique,isArtifact;

    public PastDataModel() {
    }

    public PastDataModel(String mineralType, String roughRelativeDating, String makingTechnique, String isArtifact) {
        this.mineralType = mineralType;
        this.roughRelativeDating = roughRelativeDating;
        this.makingTechnique = makingTechnique;
        this.isArtifact = isArtifact;
    }

    @Override
    public String toString() {
        return "PastDataModel{" +
                "mineralType='" + mineralType + '\'' +
                ", roughRelativeDating='" + roughRelativeDating + '\'' +
                ", makingTechnique='" + makingTechnique + '\'' +
                ", isArtifact='" + isArtifact + '\'' +
                '}';
    }

    public String getMineralType() {
        return mineralType;
    }

    public void setMineralType(String mineralType) {
        this.mineralType = mineralType;
    }

    public String getRoughRelativeDating() {
        return roughRelativeDating;
    }

    public void setRoughRelativeDating(String roughRelativeDating) {
        this.roughRelativeDating = roughRelativeDating;
    }

    public String getMakingTechnique() {
        return makingTechnique;
    }

    public void setMakingTechnique(String makingTechnique) {
        this.makingTechnique = makingTechnique;
    }

    public String getIsArtifact() {
        return isArtifact;
    }

    public void setIsArtifact(String isArtifact) {
        this.isArtifact = isArtifact;
    }
}
