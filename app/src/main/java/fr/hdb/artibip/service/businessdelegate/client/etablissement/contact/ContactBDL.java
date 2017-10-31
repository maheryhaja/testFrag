package fr.hdb.artibip.service.businessdelegate.client.etablissement.contact;

import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactRequestDto;
import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactResponseDto;


public interface ContactBDL {
    ContactResponseDto setEtablissmentContact(String url, String token, ContactRequestDto contactRequestDto);
}
