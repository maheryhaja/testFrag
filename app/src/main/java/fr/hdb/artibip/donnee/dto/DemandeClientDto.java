package fr.hdb.artibip.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.ws.demandeclient.ArriveeDto;
import fr.hdb.artibip.donnee.dto.ws.demandeclient.MaterielsInterventionDto;
import fr.hdb.artibip.donnee.dto.ws.demandeclient.PhotosInterventionDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DemandeClientDto {
    @JsonProperty(value = "idIntervention")
    private int idIntervention;

    @JsonProperty(value = "etatIntervention")
     private int etatIntervention;

    @JsonProperty(value = "dateIntervention")
    private String dateIntervention;

    @JsonProperty(value = "commentaire")
    private String commentaire;

    @JsonProperty(value = "statutClient")
    private int statutClient;

    @JsonProperty(value = "typeIntervention")
    private int typeIntervention;

    @JsonProperty(value = "statutArtisan")
    private int statutArtisan;

    @JsonProperty(value = "tauxHoraireInterv")
    private int tauxHoraireIntervention;

    @JsonProperty(value = "cheminSignature")
    private String cheminSignature;

    @JsonProperty(value = "coutTotalIntervention")
    private double coutTotalIntervention;

    @JsonProperty(value = "materiel_intervention")
    private List<MaterielsInterventionDto> materielsIntervention;

    @JsonProperty(value = "client")
    private String client;

    @JsonProperty(value = "idClient")
    private int idClient;

    @JsonProperty(value = "etablissement")
    private String etablissement;

    @JsonProperty(value = "idEtablissement")
    private int idEtablissement;

    @JsonProperty(value = "dateIntervention")
    private String natureProbleme;

    @JsonProperty(value = "idNatureProbleme")
    private int idNatureProbleme;

    @JsonProperty(value = "detailNature")
    private String detailNature;

    @JsonProperty(value = "plageHoraire")
    private String plageHoraire;

    @JsonProperty(value = "dureeDeplacement")
    private String dureeDeplacement;

    @JsonProperty(value = "detailIntervention")
    private String detailIntervention;

    @JsonProperty(value = "photoIntervention")
    private List<PhotosInterventionDto> photoIntervention;

    @JsonProperty(value = "arrivee")
    private ArriveeDto arrivee;

    @JsonProperty(value = "duree_intervention")
    private double dureeIntervention;

    @JsonProperty(value = "coutMateriel")
    private double coutMateriel;

    @JsonProperty(value = "valueTva")
    private int valueTva;

    @JsonProperty(value = "autreInterventionPlanifie")
    private boolean autreInterventionPlanifie;

    @JsonProperty(value = "cout_total_TTC")
    private double coutTotalTtc;

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    public int getEtatIntervention() {
        return etatIntervention;
    }

    public void setEtatIntervention(int etatIntervention) {
        this.etatIntervention = etatIntervention;
    }

    public String getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(String dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public ArriveeDto getArrivee() {
        return arrivee;
    }

    public void setArrivee(ArriveeDto arrivee) {
        this.arrivee = arrivee;
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

    public int getTauxHoraireIntervention() {
        return tauxHoraireIntervention;
    }

    public void setTauxHoraireIntervention(int tauxHoraireIntervention) {
        this.tauxHoraireIntervention = tauxHoraireIntervention;
    }

    public String getCheminSignature() {
        return cheminSignature;
    }

    public void setCheminSignature(String cheminSignature) {
        this.cheminSignature = cheminSignature;
    }

    public double getCoutTotalIntervention() {
        return coutTotalIntervention;
    }

    public void setCoutTotalIntervention(double coutTotalIntervention) {
        this.coutTotalIntervention = coutTotalIntervention;
    }

    public List<MaterielsInterventionDto> getMaterielsIntervention() {
        return materielsIntervention;
    }

    public void setMaterielsIntervention(List<MaterielsInterventionDto> materielsIntervention) {
        this.materielsIntervention = materielsIntervention;
    }


    public List<PhotosInterventionDto> getPhotoIntervention() {
        return photoIntervention;
    }

    public void setPhotoIntervention(List<PhotosInterventionDto> photoIntervention) {
        this.photoIntervention = photoIntervention;
    }




    public String getPlageHoraire() {
        return plageHoraire;
    }

    public void setPlageHoraire(String plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public String getDureeDeplacement() {
        return dureeDeplacement;
    }

    public void setDureeDeplacement(String dureeDeplacement) {
        this.dureeDeplacement = dureeDeplacement;
    }

    public double getDureeIntervention() {
        return dureeIntervention;
    }

    public void setDureeIntervention(double dureeIntervention) {
        this.dureeIntervention = dureeIntervention;
    }

    public double getCoutMateriel() {
        return coutMateriel;
    }

    public void setCoutMateriel(double coutMateriel) {
        this.coutMateriel = coutMateriel;
    }

    public int getValueTva() {
        return valueTva;
    }

    public void setValueTva(int valueTva) {
        this.valueTva = valueTva;
    }

    public boolean isAutreInterventionPlanifie() {
        return autreInterventionPlanifie;
    }

    public void setAutreInterventionPlanifie(boolean autreInterventionPlanifie) {
        this.autreInterventionPlanifie = autreInterventionPlanifie;
    }

    public double getCoutTotalTtc() {
        return coutTotalTtc;
    }

    public void setCoutTotalTtc(double coutTotalTtc) {
        this.coutTotalTtc = coutTotalTtc;
    }
}
