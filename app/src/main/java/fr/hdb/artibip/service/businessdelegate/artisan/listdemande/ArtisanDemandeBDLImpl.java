package fr.hdb.artibip.service.businessdelegate.artisan.listdemande;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.post.ArtisanDemandePost;
import fr.hdb.artibip.donnee.dto.ws.response.ArtisanDemandeResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;

@EBean(scope = EBean.Scope.Singleton)
public class ArtisanDemandeBDLImpl extends AbstractCommunBDLImpl implements ArtisanDemandeBDL {

    @Override
    public ArtisanDemandeResponseDto getListDemande(String token, ArtisanDemandePost artisanDemande){
        //String url = Url.CONTEXT + Url.APP_DEV + Url.DEV_WS + Url.URL_LIST_DEMANDE_ARTISAN;
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_LIST_DEMANDE_ARTISAN;
        try{
            ArtisanDemandeResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ArtisanDemandeResponseDto.class
                    , artisanDemande
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
