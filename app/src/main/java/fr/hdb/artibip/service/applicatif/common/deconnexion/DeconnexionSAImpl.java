package fr.hdb.artibip.service.applicatif.common.deconnexion;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.ws.common.deconnexion.DeconnexionResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.common.deconnexion.DeconnexionBDL;
import fr.hdb.artibip.service.businessdelegate.common.deconnexion.DeconnexionBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class DeconnexionSAImpl implements DeconnexionSA{

    @Bean(DeconnexionBDLImpl.class)
    protected DeconnexionBDL deconnexionBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @Override
    public DeconnexionResponseDto deconnect() {
        //String url = Url.CONTEXT + Url.APP_DEV + Url.DEV_WS + Url.URL_DECONNEXION;
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_DECONNEXION;
        try {
            DeconnexionResponseDto responseDto = deconnexionBDL.postDeconnexion(url, preferencesSA.getToken());
            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
