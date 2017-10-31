package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginOubliResponseDto {
    @JsonProperty(value = "hasError")
    private boolean hasError;

    @JsonProperty(value = "message")
    private String message;

    public boolean isHasError(){
        return this.hasError;
    }
    public String getMessage(){
        return this.message;
    }
}
