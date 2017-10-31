package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 06/07/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TauxHoraireDto {
    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "tauxHoraire")
    private int tauxHoraire;

    @JsonProperty(value = "tauxTVA")
    private int tauxTVA;

    @JsonProperty(value = "idPlageHoraire")
    private int idPlageHoraire;

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

    public int getIdPlageHoraire() {
        return idPlageHoraire;
    }

    public void setIdPlageHoraire(int idPlageHoraire) {
        this.idPlageHoraire = idPlageHoraire;
    }
}
