package com.patient.patienthelper.api;

public class DrugDetails {
    private String drug_Name;
    private String drug_description;

    public DrugDetails(String drug_Name, String drugDescription) {
        this.drug_Name = drug_Name;
        this.drug_description = drugDescription;
    }

    public DrugDetails() {
    }

    public String getDrug_Name() {
        return drug_Name;
    }

    public void setDrug_Name(String drug_Name) {
        this.drug_Name = drug_Name;
    }

    public String getDrug_description() {
        return drug_description;
    }

    public void setDrug_description(String drug_description) {
        this.drug_description = drug_description;
    }
}
