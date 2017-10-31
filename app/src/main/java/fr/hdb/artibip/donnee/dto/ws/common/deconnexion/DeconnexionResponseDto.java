package fr.hdb.artibip.donnee.dto.ws.common.deconnexion;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeconnexionResponseDto {

    @JsonProperty(value="hasError")
    public boolean hasError;

    @JsonProperty(value="error")
    public String error;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DeconnexionResponseDto{" +
                "hasError=" + hasError +
                ", error='" + error + '\'' +
                '}';
    }
}
