package fr.hdb.artibip.donnee.dto.ws.post;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginOubliPostDto {
    @JsonProperty(value = "identifiant")
    private String identifiant;

    /*
    @JsonProperty(value = "role")
    private String role;
    */

    @JsonProperty(value = "type")
    private int type;

    public void setIdentifiant(String identifiant){
        this.identifiant=identifiant;
    }
    /*
    public void setRole(String role){
        this.role=role;
    }
    */
    public void setType(int type){
        this.type=type;
    }
}
