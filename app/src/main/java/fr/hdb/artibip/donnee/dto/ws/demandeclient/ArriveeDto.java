package fr.hdb.artibip.donnee.dto.ws.demandeclient;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArriveeDto {
    @JsonProperty(value = "dateArrivee")
    public String dateArrivee;

    @JsonProperty(value = "heures")
    public String heures;

    @JsonProperty(value = "minutes")
    public String minutes;

    @JsonProperty(value = "technicien")
    public TechnicienDto technicien;

    public String getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public String getHeures() {
        return heures;
    }

    public void setHeures(String heures) {
        this.heures = heures;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public TechnicienDto getTechnicien() {
        return technicien;
    }

    public void setTechnicien(TechnicienDto technicien) {
        this.technicien = technicien;
    }
}
