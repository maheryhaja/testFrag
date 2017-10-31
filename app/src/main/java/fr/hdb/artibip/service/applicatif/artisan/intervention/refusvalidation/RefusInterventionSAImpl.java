package fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionRequestDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation.RefusInterventionBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation.RefusInterventionBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class RefusInterventionSAImpl implements RefusInterventionSA {

    @Bean(RefusInterventionBDLImpl.class)
    protected RefusInterventionBDL refusInterventionBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @Override
    public RefusInterventionResponseDto postRefusIntervention(int idIntervention) {
        //String url = Url.CONTEXT + Url.APP_DEV + Url.DEV_WS + Url.URL_REFUS_INTERVENTION;
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_REFUS_INTERVENTION;
        try {
            RefusInterventionRequestDto refusInterventionRequestDto = new RefusInterventionRequestDto();
            refusInterventionRequestDto.setEmploye(preferencesSA.getId());
            refusInterventionRequestDto.setIntervention(idIntervention);
            RefusInterventionResponseDto responseDto = refusInterventionBDL.postRefusIntervention(
                    url,
                    preferencesSA.getToken(), refusInterventionRequestDto );
            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
