package fr.hdb.artibip.service.applicatif.client;

import fr.hdb.artibip.donnee.dto.ws.post.InterventionPostDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListEtablissementResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListInterventionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListUrgenceResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.LoginOubliResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.LoginResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;


public interface ClientSA {
    /**
     * Se connecter à l'application
     */
    LoginResponseDto login(String identifiant,String password);
    LoginOubliResponseDto loginOubli(String identifiant, int type);

    /**
     * Changement mot de passe
     * @param oldPassword
     * @param newPassword
     * @param token
     * @return
     */
    ResponseDto loginChange(int id,String oldPassword,String newPassword,String role, String token);

    /**
     * Récupérer la liste des « établissements » pour un compte client
     * idClient : l'id du client
     */
    ListEtablissementResponseDto getListEtablissement();

    /**
     * Ajouter un « établissement » pour un compte client
     * etablissementPostDto : l'objet "établissement" à ajouter
     */
    ResponseDto insertEtablissement(String etablissement, String nom, String numero);

    /**
     * Récupérer la liste des « types d’urgence »
     */
    ListUrgenceResponseDto getListUrgence();

    /**
     * Enregistrement intervention + envoye sms et notification à l'employé prioritaire
     */
    ResponseDto createIntervention(InterventionPostDto interventionPostDto);

    /**
     * Récupération liste des interventions
     */
    ListInterventionResponseDto getListIntervention(int idClient, String token);
}
