package fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation;

import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionResponseDto;


public interface ValidationInterventionSA {
    ValidationInterventionResponseDto validateIntervention(int id, int hour, int min);
}
