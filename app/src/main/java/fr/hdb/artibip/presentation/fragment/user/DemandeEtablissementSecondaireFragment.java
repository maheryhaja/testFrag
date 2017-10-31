package fr.hdb.artibip.presentation.fragment.user;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.etablissementsecondaire.EtablissementSecondairePostDto;
import fr.hdb.artibip.presentation.fragment.AbstractFragment;
import fr.hdb.artibip.presentation.fragment.connexion.ResumerDetailClientFragment_;

@EFragment(R.layout.demande_etablissement_secondaire)
public class DemandeEtablissementSecondaireFragment extends AbstractFragment {

    @AfterViews
    void afterView(){
        setToolbarVisibility(true,getString(R.string.inscription_client),false);
        setHeaderVisibility(false,true);
    }

    @Click(R.id.relative_layout_oui)
    void clickOui(){
        getResumerInfoClientPostDto().setDemandeEtablissementSecondaire(true);
        replaceFragment(R.id.accueil_main_container, new DemandeIndependancePaiementFragment_(), false, true);
    }

    @Click(R.id.relative_layout_non)
    void clickNon(){
        getResumerInfoClientPostDto().setDemandeEtablissementSecondaire(false);
        getResumerInfoClientPostDto().setDemandeIndependancePaiement(false);
        getResumerInfoClientPostDto().setDemandeIndependanceArtisan(false);
        getResumerInfoClientPostDto().setEtablissementSecondairePostDto(new ArrayList<EtablissementSecondairePostDto>());
        replaceFragment(R.id.accueil_main_container, new ResumerDetailClientFragment_(), true, true);
    }

}
