package fr.hdb.artibip.service.businessdelegate.artisan.temps;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.response.ListTempsHoraireResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class TempsBDLImpl extends AbstractCommunBDLImpl implements TempsBDL{
    /**
     * Récupérer la liste des plages horaires et temps d'intervention
     */
    @Override
    public ListTempsHoraireResponseDto getListTempsHoraire (String token){
        String url = Url.CONTEXT +Url.DEV_WS + Url.URL_LIST_TEMPS;
        try{
            ListTempsHoraireResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ListTempsHoraireResponseDto.class
                    , null
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
