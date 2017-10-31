package fr.hdb.artibip.donnee.dto.ws.post.cgv;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CgvDto {

    @JsonProperty(value="hasError")
    public boolean hasError;

    @JsonProperty(value="listCgv")
    public List<ListCvgDto> listCgv;

    public boolean getHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<ListCvgDto> getListCgv() {
        return listCgv;
    }

    public void setListCgv(List<ListCvgDto> listCgv) {
        this.listCgv = listCgv;
    }
}
