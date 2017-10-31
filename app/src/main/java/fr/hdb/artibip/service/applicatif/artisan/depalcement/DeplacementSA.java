package fr.hdb.artibip.service.applicatif.artisan.depalcement;

import android.location.Location;
import fr.hdb.artibip.donnee.dto.ws.response.DeplacementResponseDto;


public interface DeplacementSA {
    /**
     * calculer le temps estimé du déplacement
     */
    DeplacementResponseDto calculTempsDeplacement(Location origin, int idIntervention);
}
