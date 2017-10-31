package fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.intervention;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterielResumeDto {

    @JsonProperty(value="idMaterielIntervention")
    private int idMaterielIntervention ;

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


    public double getCoutHT() {
        return coutHT;
    }

    public void setCoutHT(double coutHT) {
        this.coutHT = coutHT;
    }

    @JsonProperty(value="libelle")


    private String libelle;

    @JsonProperty(value="coutHT")
    private double coutHT;
}
