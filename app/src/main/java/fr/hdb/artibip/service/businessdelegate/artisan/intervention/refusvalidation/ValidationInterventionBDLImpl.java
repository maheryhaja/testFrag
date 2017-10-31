package fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionRequestDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class ValidationInterventionBDLImpl extends AbstractCommunBDLImpl implements  ValidationInterventionBDL {
    @Override
    public ValidationInterventionResponseDto postValidationIntervention(String url, String token, ValidationInterventionRequestDto validationInterventionRequestDto) {
        try {
            return sendMessageForEntity(null, url, HttpMethod.POST, ValidationInterventionResponseDto.class,
                    validationInterventionRequestDto, null, null, getHeaderWithToken(WsParams.AUTHORIZATION, token), getConverter()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
