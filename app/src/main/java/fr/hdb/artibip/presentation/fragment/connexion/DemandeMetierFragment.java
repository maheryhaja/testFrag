package fr.hdb.artibip.presentation.fragment.connexion;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.fragment.AbstractFragment;
import fr.hdb.artibip.presentation.fragment.user.InscriptionClientFragment_;

@EFragment(R.layout.demande_metier)
public class DemandeMetierFragment extends AbstractFragment {

    @AfterViews
    void afterView(){
        setToolbarVisibility(true,"",true);
        setHeaderVisibility(false,true);
    }

    @Click(R.id.artisan)
    void clickArtisan(){

    }

    @Click(R.id.commercant)
    void clickCommercant(){
        replaceFragment(R.id.accueil_main_container, new InscriptionClientFragment_(), false, true);
    }
}
