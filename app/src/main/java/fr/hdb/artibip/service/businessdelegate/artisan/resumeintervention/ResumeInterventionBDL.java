package fr.hdb.artibip.service.businessdelegate.artisan.resumeintervention;

import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionPostDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;


public interface ResumeInterventionBDL {
    public ResumeInterventionResponseDto getResumeIntervention(String token,ResumeInterventionPostDto resumeInterventionPostDto);
}
