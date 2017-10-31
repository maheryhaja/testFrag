package fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.intervention;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.MaterielDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.EtablissementInfoDto;
import fr.hdb.artibip.donnee.dto.ws.demandeclient.ArriveeDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intervention {
    @JsonProperty(value="idIntervention")
    public int idIntervention;
    @JsonProperty(value="etatIntervention")
    public int etatIntervention;
    @JsonProperty(value="dateIntervention")
    public String dateIntervention;
    @JsonProperty(value="statutClient")
    public int statutClient;
    @JsonProperty(value="statutArtisan")
    public int statutArtisan;
    @JsonProperty(value="tauxHoraireInterv")
    public int tauxHoraireInterv;
    @JsonProperty(value="cheminSignature")
    public String cheminSignature;
    @JsonProperty(value="coutTotalIntervention")
    public double coutTotalIntervention;
    @JsonProperty(value="materiel_intervention")
    public List<MaterielResumeDto> materiel_intervention;
    @JsonProperty(value="client")
    public String client;
    @JsonProperty(value="idClient")
    public int idClient;
    @JsonProperty(value="idEtablissement")
    public int idEtablissement;
    @JsonProperty(value="idNatureProbleme")
    public int idNatureProbleme;
    @JsonProperty(value="detailNature")
    public String detailNature;
    @JsonProperty(value="plageHoraire")
    public String plageHoraire;
    @JsonProperty(value="dureeDeplacement")
    public String dureeDeplacement;
    @JsonProperty(value="arrivee")
    public ArriveeDto arrivee;
    @JsonProperty(value="autreInterventionPlanifie")
    public boolean autreInterventionPlanifie;
    @JsonProperty(value="coutMateriel")
    public double coutMateriel;
    @JsonProperty(value="valueTva")
    public double valueTva;
    @JsonProperty(value="cout_total_TTC")
    public double coutTotalTTC;
    @JsonProperty(value="photosTraite")
    public List<String> photosTraite;
    @JsonProperty(value="commentaireTraite")
    public String commentaireTraite;



    @JsonProperty(value="userRefused")

    public boolean userRefused;
    @JsonProperty(value="duree_intervention")
    public String dureeIntervention;
    @JsonProperty(value="etablissement")
    private EtablissementInfoDto etabliseement;
    @JsonProperty(value="typeIntervention")
    private int type;
    @JsonProperty(value="natureProbleme")
    private String nature;
    @JsonProperty(value="detailIntervention")
    private String detail;
    @JsonProperty(value="commentaire")
    private String commentaire;
    @JsonProperty(value="photos")
    private List<String> urlPhoto;

    public EtablissementInfoDto getEtabliseement() {
        return etabliseement;
    }

    public void setEtabliseement(EtablissementInfoDto etabliseement) {
        this.etabliseement = etabliseement;
    }

    public int getType() {
        return type;
    }
    public String showType(){
        return ""+type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public List<String> getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(List<String> urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

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

    public int getStatutClient() {
        return statutClient;
    }

    public void setStatutClient(int statutClient) {
        this.statutClient = statutClient;
    }

    public int getStatutArtisan() {
        return statutArtisan;
    }

    public void setStatutArtisan(int statutArtisan) {
        this.statutArtisan = statutArtisan;
    }

    public int getTauxHoraireInterv() {
        return tauxHoraireInterv;
    }

    public void setTauxHoraireInterv(int tauxHoraireInterv) {
        this.tauxHoraireInterv = tauxHoraireInterv;
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

    public List<MaterielResumeDto> getMateriel_intervention() {
        return materiel_intervention;
    }

    public void setMateriel_intervention(List<MaterielResumeDto> materiel_intervention) {
        this.materiel_intervention = materiel_intervention;
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

    public int getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(int idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public int getIdNatureProbleme() {
        return idNatureProbleme;
    }

    public void setIdNatureProbleme(int idNatureProbleme) {
        this.idNatureProbleme = idNatureProbleme;
    }

    public String getDetailNature() {
        return detailNature;
    }

    public void setDetailNature(String detailNature) {
        this.detailNature = detailNature;
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

    public ArriveeDto getArrivee() {
        return arrivee;
    }

    public void setArrivee(ArriveeDto arrivee) {
        this.arrivee = arrivee;
    }

    public boolean isAutreInterventionPlanifie() {
        return autreInterventionPlanifie;
    }

    public void setAutreInterventionPlanifie(boolean autreInterventionPlanifie) {
        this.autreInterventionPlanifie = autreInterventionPlanifie;
    }

    public double getCoutMateriel() {
        return coutMateriel;
    }

    public void setCoutMateriel(double coutMateriel) {
        this.coutMateriel = coutMateriel;
    }

    public double getValueTva() {
        return valueTva;
    }

    public void setValueTva(double valueTva) {
        this.valueTva = valueTva;
    }

    public double getCoutTotalTTC() {
        return coutTotalTTC;
    }

    public void setCoutTotalTTC(double coutTotalTTC) {
        this.coutTotalTTC = coutTotalTTC;
    }

    public List<String> getPhotosTraite() {
        return photosTraite;
    }

    public void setPhotosTraite(List<String> photosTraite) {
        this.photosTraite = photosTraite;
    }

    public String getCommentaireTraite() {
        return commentaireTraite;
    }

    public void setCommentaireTraite(String commentaireTraite) {
        this.commentaireTraite = commentaireTraite;
    }

    public boolean isUserRefused() {
        return userRefused;
    }

    public void setUserRefused(boolean userRefused) {
        this.userRefused = userRefused;
    }

    public String getDureeIntervention() {
        return dureeIntervention;
    }

    public void setDureeIntervention(String dureeIntervention) {
        this.dureeIntervention = dureeIntervention;
    }
}
