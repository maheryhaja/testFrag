package fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EtablissementInfoDto {
    @JsonProperty(value="nom")
    private String nom;
    @JsonProperty(value="rue")
    private String rue;
    @JsonProperty(value="numero")
    private String numero;

    @JsonProperty(value="ville")
    private String ville;
    @JsonProperty(value="code_postal")
    private String codePostal;
    @JsonProperty(value="pays")
    private String pays;

    @JsonProperty(value="nom_contact")
    private String nomContact;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Object getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    @Override
    public String toString() {
        return "EtablissementInfoDto{" +
                "nom='" + nom + '\'' +
                ", rue='" + rue + '\'' +
                ", numero='" + numero + '\'' +
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", pays='" + pays + '\'' +
                ", nomContact='" + nomContact + '\'' +
                '}';
    }
}
