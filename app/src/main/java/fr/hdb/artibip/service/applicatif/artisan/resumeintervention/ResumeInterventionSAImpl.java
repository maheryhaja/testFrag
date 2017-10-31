package fr.hdb.artibip.service.applicatif.artisan.resumeintervention;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionPostDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.resumeintervention.ResumeInterventionBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.resumeintervention.ResumeInterventionBDLImpl;

@EBean(scope = EBean.Scope.Singleton)
public class ResumeInterventionSAImpl implements ResumeInterventionSA{

    @Bean(ResumeInterventionBDLImpl.class)
    ResumeInterventionBDL resumeInterventionBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @Override
    public ResumeInterventionResponseDto getResumeIntervention(int idIntervention){
        ResumeInterventionPostDto  resumeInterventionPostDto= new ResumeInterventionPostDto();
        resumeInterventionPostDto.setIdIntervention(idIntervention);
        ResumeInterventionResponseDto result= resumeInterventionBDL.getResumeIntervention(preferences.getToken(),resumeInterventionPostDto);
        if(result != null){
            return result;
        }
        return null;
    }
}
