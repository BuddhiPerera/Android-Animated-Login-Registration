package com.example.sample.model;


public class SourceFile {


    boolean isArtifact;
String mineralType;
String roughRelativeDating;
String makingTechnique;
String functionalDescription;
String link;

    public SourceFile() {
    }

    public SourceFile(boolean isArtifact, String mineralType, String roughRelativeDating, String makingTechnique, String functionalDescription, String link) {
        this.isArtifact = isArtifact;
        this.mineralType = mineralType;
        this.roughRelativeDating = roughRelativeDating;
        this.makingTechnique = makingTechnique;
        this.functionalDescription = functionalDescription;
        this.link = link;
    }

    public SourceFile(boolean isArtifact, String mineralType, String roughRelativeDating, String makingTechnique, String functionalDescription) {
        this.isArtifact = isArtifact;
        this.mineralType = mineralType;
        this.roughRelativeDating = roughRelativeDating;
        this.makingTechnique = makingTechnique;
        this.functionalDescription = functionalDescription;
    }

    @Override
    public String toString() {
        return "SourceFile{" +
                "isArtifact=" + isArtifact +
                ", mineralType='" + mineralType + '\'' +
                ", roughRelativeDating='" + roughRelativeDating + '\'' +
                ", makingTechnique='" + makingTechnique + '\'' +
                ", functionalDescription='" + functionalDescription + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
