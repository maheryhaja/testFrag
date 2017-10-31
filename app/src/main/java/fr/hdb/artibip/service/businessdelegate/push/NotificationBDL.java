package fr.hdb.artibip.service.businessdelegate.push;

import fr.hdb.artibip.donnee.dto.ws.push.NotificationRequestDto;
import fr.hdb.artibip.donnee.dto.ws.push.NotificationResponseDto;


public interface NotificationBDL {
    NotificationResponseDto postUid(String url, String token, NotificationRequestDto notificationRequestDto);
}
