package fr.hdb.artibip.donnee.dto.ws.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * .
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventionPostDto {
    @JsonProperty(value="id_client")
    private int idClient;

    @JsonProperty(value="natureProbleme")
    private int natureProbleme; //Type d'urgence

    @JsonProperty(value="typeIntervention")
    private int typeIntervention; //Type d'intervention (ex : urgence ou intervention)

    @JsonProperty(value="etablissement")
    private int etablissement; //id de l'établissemnt séléctionner

    @JsonProperty(value="photoIntervention")
    private String PhotoIntervention; //Bitmap encoder en Base 64; //changer en liste plustard

    @JsonProperty(value="commentaire")
    private String commentaire;

    @JsonProperty(value="statutClient")
    private int statutClient; //statut d'envoi => en cours

    @JsonProperty(value="statutArtisan")
    private int statutArtisan; //statut d'envoi => en cours

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int isStatutClient() {
        return statutClient;
    }

    public void setStatutClient(int statutClient) {
        this.statutClient = statutClient;
    }

    public int getTypeIntervention() {
        return typeIntervention;
    }

    public void setTypeIntervention(int typeIntervention) {
        this.typeIntervention = typeIntervention;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getStatutArtisan() {
        return statutArtisan;
    }

    public void setStatutArtisan(int statutArtisan) {
        this.statutArtisan = statutArtisan;
    }

    public int getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(int etablissement) {
        this.etablissement = etablissement;
    }

    public int getNatureProbleme() {
        return natureProbleme;
    }

    public void setNatureProbleme(int natureProbleme) {
        this.natureProbleme = natureProbleme;
    }

    public String getPhotoIntervention() {
        return PhotoIntervention;
    }

    public void setPhotoIntervention(String photoIntervention) {
        PhotoIntervention = photoIntervention;
    }
}

