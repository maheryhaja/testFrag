package fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation;

import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionRequestDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionResponseDto;


public interface ValidationInterventionBDL {
    ValidationInterventionResponseDto postValidationIntervention(String url, String token, ValidationInterventionRequestDto validationInterventionRequestDto);
}
