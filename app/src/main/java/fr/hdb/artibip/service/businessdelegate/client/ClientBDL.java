package fr.hdb.artibip.service.businessdelegate.client;

import fr.hdb.artibip.donnee.dto.ws.post.ClientPostDto;
import fr.hdb.artibip.donnee.dto.ws.post.EtablissementPostDto;
import fr.hdb.artibip.donnee.dto.ws.post.InterventionPostDto;
import fr.hdb.artibip.donnee.dto.ws.post.LoginChangePostDto;
import fr.hdb.artibip.donnee.dto.ws.post.LoginOubliPostDto;
import fr.hdb.artibip.donnee.dto.ws.post.LoginPostDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListEtablissementResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListInterventionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListUrgenceResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.LoginOubliResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.LoginResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;


public interface ClientBDL {
    /**
     * Se connecter à l'application
     */
    LoginResponseDto login(LoginPostDto loginPostDto);

    /**
     * Récupérer la liste des « établissements » pour un compte client
     * clienPostDto : l'obejt "client" à poster
     */
    ListEtablissementResponseDto getListEtablissement(ClientPostDto clientPostDto, String token);

    /**
     * Ajouter un « établissement » pour un compte client
     * etablissementPostDto : l'objet "établissement" à ajouter
     */
    ResponseDto insertEtablissement(EtablissementPostDto etablissementPostDto, String token);

    /**
     * Récupérer la liste des « types d’urgence »
     */
    ListUrgenceResponseDto getListUrgence(String token);

    /**
     * Enregistrement intervention + envoye sms et notification à l'employé prioritaire
     */
    ResponseDto createIntervention(InterventionPostDto interventionPostDto, String token);

    /**
     * Récupération liste des interventions
     */
    ListInterventionResponseDto getListIntervention(ClientPostDto clientPostDto, String token);

    /**
     * Récupération nouveau mot de passe
     * @param loginOubliPostDto
     * @return
     */
    LoginOubliResponseDto loginOubli(LoginOubliPostDto loginOubliPostDto);

    /**
     * Changement mot de passe
     * @param loginChangePostDto
     * @param token
     * @return
     */
    ResponseDto loginChange(LoginChangePostDto loginChangePostDto, String token);
}

