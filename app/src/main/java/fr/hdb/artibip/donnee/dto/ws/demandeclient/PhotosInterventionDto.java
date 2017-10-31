package fr.hdb.artibip.donnee.dto.ws.demandeclient;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotosInterventionDto {

    @JsonProperty(value = "id_photo_intervention")
    public int idPhotoIntervention ;

    @JsonProperty(value = "chemin_photo")
    public String cheminPhoto;

}
