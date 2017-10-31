package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.DemandeArtisanDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtisanDemandeResponseDto {
    @JsonProperty(value = "hasError")
    private boolean hasError;

    @JsonProperty(value = "list_intervention")
    private List<DemandeArtisanDto> listDemande;

    @JsonProperty(value = "message")
    private String message;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<DemandeArtisanDto> getListDemande() {
        return listDemande;
    }

    public void setListDemande(List<DemandeArtisanDto> listDemande) {
        this.listDemande = listDemande;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
