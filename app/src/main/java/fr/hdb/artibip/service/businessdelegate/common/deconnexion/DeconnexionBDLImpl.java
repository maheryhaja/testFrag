package fr.hdb.artibip.service.businessdelegate.common.deconnexion;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.common.deconnexion.DeconnexionResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class DeconnexionBDLImpl extends AbstractCommunBDLImpl implements DeconnexionBDL {

    @Override
    public DeconnexionResponseDto postDeconnexion(String url,String token) {
        try {
            return sendMessageForEntity(null, url, HttpMethod.POST, DeconnexionResponseDto.class,
                    null, null, null, getHeaderWithToken(WsParams.AUTHORIZATION, token), getConverter()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
