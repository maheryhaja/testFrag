package fr.hdb.artibip.service.businessdelegate.client;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
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
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ClientBDLImpl extends AbstractCommunBDLImpl implements ClientBDL {
    /**
     * Se connecter à l'application
     */
    @Override
    public LoginResponseDto login(LoginPostDto loginPostDto){
        String url = Url.CONTEXT + Url.URL_LOGIN;
        try{
            LoginResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , LoginResponseDto.class
                    , loginPostDto
                    , null
                    , null
                    , getHeader()
                    , getConverter()
            ).getBody();
            return result;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public LoginOubliResponseDto loginOubli(LoginOubliPostDto loginOubliPostDto){
        String url = Url.CONTEXT + Url.URL_LOGIN_OUBLI;
        try{
            LoginOubliResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , LoginOubliResponseDto.class
                    , loginOubliPostDto
                    , null
                    , null
                    , getHeader()
                    , getConverter()
            ).getBody();
            return result;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * Récupérer la liste des « établissements » pour un compte client
     * clienPostDto : l'obejt client à poster
     */
    @Override
    public ListEtablissementResponseDto getListEtablissement(ClientPostDto clientPostDto,String token){
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_LIST_ETABLISSEMENT;
        try{
            ListEtablissementResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ListEtablissementResponseDto.class
                    , clientPostDto
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION,token)
                    , getConverter()
            ).getBody();
            return result;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * Ajouter un « établissement » pour un compte client
     * etablissementPostDto : l'objet "établissement" à ajouter
     */
    @Override
    public ResponseDto insertEtablissement(EtablissementPostDto etablissementPostDto, String token){
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_INSERT_ETABLISSEMNET;
        try{
            ResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ResponseDto.class
                    , etablissementPostDto
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION,token)
                    , getConverter()
            ).getBody();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Récupérer la liste des « types d’urgence »
     */
    @Override
    public ListUrgenceResponseDto getListUrgence(String token){
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_LIST_URGENCE;
        try{
            ListUrgenceResponseDto result = sendMessageForEntity(null
                    , url
                    , HttpMethod.GET
                    , ListUrgenceResponseDto.class
                    , null
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION,token)
                    , getConverter()
            ).getBody();
            return result;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * Enregistrement intervention + envoye sms et notification à l'employé prioritaire
     */
    @Override
    public ResponseDto createIntervention(InterventionPostDto interventionPostDto, String token){
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_CREATE_INTERVENTION;
        try{
            ResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ResponseDto.class
                    , interventionPostDto
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION,token)
                    , getConverter()
            ).getBody();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Récupération liste des interventions
     * @param clientPostDto : l'objet contenant l'id client
     * @param token : à mettre dans le header
     * @return
     */
    @Override
    public ListInterventionResponseDto getListIntervention(ClientPostDto clientPostDto, String token){
        String url = Url.CONTEXT + Url.DEV_WS +Url.URL_LIST_INTERVENTION;
        try{
            ListInterventionResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ListInterventionResponseDto.class
                    , clientPostDto
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION,token)
                    , getConverter()
            ).getBody();
            return result;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * Changement mot de passe
     * @param loginChangePostDto
     * @param token
     * @return
     */
    @Override
    public ResponseDto loginChange(LoginChangePostDto loginChangePostDto, String token){
        String url = Url.CONTEXT + Url.DEV_WS +Url.URL_LOGIN_CHANGE;
        try{
            ResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ResponseDto.class
                    , loginChangePostDto
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION,token)
                    , getConverter()
            ).getBody();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
