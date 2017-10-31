package fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation;

import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionResponseDto;


public interface RefusInterventionSA {
    RefusInterventionResponseDto postRefusIntervention(int idIntervention);
}
