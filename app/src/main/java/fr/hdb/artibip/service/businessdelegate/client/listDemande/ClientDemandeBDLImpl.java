package fr.hdb.artibip.service.businessdelegate.client.listdemande;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.post.ClientDemandePost;
import fr.hdb.artibip.donnee.dto.ws.response.ClientDemandeResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ClientDemandeBDLImpl extends AbstractCommunBDLImpl implements fr.hdb.artibip.service.businessdelegate.client.listdemande.ClientDemandeBDL {

    @Override
    public ClientDemandeResponseDto getListDemande(String token, ClientDemandePost clientDemande){
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_LIST_DEMANDE_CLIENT;
        try{
            ClientDemandeResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ClientDemandeResponseDto.class
                    , clientDemande
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION, token)
                    , getConverter()
            ).getBody();
            return result;
        }catch(Exception e){
            return null;
        }
    }
}
