package fr.hdb.artibip.service.businessdelegate.artisan.resumeintervention;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.constantes.ws.WsParams;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionPostDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;
import static fr.hdb.artibip.commun.constantes.url.Url.APP_DEV;
import static fr.hdb.artibip.commun.constantes.url.Url.DEV_WS;
import static fr.hdb.artibip.commun.constantes.url.Url.URL_ABS_INTERVENTION;


@EBean(scope = EBean.Scope.Singleton)
public class ResumeInterventionBDLImpl extends AbstractCommunBDLImpl implements ResumeInterventionBDL{

    @Override
    public ResumeInterventionResponseDto getResumeIntervention(String token,ResumeInterventionPostDto resumeInterventionPostDto){
        //String url = Url.CONTEXT + APP_DEV + DEV_WS + URL_ABS_INTERVENTION;
        String url = Url.CONTEXT + DEV_WS + URL_ABS_INTERVENTION;
        try{
            ResumeInterventionResponseDto result =  sendMessageForEntity(null
                    , url
                    , HttpMethod.POST
                    , ResumeInterventionResponseDto.class
                    , resumeInterventionPostDto
                    , null
                    , null
                    , getHeaderWithToken(WsParams.AUTHORIZATION, token)
                    , getConverter()
            ).getBody();
            return result;
        }catch(Exception e){
            return null;
        }
    }
}
