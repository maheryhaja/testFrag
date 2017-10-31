package fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.intervention.Intervention;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeInterventionResponseDto {
    @JsonProperty(value="hasError")
    private boolean hasError;

    @JsonProperty(value="message")
    public String message;

    @JsonProperty(value="intervention")
    private Intervention intervention;

    public Intervention getIntervention() {
        return intervention;
    }

    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    public boolean isHasError() {
        return hasError;
    }
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }



}
