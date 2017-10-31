package fr.hdb.artibip.donnee.dto.ws.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeplacementPostDto {
    @JsonProperty(value="origin")
    private String origin;

    @JsonProperty(value="idIntervention")
    private int idIntervention;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    @Override
    public String toString() {
        return "DeplacementPostDto{" +
                "origin='" + origin + '\'' +
                ", idIntervention=" + idIntervention +
                '}';
    }
}
