package fr.hdb.artibip.donnee.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DemandeArtisanDto {
    @JsonProperty(value = "id_intervention")
    private int idDemande;

    @JsonProperty(value = "etat_intervention")
    private int status;

    @JsonProperty(value = "date_intervention")
    private String date;

    @JsonProperty(value = "duree_deplacement")
    private int dureeDeplacement;

    @JsonProperty(value = "userRefused")
    private boolean canModify;

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDureeDeplacement() {
        return dureeDeplacement;
    }

    public void setDureeDeplacement(int dureeDeplacement) {
        this.dureeDeplacement = dureeDeplacement;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }
}
