package fr.hdb.artibip.donnee.dto.ws.post.cgv;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListCvgDto {

    @JsonProperty(value="id")
    private int id;
    @JsonProperty(value="slug")
    private String slug;
    @JsonProperty(value="contenu")
    private  String cgv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCgv() {
        return cgv;
    }

    public void setCgv(String cgv) {
        this.cgv = cgv;
    }
}
