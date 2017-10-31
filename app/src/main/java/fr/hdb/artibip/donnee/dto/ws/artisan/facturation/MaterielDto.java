package fr.hdb.artibip.donnee.dto.ws.artisan.facturation;


import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterielDto {

    @JsonProperty(value="nomMateriel")
    private String nomMateriel;

    @JsonProperty(value="prixMaterielHt")
    private double prixMaterielHt;

    public String getNomMateriel() {
        return nomMateriel;
    }

    public void setNomMateriel(String nomMateriel) {
        this.nomMateriel = nomMateriel;
    }

    public double getPrixMaterielHt() {
        return prixMaterielHt;
    }

    public void setPrixMaterielHt(double prixMaterielHt) {
        this.prixMaterielHt = prixMaterielHt;
    }
}
