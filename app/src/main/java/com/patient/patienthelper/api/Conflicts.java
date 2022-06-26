package com.patient.patienthelper.api;

import java.util.List;

public class Conflicts {
    private String drug;
    private List<String> conflicts_drugs;

    public Conflicts(String drug, List<String> conflicts_drugs) {
        drug = drug;
        this.conflicts_drugs = conflicts_drugs;
    }

    public Conflicts() {
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public List<String> getConflicts_drugs() {
        return conflicts_drugs;
    }

    public void setConflicts_drugs(List<String> conflicts_drugs) {
        this.conflicts_drugs = conflicts_drugs;
    }

    @Override
    public String toString() {
        return "Conflicts{" +
                "drug='" + drug + '\'' +
                ", conflicts_drugs=" + conflicts_drugs +
                '}';
    }


}
