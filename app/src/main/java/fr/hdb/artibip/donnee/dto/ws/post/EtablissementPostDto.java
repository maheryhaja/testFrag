package fr.hdb.artibip.donnee.dto.ws.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtablissementPostDto {

    @JsonProperty(value="etablissement")
    private String etablissement;

    @JsonProperty(value = "nom_personne")
    private String nomPersonne;

    @JsonProperty(value = "numero_telephone")
    private String numeroTelephone;

    @JsonProperty(value="id_client")
    private int idClient;


    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
