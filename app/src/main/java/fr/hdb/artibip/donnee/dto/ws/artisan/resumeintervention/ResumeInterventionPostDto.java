package fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeInterventionPostDto {
    @JsonProperty(value="idIntervention")
    private int idIntervention;

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }
}
