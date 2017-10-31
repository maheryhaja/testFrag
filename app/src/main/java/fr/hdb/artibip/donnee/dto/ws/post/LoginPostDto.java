package fr.hdb.artibip.donnee.dto.ws.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginPostDto {
    /*
    {
	"identifiant":"diahm.iza1+client@gmail.com",
	"pass":"6msh4jrt" ,
	"role" : "client"
}
     */
    @JsonProperty(value = "identifiant")
    private String identifiant;

    @JsonProperty(value = "pass")
    private String password;

    /*
    @JsonProperty(value = "role")
    private String role;*/

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginPostDto{" +
                "identifiant='" + identifiant + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


/*
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    */


}
