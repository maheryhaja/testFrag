package fr.hdb.artibip.service.applicatif.artisan.facturation;

import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.FacturationDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;


public interface FacturationSA {
    /**
     * Ajout des éléments pour la facturation
     */
    ResponseDto sendFacturation(FacturationDto facturationDto);
}
