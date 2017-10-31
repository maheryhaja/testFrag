package fr.hdb.artibip.donnee.dto.ws.demandeclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterielsInterventionDto {

    @JsonProperty(value = "idMaterielIntervention")
    private int idMaterielIntervention ;

    @JsonProperty(value = "libelle")
    private String libelle;

    @JsonProperty(value = "coutHT")
    private int coutHT;

    public int getIdMaterielIntervention() {
        return idMaterielIntervention;
    }

    public void setIdMaterielIntervention(int idMaterielIntervention) {
        this.idMaterielIntervention = idMaterielIntervention;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCoutHT() {
        return coutHT;
    }

    public void setCoutHT(int coutHT) {
        this.coutHT = coutHT;
    }
}
