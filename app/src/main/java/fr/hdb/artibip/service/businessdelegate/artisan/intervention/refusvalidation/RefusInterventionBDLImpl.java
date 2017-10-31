package fr.hdb.artibip.service.businessdelegate.artisan.intervention.refusvalidation;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;

import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionRequestDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class RefusInterventionBDLImpl  extends AbstractCommunBDLImpl implements RefusInterventionBDL{

    @Override
    public RefusInterventionResponseDto postRefusIntervention(String url, String token, RefusInterventionRequestDto interventionPostDto) {
        try {
            return sendMessageForEntity(null, url, HttpMethod.POST, RefusInterventionResponseDto.class,
                    interventionPostDto, null, null, getHeaderWithToken(WsParams.AUTHORIZATION, token), getConverter()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
