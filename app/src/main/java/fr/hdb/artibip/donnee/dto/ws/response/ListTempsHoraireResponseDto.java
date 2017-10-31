package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.ForfaitDeplacementDto;
import fr.hdb.artibip.donnee.dto.PlageHoraireDto;
import fr.hdb.artibip.donnee.dto.TauxHoraireDto;
import fr.hdb.artibip.donnee.dto.TauxValueDto;
import fr.hdb.artibip.donnee.dto.TempsInterventionDto;
import fr.hdb.artibip.donnee.dto.constantes.facturation.plagehoraire.PlageHoraire;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListTempsHoraireResponseDto {

    @JsonProperty(value="hasError")
    private boolean hasError;

    @JsonProperty(value="message")
    private String message;

    @JsonProperty(value="plage_horaire")
    private List<PlageHoraireDto> plageHoraires;

    @JsonProperty(value="temps_intervention")
    private List<TempsInterventionDto> tempsInterventions;

    @JsonProperty(value = "taux_value")
    private TauxValueDto tauxValue;

    @JsonProperty(value = "forfait_deplacement")
    private List<ForfaitDeplacementDto> forfaitDeplacements;

    @JsonProperty(value = "taux_horaire")
    private List<TauxHoraireDto> TauxHoraires;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PlageHoraireDto> getPlageHoraires() {
        return plageHoraires;
    }

    public void setPlageHoraires(List<PlageHoraireDto> plageHoraires) {
        this.plageHoraires = plageHoraires;
    }

    public List<TempsInterventionDto> getTempsInterventions() {
        return tempsInterventions;
    }

    public void setTempsInterventions(List<TempsInterventionDto> tempsInterventions) {
        this.tempsInterventions = tempsInterventions;
    }

    public TauxValueDto getTauxValue() {
        return tauxValue;
    }

    public void setTauxValue(TauxValueDto tauxValue) {
        this.tauxValue = tauxValue;
    }

    public List<ForfaitDeplacementDto> getForfaitDeplacements() {
        return forfaitDeplacements;
    }

    public void setForfaitDeplacements(List<ForfaitDeplacementDto> forfaitDeplacements) {
        this.forfaitDeplacements = forfaitDeplacements;
    }

    public List<TauxHoraireDto> getTauxHoraires() {
        return TauxHoraires;
    }

    public void setTauxHoraires(List<TauxHoraireDto> tauxHoraires) {
        TauxHoraires = tauxHoraires;
    }
}
