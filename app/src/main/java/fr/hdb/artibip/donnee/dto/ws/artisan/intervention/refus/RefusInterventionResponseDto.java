package fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefusInterventionResponseDto {


    @JsonProperty(value="hasError")
    public boolean hasError;

    @JsonProperty(value="message")
    public String message;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RefusInterventionResponseDto{" +
                "hasError=" + hasError +
                ", message='" + message + '\'' +
                '}';
    }
}
