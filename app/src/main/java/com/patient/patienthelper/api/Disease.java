package com.patient.patienthelper.api;

import java.util.List;

public class Disease {
    private String disease_name;
    private String description_t;
    private List<String> drugs_names;

    public Disease(String disease_name, String description_t, List<String> drugs_names) {
        this.disease_name = disease_name;
        this.description_t = description_t;
        this.drugs_names = drugs_names;
    }

    public Disease() {
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDescription_t() {
        return description_t;
    }

    public void setDescription_t(String description_t) {
        this.description_t = description_t;
    }

    public List<String> getDrugs_names() {
        return drugs_names;
    }

    public void setDrugs_names(List<String> drugs_names) {
        this.drugs_names = drugs_names;
    }
}
