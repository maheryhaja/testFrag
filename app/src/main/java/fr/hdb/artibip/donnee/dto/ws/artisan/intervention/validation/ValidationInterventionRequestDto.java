package fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationInterventionRequestDto {

    @JsonProperty(value="idIntervention")
    private int idIntervention;

    @JsonProperty(value="heures")
    private int heures;

    @JsonProperty(value="minutes")
    private int minutes;

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    public int getHeures() {
        return heures;
    }

    public void setHeures(int heures) {
        this.heures = heures;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "ValidationInterventionRequestDto{" +
                "idIntervention=" + idIntervention +
                ", heures=" + heures +
                ", minutes=" + minutes +
                '}';
    }
}
