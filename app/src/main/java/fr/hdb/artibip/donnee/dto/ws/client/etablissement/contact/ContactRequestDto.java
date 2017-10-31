package fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactRequestDto {


    @JsonProperty(value="id_etablissement")
    private int idEtablissment;

    @JsonProperty(value="numero_contact")
    private String number;

    @JsonProperty(value="nom_contact")
    private String name;


    public int getIdEtablissment() {
        return idEtablissment;
    }

    public void setIdEtablissment(int idEtablissment) {
        this.idEtablissment = idEtablissment;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactRequestDto{" +
                "idEtablissment=" + idEtablissment +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
