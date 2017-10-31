package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.hdb.artibip.donnee.dto.EtablissementLocationDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeplacementResponseDto {

    @JsonProperty(value="distance")
    private String distance;

    @JsonProperty(value="time")
    private  String time;

    @JsonProperty(value = "hasError")
    private boolean hasError;

    @JsonProperty(value="etablissement")
    private EtablissementLocationDto etablissement;

    @JsonProperty(value = "message")
    private String message;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public EtablissementLocationDto getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementLocationDto etablissement) {
        this.etablissement = etablissement;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
