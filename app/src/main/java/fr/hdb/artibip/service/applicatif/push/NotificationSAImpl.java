package fr.hdb.artibip.service.applicatif.push;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.ws.push.NotificationRequestDto;
import fr.hdb.artibip.donnee.dto.ws.push.NotificationResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.push.NotificationBDL;
import fr.hdb.artibip.service.businessdelegate.push.NotificationBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class NotificationSAImpl implements NotificationSA{

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @Bean(NotificationBDLImpl.class)
    protected NotificationBDL notificationBDL;

    @Override
    public NotificationResponseDto postUid(String regId) {
        //String url = Url.CONTEXT + Url.APP_DEV + Url.DEV_WS + Url.URL_NOTIFICATION;
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_NOTIFICATION;
        try {
            NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
            notificationRequestDto.setUid(regId);
            NotificationResponseDto responseDto = notificationBDL.postUid(url, preferencesSA.getToken(), notificationRequestDto);
            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
