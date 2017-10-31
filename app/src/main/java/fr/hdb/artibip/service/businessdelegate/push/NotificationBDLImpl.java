package fr.hdb.artibip.service.businessdelegate.push;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.push.NotificationRequestDto;
import fr.hdb.artibip.donnee.dto.ws.push.NotificationResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class NotificationBDLImpl extends AbstractCommunBDLImpl implements NotificationBDL{

    @Override
    public NotificationResponseDto postUid(String url, String token, NotificationRequestDto notificationRequestDto) {
        try {
            return sendMessageForEntity(
                    null,
                    url,
                    HttpMethod.POST,
                    NotificationResponseDto.class,
                    notificationRequestDto,
                    null,
                    null,
                    getHeaderWithToken(WsParams.AUTHORIZATION, token),
                    getConverter()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
