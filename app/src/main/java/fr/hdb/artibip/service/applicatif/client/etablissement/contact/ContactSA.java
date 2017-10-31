package fr.hdb.artibip.service.applicatif.client.etablissement.contact;

import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactResponseDto;


public interface ContactSA {
    ContactResponseDto setEtablissmentContact(int idEtablissemnt, String tel, String name);
}
