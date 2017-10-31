package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtablissementDto {

    @JsonProperty(value="id")
    private int id;

    @JsonProperty(value="nom_etablissement")
    private String nomEtablissement;

    @JsonProperty(value="numero_portable")
    private String numeroPorable;

    @JsonProperty(value = "nom_contact")
    private String nomContact;

    @JsonProperty(value="rue")
    private String rue;

    @JsonProperty(value="ville")
    private String ville;

    @JsonProperty(value="code_postal")
    private String codePostal;

    @JsonProperty(value = "pays")
    private String pays;

    private String adresse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomEtablissement() {
        if(nomEtablissement == null) return "";
        return nomEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public String getNumeroPorable() {
        return numeroPorable;
    }

    public void setNumeroPorable(String numeroPorable) {
        this.numeroPorable = numeroPorable;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getRue() {
        if(rue == null) return "";
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        if(ville == null) return "";
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        if(codePostal == null) return "";
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        if(pays == null) return "";
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return getRue()+" "+getCodePostal()+" "+getVille()+" "+getPays();
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "EtablissementDto{" +
                "id=" + id +
                ", nomEtablissement='" + nomEtablissement + '\'' +
                ", numeroPorable='" + numeroPorable + '\'' +
                ", nomContact='" + nomContact + '\'' +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", pays='" + pays + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
