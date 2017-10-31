package fr.hdb.artibip.service.applicatif.client.etablissement.contact;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactRequestDto;
import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.client.etablissement.contact.ContactBDL;
import fr.hdb.artibip.service.businessdelegate.client.etablissement.contact.ContactBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ContactSAImpl implements ContactSA {

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @Bean(ContactBDLImpl.class)
    protected ContactBDL contactBDL;

    @Override
    public ContactResponseDto setEtablissmentContact(int idEtablissemnt, String tel, String name) {
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_UPDATE_CONTACT;
        try {
            ContactRequestDto contactRequestDto = new ContactRequestDto();
            contactRequestDto.setIdEtablissment(idEtablissemnt);
            contactRequestDto.setName(name);
            contactRequestDto.setNumber(tel);

            ContactResponseDto contactResponseDto = contactBDL.setEtablissmentContact(
                    url,
                    preferencesSA.getToken(),
                    contactRequestDto);
            return contactResponseDto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
