package fr.hdb.artibip.service.businessdelegate.common.deconnexion;

import fr.hdb.artibip.donnee.dto.ws.common.deconnexion.DeconnexionResponseDto;


public interface DeconnexionBDL {
    DeconnexionResponseDto postDeconnexion(String url, String token);
}
