package fr.hdb.artibip.donnee.dto;

/**
 *
 */

public class ProblemeDto {
    private String probleme;
    private String image;
    private String detail;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProbleme() {
        return probleme;
    }

    public void setProbleme(String probleme) {
        this.probleme = probleme;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
