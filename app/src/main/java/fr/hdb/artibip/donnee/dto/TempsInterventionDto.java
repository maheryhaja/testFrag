package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * .
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TempsInterventionDto {

    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "libelle")
    private String libelle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
