package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 *.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseDto {
    @JsonProperty(value = "hasError")
    private boolean hasError;

    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "id")
    private int idClient;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "type_user")
    private int typeUser;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }
}
