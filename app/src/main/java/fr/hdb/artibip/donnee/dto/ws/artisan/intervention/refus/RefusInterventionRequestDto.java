package fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RefusInterventionRequestDto {

    @JsonProperty(value="employe")
    private int employe;

    @JsonProperty(value="intervention")
    private int intervention;


    public int getEmploye() {
        return employe;
    }

    public void setEmploye(int employe) {
        this.employe = employe;
    }

    public int getIntervention() {
        return intervention;
    }

    public void setIntervention(int intervention) {
        this.intervention = intervention;
    }

    @Override
    public String toString() {
        return "RefusInterventionRequestDto{" +
                "employe=" + employe +
                ", intervention=" + intervention +
                '}';
    }
}
