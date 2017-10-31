package fr.hdb.artibip.service.applicatif.artisan.temps;

import fr.hdb.artibip.donnee.dto.ws.response.ListTempsHoraireResponseDto;


public interface TempsSA {
    /**
     * Récupérer la liste des plages horaires et temps d'intervention
     */
    ListTempsHoraireResponseDto getListTempsHoraire ();
    /**
     * Conversion String en minute
     * @param strTime
     * @return
     */
    int StringtoMinute(String strTime);

    /**
     * Conversion String en heure
     * @param strTime
     * @return
     */
    double StringtoHour(String strTime);

    /**
     * Conversion time en String
     * @param time
     * @return
     */
    String TimeToString(int time);
}
