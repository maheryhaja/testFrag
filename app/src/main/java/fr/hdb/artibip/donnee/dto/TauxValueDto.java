package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TauxValueDto {

    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "tauxHoraire")
    private int tauxHoraire;

    @JsonProperty(value = "tauxTVA")
    private int tauxTVA;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(int tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public int getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(int tauxTVA) {
        this.tauxTVA = tauxTVA;
    }
}
