package fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient;

import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.etablissementsecondaire.EtablissementSecondairePostDto;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.infobancaire.InfoBancairePostDto;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.infoclient.InfoCLientPostDto;

public class ResumerInfoClientPostDto {

    private InfoCLientPostDto infoCLientPostDto= new InfoCLientPostDto();
    private InfoBancairePostDto infoBancairePostDto= new InfoBancairePostDto();
    private List<EtablissementSecondairePostDto> etablissementSecondairePostDto= new ArrayList<EtablissementSecondairePostDto>();
    private boolean demandeEtablissementSecondaire=false;
    private boolean demandeIndependancePaiement=false;
    private boolean demandeIndependanceArtisan=false;

    public boolean isDemandeIndependancePaiement() {
        return demandeIndependancePaiement;
    }

    public void setDemandeIndependancePaiement(boolean demandeIndependancePaiement) {
        this.demandeIndependancePaiement = demandeIndependancePaiement;
    }

    public boolean isDemandeIndependanceArtisan() {
        return demandeIndependanceArtisan;
    }

    public void setDemandeIndependanceArtisan(boolean demandeIndependanceArtisan) {
        this.demandeIndependanceArtisan = demandeIndependanceArtisan;
    }

    public boolean isDemandeEtablissementSecondaire() {
        return demandeEtablissementSecondaire;
    }

    public void setDemandeEtablissementSecondaire(boolean demandeEtablissementSecondaire) {
        this.demandeEtablissementSecondaire = demandeEtablissementSecondaire;
    }

    public InfoCLientPostDto getInfoCLientPostDto() {
        return infoCLientPostDto;
    }

    public void setInfoCLientPostDto(InfoCLientPostDto infoCLientPostDto) {
        this.infoCLientPostDto = infoCLientPostDto;
    }

    public InfoBancairePostDto getInfoBancairePostDto() {
        return infoBancairePostDto;
    }

    public void setInfoBancairePostDto(InfoBancairePostDto infoBancairePostDto) {
        this.infoBancairePostDto = infoBancairePostDto;
    }

    public List<EtablissementSecondairePostDto> getEtablissementSecondairePostDto() {
        return etablissementSecondairePostDto;
    }

    public void setEtablissementSecondairePostDto(List<EtablissementSecondairePostDto> etablissementSecondairePostDto) {
        this.etablissementSecondairePostDto = etablissementSecondairePostDto;
    }
}
