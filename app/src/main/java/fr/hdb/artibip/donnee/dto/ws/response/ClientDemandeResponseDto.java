package fr.hdb.artibip.donnee.dto.ws.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.DemandeClientDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDemandeResponseDto {
    @JsonProperty(value = "hasError")
    private boolean hasError;

    @JsonProperty(value = "list_intervention")
    private List<DemandeClientDto> listDemande;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "images_encours")
    private String imageEncours;

    @JsonProperty(value = "images_refuse")
    private String imageRefuse;

    @JsonProperty(value = "id_client")
    private int idClient;

    @JsonProperty(value = "numero_conseiller")
    private String numeroConseiller;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<DemandeClientDto> getListDemande() {
        return listDemande;
    }

    public void setListDemande(List<DemandeClientDto> listDemande) {
        this.listDemande = listDemande;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageEncours() {
        return imageEncours;
    }

    public void setImageEncours(String imageEncours) {
        this.imageEncours = imageEncours;
    }

    public String getImageRefuse() {
        return imageRefuse;
    }

    public void setImageRefuse(String imageRefuse) {
        this.imageRefuse = imageRefuse;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNumeroConseiller() {
        return numeroConseiller;
    }

    public void setNumeroConseiller(String numeroConseiller) {
        this.numeroConseiller = numeroConseiller;
    }

}
