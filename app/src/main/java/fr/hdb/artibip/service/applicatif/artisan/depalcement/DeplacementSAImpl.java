package fr.hdb.artibip.service.applicatif.artisan.depalcement;

import android.location.Location;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.donnee.dto.ws.post.DeplacementPostDto;
import fr.hdb.artibip.donnee.dto.ws.response.DeplacementResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.deplacement.DeplacementBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.deplacement.DeplacementBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class DeplacementSAImpl implements DeplacementSA {

    @Bean(DeplacementBDLImpl.class)
    protected DeplacementBDL deplacementBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;
    /**
     * calculer le temps estimé du déplacement
     */
    @Override
    public DeplacementResponseDto calculTempsDeplacement(Location origin, int idIntervention){
        String location = origin.getLatitude() + "|" + origin.getLongitude()+ "|1";
        DeplacementPostDto deplacementPost = new DeplacementPostDto();
        deplacementPost.setOrigin(location);
        deplacementPost.setIdIntervention(idIntervention);
        DeplacementResponseDto result = deplacementBDL.calculTempsDeplacement(deplacementPost,preferencesSA.getToken());
        if(result != null){
            return result;
        }
        return null;
    }
}
