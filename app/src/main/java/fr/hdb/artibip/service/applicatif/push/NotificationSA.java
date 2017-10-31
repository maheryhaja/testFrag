package fr.hdb.artibip.service.applicatif.push;

import fr.hdb.artibip.donnee.dto.ws.push.NotificationResponseDto;


public interface NotificationSA {
    NotificationResponseDto postUid(String regId);
}
