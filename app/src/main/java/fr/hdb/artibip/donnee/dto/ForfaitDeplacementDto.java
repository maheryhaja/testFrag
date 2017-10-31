package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * .
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForfaitDeplacementDto {

    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "designation")
    private String designation;

    @JsonProperty(value = "taux")
    private int taux;

    @JsonProperty(value = "minutes")
    private int minute;

    @JsonProperty(value = "id_plage_horaire")
    private int idPlageHoraire;

    private String dureeIntervetion;

    private int minuteDureeIntervention;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getTaux() {
        return taux;
    }

    public void setTaux(int taux) {
        this.taux = taux;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getIdPlageHoraire() {
        return idPlageHoraire;
    }

    public void setIdPlageHoraire(int idPlageHoraire) {
        this.idPlageHoraire = idPlageHoraire;
    }

    public String getDureeIntervetion() {
        return dureeIntervetion;
    }

    public void setDureeIntervetion(String dureeIntervetion) {
        this.dureeIntervetion = dureeIntervetion;
    }

    public int getMinuteDureeIntervention() {
        return minuteDureeIntervention;
    }

    public void setMinuteDureeIntervention(int minuteDureeIntervention) {
        this.minuteDureeIntervention = minuteDureeIntervention;
    }
}
