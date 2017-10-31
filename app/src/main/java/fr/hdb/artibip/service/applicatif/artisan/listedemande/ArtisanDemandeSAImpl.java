package fr.hdb.artibip.service.applicatif.artisan.listedemande;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.donnee.dto.ws.post.ArtisanDemandePost;
import fr.hdb.artibip.donnee.dto.ws.response.ArtisanDemandeResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.listdemande.ArtisanDemandeBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.listdemande.ArtisanDemandeBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ArtisanDemandeSAImpl  implements ArtisanDemandeSA {

    @Bean(ArtisanDemandeBDLImpl.class)
    ArtisanDemandeBDL ArtisanDemandeBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @Override
    public ArtisanDemandeResponseDto getListDemande(){
        ArtisanDemandePost artisanDemandePost = new ArtisanDemandePost();
        artisanDemandePost.setIdEmploye(preferences.getId());
        ArtisanDemandeResponseDto result =  ArtisanDemandeBDL.getListDemande(preferences.getToken(), artisanDemandePost);
        if(result != null){
            return result;
        }
        return null;
    }
}
