package fr.hdb.artibip.donnee.dto.ws.artisan.facturation;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FacturationDto {

    /**
     * id de l'intervention
     * */
    @JsonProperty(value="idIntervention")
    private int idIntervention;

    /**
     * plageHoraire
     * */
    @JsonProperty(value="plageHoraire")
    private int plageHoraire;

    /**
     * forfait du déplacement
     */
    @JsonProperty(value="forfaitDeplacement")
    private int forfaitDeplacement;

    /**
     * dureeIntervention
     *
     * */
    @JsonProperty(value="dureeIntervention")
    private int dureeIntervention;

    /**
     * Liste matériels utlisés lors de l'intervention
     * null si pas de matériel
     *
     * */
    @JsonProperty(value="listeMateriels")
    private List<MaterielDto> listeMateriels;


    /**
     * Total matériels utlisés lors de l'intervention
     * null si pas de matériel
     *
     * */
    @JsonProperty(value="totalMateriels")
    private double totalMateriels;

    /**
     * coût total
     */
    @JsonProperty(value="coutTotal")
    private double coutTotal;

    /**
     * Tva
     */
    @JsonProperty(value = "valueTva")
    private double tva;

    /**
     * Signature
     * Base 64
     *
     * */
    @JsonProperty(value="signature")
    private String signature;

    /**
     * autreIntervention
     * boolean
     *
     * si true = commentaire et photos
     *
     * */
    @JsonProperty(value="autreIntervention")
    private boolean autreIntervention;

    /**
     * si autreInterventio ==true => commentaires et photos
     *
     * */
    @JsonProperty(value="commentaires")
    private String commentaires;

    /**
     * Base64
     * img1#a_a_e#img2
     *
     * */
    @JsonProperty(value="photos")
    private String photos;


    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    public int getPlageHoraire() {
        return plageHoraire;
    }

    public void setPlageHoraire(int plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public int getForfaitDeplacement() {
        return forfaitDeplacement;
    }

    public void setForfaitDeplacement(int forfaitDeplacement) {
        this.forfaitDeplacement = forfaitDeplacement;
    }

    public int getDureeIntervention() {
        return dureeIntervention;
    }

    public void setDureeIntervention(int dureeIntervention) {
        this.dureeIntervention = dureeIntervention;
    }

    public List<MaterielDto> getListeMateriels() {
        return listeMateriels;
    }

    public void setListeMateriels(List<MaterielDto> listeMateriels) {
        this.listeMateriels = listeMateriels;
    }

    public double getTotalMateriels() {
        return totalMateriels;
    }

    public void setTotalMateriels(double totalMateriels) {
        this.totalMateriels = totalMateriels;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isAutreIntervention() {
        return autreIntervention;
    }

    public void setAutreIntervention(boolean autreIntervention) {
        this.autreIntervention = autreIntervention;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "FacturationDto{" +
                "idIntervention=" + idIntervention +
                ", plageHoraire=" + plageHoraire +
                ", forfaitDeplacement=" + forfaitDeplacement +
                ", dureeIntervention=" + dureeIntervention +
                ", listeMateriels=" + listeMateriels +
                ", totalMateriels=" + totalMateriels +
                ", coutTotal=" + coutTotal +
                ", tva=" + tva +
                ", signature='" + signature + '\'' +
                ", autreIntervention=" + autreIntervention +
                ", commentaires='" + commentaires + '\'' +
                ", photos='" + photos + '\'' +
                '}';
    }
}
