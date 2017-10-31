package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.nouvelleintervention;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.facturationfinale.FacturationFragment_;

import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;


@EFragment(R.layout.facturation_besoin_nouvelle_intervention_artisan)
public class FacturationNouvelleInterventionFragment extends GenericFragment {

    @AfterViews
    void afterView(){
        setApplicationDesing();
    }

    void setApplicationDesing(){
        PAGE_CURRENT = 4;
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        setFooterVisibility(true);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.nouvelle_intervention_toolbar),false);
    }

    @Click(R.id.relative_layout_oui)
    void onClickoui(){
        FACTURATION_POST.setAutreIntervention(true);
        PAGE_CURRENT = 5;
        replaceFragment(R.id.main_container,new FacturationDetailInterventionFragment_(),true,true);
    }

    @Click(R.id.relative_layout_non)
    void onClicknon(){
        FACTURATION_POST.setAutreIntervention(false);
        FACTURATION_POST.setCommentaires(null);
        FACTURATION_POST.setPhotos(null);
        setBitmaps(null);
        getDataAndShowFacturation();
    }

    void getDataAndShowFacturation(){
        FacturationFragment_ facturationFragment= new FacturationFragment_();
        if(getMainActivity() == null){
            return;
        }
        if (getMainActivity().getTauxHoraire()!= null){
            facturationFragment.setTva(getMainActivity().getTauxHoraire().getTauxTVA());
        }

        if(getMainActivity().getHoraire().getLibelle()!= null){
            facturationFragment.setPlageHoraire(getMainActivity().getHoraire().getLibelle().toUpperCase());
        }

        if(getMainActivity().getForfait().getDesignation() != null){
            facturationFragment.setDureeDeplacement(getMainActivity().getForfait().getDesignation().toUpperCase());
        }

        if(getMainActivity().getForfait().getDureeIntervetion()!=null){
            facturationFragment.setDureeExact(getMainActivity().getForfait().getDureeIntervetion().toUpperCase());
        }


            facturationFragment.setDureeintervention(getMainActivity().getForfait().getMinuteDureeIntervention());


            facturationFragment.setCoutDeplacement(getMainActivity().getForfait().getTaux());

        if(getMainActivity().getTauxHoraire()!=null) {
            facturationFragment.setTauxHoraire(getMainActivity().getTauxHoraire().getTauxHoraire());
        }


        replaceFragment(R.id.main_container,facturationFragment,true,true);
    }

    MainActivity getMainActivity() {
        try {
            if (getActivity() != null) {
                return (MainActivity) getActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
