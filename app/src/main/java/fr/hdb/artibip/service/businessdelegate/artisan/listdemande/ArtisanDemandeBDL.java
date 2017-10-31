package fr.hdb.artibip.service.businessdelegate.artisan.listdemande;

import fr.hdb.artibip.donnee.dto.ws.post.ArtisanDemandePost;
import fr.hdb.artibip.donnee.dto.ws.response.ArtisanDemandeResponseDto;


public interface ArtisanDemandeBDL {
    ArtisanDemandeResponseDto getListDemande(String token, ArtisanDemandePost ArtisanDemande);
}
