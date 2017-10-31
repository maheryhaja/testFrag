package fr.hdb.artibip.presentation.fragment.user;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.fragment.AbstractFragment;
import fr.hdb.artibip.presentation.fragment.connexion.ResumerDetailClientFragment_;

@EFragment(R.layout.demande_independance_paiement)
public class DemandeIndependancePaiementFragment extends AbstractFragment {


    @AfterViews
    void afterView(){
        setToolbarVisibility(true,getString(R.string.inscription_client),false);
        setHeaderVisibility(false,true);
    }

    @Click(R.id.relative_layout_oui)
    void clickOui(){
        getResumerInfoClientPostDto().setDemandeIndependancePaiement(true);
        replaceFragment(R.id.accueil_main_container, new ResumerDetailClientFragment_(), true, true);
    }

    @Click(R.id.relative_layout_non)
    void clickNon(){
        getResumerInfoClientPostDto().setDemandeIndependancePaiement(false);
        replaceFragment(R.id.accueil_main_container, new InscriptionEtablissementFragment_(), false, true);
    }

}
