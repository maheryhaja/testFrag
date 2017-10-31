package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrgenceDto {

    @JsonProperty(value="id")
    private int id;

    @JsonProperty(value = "lebelle")
    private String lebelle;

    @JsonProperty(value = "url_photo")
    private String urlPhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLebelle() {
        return lebelle;
    }

    public void setLebelle(String lebelle) {
        this.lebelle = lebelle;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
