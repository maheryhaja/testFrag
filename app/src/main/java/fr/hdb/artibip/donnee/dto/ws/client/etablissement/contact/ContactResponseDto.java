package fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactResponseDto {

    @JsonProperty(value="hasError")
    public boolean hasError;

    @JsonProperty(value="message")
    public String message;

    public boolean getHasError() {
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
        return "ContactResponseDto{" +
                "hasError=" + hasError +
                ", message='" + message + '\'' +
                '}';
    }
}
