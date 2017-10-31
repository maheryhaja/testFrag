package fr.hdb.artibip.service.applicatif.artisan.resumeintervention;

import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;

public interface ResumeInterventionSA {
    public ResumeInterventionResponseDto getResumeIntervention(int idIntervention);
}
