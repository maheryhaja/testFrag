package fr.hdb.artibip.service.applicatif.client.listDemande;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import fr.hdb.artibip.donnee.dto.ws.post.ClientDemandePost;
import fr.hdb.artibip.donnee.dto.ws.response.ClientDemandeResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.client.listdemande.ClientDemandeBDL;
import fr.hdb.artibip.service.businessdelegate.client.listdemande.ClientDemandeBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ClientDemandeSAImpl implements ClientDemandeSA {

    @Bean(ClientDemandeBDLImpl.class)
    ClientDemandeBDL clientDemandeBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @Override
    public  ClientDemandeResponseDto getClientDemande(){
        ClientDemandePost clientDemandePost = new ClientDemandePost();
        clientDemandePost.setIdClient(preferences.getId());
        ClientDemandeResponseDto result =  clientDemandeBDL.getListDemande(preferences.getToken(), clientDemandePost);
        if(result != null){
            return result;
        }
        return null;
    }
}
