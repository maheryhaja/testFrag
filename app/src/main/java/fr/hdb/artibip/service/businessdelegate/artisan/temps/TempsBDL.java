package fr.hdb.artibip.service.businessdelegate.artisan.temps;

import fr.hdb.artibip.donnee.dto.ws.response.ListTempsHoraireResponseDto;


public interface TempsBDL {
    /**
     * Récupérer la liste des plages horaires et temps d'intervention
     */
    ListTempsHoraireResponseDto getListTempsHoraire (String token);
}
