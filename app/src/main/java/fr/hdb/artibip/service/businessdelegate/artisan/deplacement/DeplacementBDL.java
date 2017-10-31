package fr.hdb.artibip.service.businessdelegate.artisan.deplacement;

import fr.hdb.artibip.donnee.dto.ws.post.DeplacementPostDto;
import fr.hdb.artibip.donnee.dto.ws.response.DeplacementResponseDto;


public interface DeplacementBDL {
    /**
     * calculer le temps estimé du déplacement
     */
    DeplacementResponseDto calculTempsDeplacement(DeplacementPostDto deplacementPostDto,String token);
}
