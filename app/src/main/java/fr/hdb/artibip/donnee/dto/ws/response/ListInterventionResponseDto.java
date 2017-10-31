package fr.hdb.artibip.donnee.dto.ws.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import fr.hdb.artibip.donnee.dto.InterventionDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListInterventionResponseDto {

    @JsonProperty(value="hasError")
    private boolean hasError;

    @JsonProperty(value="list_intervention")
    private List<InterventionDto> interventions;

    @JsonProperty(value="error")
    private String error;
}
