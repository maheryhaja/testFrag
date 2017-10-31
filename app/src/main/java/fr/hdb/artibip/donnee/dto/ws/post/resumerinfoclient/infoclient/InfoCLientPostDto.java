package fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.infoclient;

public class InfoCLientPostDto {
    private String raisonSociale=new String();
    private String nomPrenom=new String();
    private String adresseEmail=new String();
    private String numero=new String();
    private String motDePasse=new String();
    private String adresseFacturation=new String();

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdresseFacturation() {
        return adresseFacturation;
    }

    public void setAdresseFacturation(String adresseFacturation) {
        this.adresseFacturation = adresseFacturation;
    }

}
