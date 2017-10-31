package fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationInterventionResponseDto {

    @JsonProperty(value="hasError")
    private boolean hasError;

    @JsonProperty(value="result")
    private String result;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String isResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
