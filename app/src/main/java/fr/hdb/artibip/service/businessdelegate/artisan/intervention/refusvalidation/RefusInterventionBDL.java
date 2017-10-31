package fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation;

import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionRequestDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionResponseDto;


public interface RefusInterventionBDL {
    RefusInterventionResponseDto postRefusIntervention(String url, String token, RefusInterventionRequestDto interventionPostDto);
}
