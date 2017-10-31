package fr.hdb.artibip.service.businessdelegate.client.etablissement.contact;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactRequestDto;
import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ContactBDLImpl extends AbstractCommunBDLImpl implements ContactBDL{

    @Override
    public ContactResponseDto setEtablissmentContact(String url, String token, ContactRequestDto contactRequestDto) {
        try {
            return sendMessageForEntity(null, url, HttpMethod.POST, ContactResponseDto.class,
                    contactRequestDto, null, null, getHeaderWithToken(WsParams.AUTHORIZATION, token), getConverter()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
