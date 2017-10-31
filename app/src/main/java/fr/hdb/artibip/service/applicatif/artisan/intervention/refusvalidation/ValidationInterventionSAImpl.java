package fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionRequestDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation.ValidationInterventionBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation.ValidationInterventionBDLImpl;

@EBean(scope = EBean.Scope.Singleton)
public class ValidationInterventionSAImpl implements ValidationInterventionSA {

    @Bean(ValidationInterventionBDLImpl.class)
    protected ValidationInterventionBDL validationInterventionBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @Override
    public ValidationInterventionResponseDto validateIntervention(int id, int hour, int min) {
        //String url = Url.CONTEXT + Url.APP_DEV + Url.DEV_WS + Url.URL_VALIDATION_INTERVENTION;
        String url = Url.CONTEXT + Url.DEV_WS + Url.URL_VALIDATION_INTERVENTION;
        try {
            ValidationInterventionRequestDto validationInterventionRequestDto = new ValidationInterventionRequestDto();
            validationInterventionRequestDto.setIdIntervention(id);
            validationInterventionRequestDto.setHeures(hour);
            validationInterventionRequestDto.setMinutes(min);
            ValidationInterventionResponseDto responseDto = validationInterventionBDL.postValidationIntervention(url, preferencesSA.getToken(), validationInterventionRequestDto);
            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
