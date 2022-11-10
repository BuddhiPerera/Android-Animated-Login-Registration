package com.example.sample.model;


public class SourceFile {


    String isArtifact;
String mineralType;
String roughRelativeDating;
String makingTechnique;
String functionalDescription;

    public SourceFile() {
    }

    public SourceFile(String isArtifact, String mineralType, String roughRelative, String makingTechnique, String functionalDescription) {
        this.isArtifact = isArtifact;
        this.mineralType = mineralType;
        this.roughRelativeDating = roughRelative;
        this.makingTechnique = makingTechnique;

        this.functionalDescription = functionalDescription;
    }

    @Override
    public String toString() {
        return "SourceFile{" +
                "isArtifact='" + isArtifact + '\'' +
                ", mineralType='" + mineralType + '\'' +
                ", roughRelativeDating='" + roughRelativeDating + '\'' +
                ", makingTechnique='" + makingTechnique + '\'' +
                ", functionalDescription='" + functionalDescription + '\'' +
                '}';
    }

    public String getIsArtifact() {
        return isArtifact;
    }

    public void setIsArtifact(String isArtifact) {
        this.isArtifact = isArtifact;
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


    public String getFunctionalDescription() {
        return functionalDescription;
    }

    public void setFunctionalDescription(String functionalDescription) {
        this.functionalDescription = functionalDescription;
    }
}
