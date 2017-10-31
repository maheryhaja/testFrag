package fr.hdb.artibip.donnee.dto.ws.demandeclient;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TechnicienDto {


    @JsonProperty(value = "nom")
    private String nom;

    @JsonProperty(value = "prenom")
    private String prenom;

    @JsonProperty(value = "numero_portable")
    private String portable;

    @JsonProperty(value = "mail")
    private String mail;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPortable() {
        return portable;
    }

    public void setPortable(String portable) {
        this.portable = portable;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
