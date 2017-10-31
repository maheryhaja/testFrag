package fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.etablissementsecondaire;

public class EtablissementSecondairePostDto {
    private String nomEtablissement=new String();
    private String adresseEtablissement=new String();
    private String numeroResponsable=new String();
    private String motDePasse=new String();

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public String getAdresseEtablissement() {
        return adresseEtablissement;
    }

    public void setAdresseEtablissement(String adresseEtablissement) {
        this.adresseEtablissement = adresseEtablissement;
    }

    public String getNumeroResponsable() {
        return numeroResponsable;
    }

    public void setNumeroResponsable(String numeroResponsable) {
        this.numeroResponsable = numeroResponsable;
    }
}
