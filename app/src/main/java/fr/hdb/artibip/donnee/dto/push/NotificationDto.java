package fr.hdb.artibip.donnee.dto.push;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDto {
    /*
      {
            "title":"D'CLIC ELEC",
            "message":"Votre demande n'a pa eu de réponse positive. Contactez notre conseiller : 0659842504",
            "idIntervention":166
       }
     */

    @JsonProperty(value ="idIntervention")
    private int idIntervention;

    @JsonProperty(value = "body")
    private String body;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "sound")
    private String sound;

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}
