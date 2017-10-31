package fr.hdb.artibip.service.businessdelegate.artisan.deplacement;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.post.DeplacementPostDto;
import fr.hdb.artibip.donnee.dto.ws.response.DeplacementResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class DeplacementBDLImpl extends AbstractCommunBDLImpl implements DeplacementBDL {
    /**
     * calculer le temps estimé du déplacement
     */
    @Override
    public DeplacementResponseDto calculTempsDeplacement(DeplacementPostDto deplacementPostDto,String token){
        String url = Url.CONTEXT +Url.DEV_WS + Url.URL_DISTANCE;
        Log.e("WS", "PARAMS : "+deplacementPostDto.toString());
        try{
            DeplacementResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , DeplacementResponseDto.class
                    , deplacementPostDto
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
