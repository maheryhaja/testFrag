package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import fr.hdb.artibip.donnee.dto.EtablissementDto;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListEtablissementResponseDto {
    @JsonProperty(value="hasError")
    private boolean hasError;

    @JsonProperty(value="list_etablissement")
    private List<EtablissementDto> etablissements;

    @JsonProperty(value="id_client")
    private int idClient;

    @JsonProperty(value="error")
    private String error;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<EtablissementDto> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(List<EtablissementDto> etablissements) {
        this.etablissements = etablissements;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "etablissements=" + etablissements.size() ;
    }
}
