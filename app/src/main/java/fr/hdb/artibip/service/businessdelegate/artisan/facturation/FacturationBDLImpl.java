package fr.hdb.artibip.service.businessdelegate.artisan.facturation;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.FacturationDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class FacturationBDLImpl extends AbstractCommunBDLImpl implements FacturationBDL{
    /**
     * Ajout des éléments pour la facturation
     */
    @Override
    public ResponseDto sendFacturation(FacturationDto facturationDto, String token){
        //String url = Url.CONTEXT + Url.APP_DEV +Url.DEV_WS + Url.URL_FACTURATION;
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_FACTURATION;
        try{
            ResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ResponseDto.class
                    , facturationDto
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
