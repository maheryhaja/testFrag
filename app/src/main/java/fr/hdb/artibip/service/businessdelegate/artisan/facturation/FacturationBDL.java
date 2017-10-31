package fr.hdb.artibip.service.businessdelegate.artisan.facturation;

import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.FacturationDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;


public interface FacturationBDL {
    /**
     * Ajout des éléments pour la facturation
     */
    ResponseDto sendFacturation(FacturationDto facturationDto, String token);
}
