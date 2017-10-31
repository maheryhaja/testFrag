package fr.hdb.artibip.service.businessdelegate.client.listdemande;

import fr.hdb.artibip.donnee.dto.ws.post.ClientDemandePost;
import fr.hdb.artibip.donnee.dto.ws.response.ClientDemandeResponseDto;


public interface  ClientDemandeBDL {
   ClientDemandeResponseDto getListDemande(String token, ClientDemandePost clientDemande);
}
