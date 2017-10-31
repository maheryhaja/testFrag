package fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.infobancaire;


public class InfoBancairePostDto {
    private String numeroCarteBancaire=new String();
    private String codeDePaiement=new String();

    public String getNumeroCarteBancaire() {
        return numeroCarteBancaire;
    }

    public void setNumeroCarteBancaire(String numeroCarteBancaire) {
        this.numeroCarteBancaire = numeroCarteBancaire;
    }

    public String getCodeDePaiement() {
        return codeDePaiement;
    }

    public void setCodeDePaiement(String codeDePaiement) {
        this.codeDePaiement = codeDePaiement;
    }
}
