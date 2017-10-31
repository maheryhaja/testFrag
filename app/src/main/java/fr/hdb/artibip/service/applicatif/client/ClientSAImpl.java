package fr.hdb.artibip.service.applicatif.client;

import android.content.Context;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
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
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.client.ClientBDL;
import fr.hdb.artibip.service.businessdelegate.client.ClientBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ClientSAImpl implements ClientSA {

    @RootContext
    protected Context context;

    @Bean(ClientBDLImpl.class)
    protected ClientBDL clientBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    /**
     * Se connecter à l'application
     */
    @Override
    public LoginResponseDto login(String identifiant, String password){
        LoginPostDto loginPost = new LoginPostDto();
        loginPost.setIdentifiant(identifiant);
        loginPost.setPassword(password);
        LoginResponseDto result = clientBDL.login(loginPost);
        if(result != null){
            return result;
        }
        return null;
    }

    @Override
    public LoginOubliResponseDto loginOubli(String identifiant, int type){
        LoginOubliPostDto loginOubliPost = new LoginOubliPostDto();
        loginOubliPost.setIdentifiant(identifiant);
        loginOubliPost.setType(type);
        LoginOubliResponseDto result = clientBDL.loginOubli(loginOubliPost);
        if(result != null){
            return result;
        }
        return null;
    }

    /**
     * Changement mot de passe
     * @param oldPassword
     * @param newPassword
     * @param token
     * @return
     */
    @Override
    public ResponseDto loginChange(int id,String oldPassword,String newPassword,String role, String token){
        LoginChangePostDto loginChangePost = new LoginChangePostDto();
        loginChangePost.setId(id);
        loginChangePost.setOldPassword(oldPassword);
        loginChangePost.setNewPassword(newPassword);
        loginChangePost.setRole(role);
        try{
            ResponseDto result = clientBDL.loginChange(loginChangePost,token);
            if(result!= null){
                return result;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Récupérer la liste des « établissements » pour un compte client
     * idClient : l'id du client
     */
    @Override
    public ListEtablissementResponseDto getListEtablissement(){
        ClientPostDto clientPost = new ClientPostDto();
        clientPost.setIdClient(preferencesSA.getId());
        try{
            ListEtablissementResponseDto result = clientBDL.getListEtablissement(clientPost,preferencesSA.getToken());
            if(result != null){
                return result;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Ajouter un « établissement » pour un compte client
     * etablissementPostDto : l'objet "établissement" à ajouter
     */
    public ResponseDto insertEtablissement(String etablissement, String nom, String numero){
        EtablissementPostDto etablissementPost = new EtablissementPostDto();
        etablissementPost.setEtablissement(etablissement);
        etablissementPost.setNomPersonne(nom);
        etablissementPost.setNumeroTelephone(numero);
        etablissementPost.setIdClient(preferencesSA.getId());
        try{
            ResponseDto result = clientBDL.insertEtablissement(etablissementPost, preferencesSA.getToken());
            if(result!= null){
                return result;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * Récupérer la liste des « types d’urgence »
     */
    @Override
    public ListUrgenceResponseDto getListUrgence(){
        try{
            ListUrgenceResponseDto result = clientBDL.getListUrgence(preferencesSA.getToken());
            if(result != null){
                return result;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * Enregistrement intervention + envoye sms et notification à l'employé prioritaire
     */
    @Override
    public ResponseDto createIntervention(InterventionPostDto interventionPostDto){
        InterventionPostDto interventionPost = new InterventionPostDto();
        interventionPost = interventionPostDto;
        try{
            ResponseDto result = clientBDL.createIntervention(interventionPost,preferencesSA.getToken());
            if(result!= null){
                return result;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Récupération liste des interventions
     */
    @Override
    public ListInterventionResponseDto getListIntervention(int idClient, String token){
        ClientPostDto clientPost = new ClientPostDto();
        clientPost.setIdClient(idClient);
        try{
            ListInterventionResponseDto result = clientBDL.getListIntervention(clientPost,token);
            if(result != null){
                return result;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
