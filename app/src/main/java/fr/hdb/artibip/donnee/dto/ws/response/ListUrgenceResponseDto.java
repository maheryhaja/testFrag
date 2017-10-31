package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.UrgenceDto;

/**
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListUrgenceResponseDto {
    @JsonProperty(value = "hasError")
    private boolean hasError;

    @JsonProperty(value = "listTypeUrgence")
    private List<UrgenceDto> Urgences;

    @JsonProperty(value="message")
    private String message;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<UrgenceDto> getUrgences() {
        return Urgences;
    }

    public void setUrgences(List<UrgenceDto> urgences) {
        Urgences = urgences;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
