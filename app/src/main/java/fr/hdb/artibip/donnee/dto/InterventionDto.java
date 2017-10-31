package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.MaterielDto;

/**
 * .
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventionDto {

    @JsonProperty(value = "idIntervention")
    private int idIntervention;

    @JsonProperty(value="commentaire")
    private String commentaire;

    @JsonProperty(value="statutClient")
    private int statutClient;

    @JsonProperty(value="typeIntervention")
    private int typeIntervention;

    @JsonProperty(value="statutArtisan")
    private int statutArtisan;

    @JsonProperty(value="tauxHoraireInterv")
    private double tauxHoraireInterv;

    @JsonProperty(value="cheminSignature")
    private String cheminSignature;

    @JsonProperty(value = "dateIntervention")
    private Date dateIntervention;

    @JsonProperty(value="coutTotalIntervention")
    private double coutTotalIntervention;

    @JsonProperty(value = "client")
    private String client;

    @JsonProperty(value="idClient")
    private int idClient;

    @JsonProperty(value="etablissement")
    private String etablissement;

    @JsonProperty(value = "idEtablissement")
    private int idEtablissement;

    @JsonProperty(value="natureProbleme")
    private String natureProbleme;

    @JsonProperty(value="idNatureProbleme")
    private int idNatureProbleme;

    @JsonProperty(value = "detailNature")
    private int detailNature;

    @JsonProperty(value = "plageHoraire")
    private int plageHoraire;

    @JsonProperty(value = "dureeDeplacement")
    private int dureeDeplacement;

    @JsonIgnoreProperties(value = "materiel_intervention")
    private List<MaterielDto> materielIntervention;

    @JsonIgnoreProperties(value = "detailIntervention")
    private String detailIntervention;

    @JsonProperty(value="photoIntervention")
    private String PhotoIntervention;

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getStatutClient() {
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

    public int getStatutArtisan() {
        return statutArtisan;
    }

    public void setStatutArtisan(int statutArtisan) {
        this.statutArtisan = statutArtisan;
    }

    public double getTauxHoraireInterv() {
        return tauxHoraireInterv;
    }

    public void setTauxHoraireInterv(double tauxHoraireInterv) {
        this.tauxHoraireInterv = tauxHoraireInterv;
    }

    public String getCheminSignature() {
        return cheminSignature;
    }

    public void setCheminSignature(String cheminSignature) {
        this.cheminSignature = cheminSignature;
    }

    public Date getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(Date dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public double getCoutTotalIntervention() {
        return coutTotalIntervention;
    }

    public void setCoutTotalIntervention(double coutTotalIntervention) {
        this.coutTotalIntervention = coutTotalIntervention;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public int getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(int idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public String getNatureProbleme() {
        return natureProbleme;
    }

    public void setNatureProbleme(String natureProbleme) {
        this.natureProbleme = natureProbleme;
    }

    public int getIdNatureProbleme() {
        return idNatureProbleme;
    }

    public void setIdNatureProbleme(int idNatureProbleme) {
        this.idNatureProbleme = idNatureProbleme;
    }

    public int getDetailNature() {
        return detailNature;
    }

    public void setDetailNature(int detailNature) {
        this.detailNature = detailNature;
    }

    public int getPlageHoraire() {
        return plageHoraire;
    }

    public void setPlageHoraire(int plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public int getDureeDeplacement() {
        return dureeDeplacement;
    }

    public void setDureeDeplacement(int dureeDeplacement) {
        this.dureeDeplacement = dureeDeplacement;
    }

    public List<MaterielDto> getMaterielIntervention() {
        return materielIntervention;
    }

    public void setMaterielIntervention(List<MaterielDto> materielIntervention) {
        this.materielIntervention = materielIntervention;
    }

    public String getDetailIntervention() {
        return detailIntervention;
    }

    public void setDetailIntervention(String detailIntervention) {
        this.detailIntervention = detailIntervention;
    }

    public String getPhotoIntervention() {
        return PhotoIntervention;
    }

    public void setPhotoIntervention(String photoIntervention) {
        PhotoIntervention = photoIntervention;
    }
}
