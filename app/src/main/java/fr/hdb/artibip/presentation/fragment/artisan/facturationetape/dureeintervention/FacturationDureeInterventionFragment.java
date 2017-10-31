package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.dureeintervention;

import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.TempsInterventionDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.artisan.facturationetape.utilisationmateriel.FacturationUtilisationMaterielFragment_;
import fr.hdb.artibip.service.applicatif.artisan.temps.TempsSA;
import fr.hdb.artibip.service.applicatif.artisan.temps.TempsSAImpl;
import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;

@EFragment(R.layout.facturation_duree_intervention_artisan)
public class FacturationDureeInterventionFragment extends GenericFragment {

    private int dureeMinute;
    private List<TempsInterventionDto> tempsInterventions;
    private int timeSize;
    private int idDureeIntervetion;

    @Bean(TempsSAImpl.class)
    TempsSA tempsSA;

    @ViewById(R.id.text_duree)
    TextView textViewDuree;

    @AfterViews
    void afterView(){
        setApplicationDesign();
    }

    void setApplicationDesign(){
        PAGE_CURRENT = 1;
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        setFooterVisibility(true);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.facturation),false);
        //Init Duree
        showDuree(0);
    }

    void showDuree(int position){
        String libelle;
        if(tempsInterventions == null) {
            textViewDuree.setText("");
            return;
        }

        if(tempsInterventions.size()<= 0){
            textViewDuree.setText("");
            return;
        }
        idDureeIntervetion = tempsInterventions.get(position).getId();
        libelle = tempsInterventions.get(position).getLibelle().trim();
        dureeMinute = tempsSA.StringtoMinute(libelle);
        libelle = libelle.replace(
                "heures","heure"
        ).replace(
                "heure","h"
        ).replace(
                "minutes","min"
        );
        libelle = libelle.toUpperCase();
        textViewDuree.setText(libelle);
    }

    @Click(R.id.button_valider_duree)
    void onClickValidate(){
        PAGE_CURRENT = 2;
        FACTURATION_POST.setDureeIntervention(idDureeIntervetion);
        getMainActivity().getForfait().setDureeIntervetion(textViewDuree.getText().toString());
        getMainActivity().getForfait().setMinuteDureeIntervention(dureeMinute);
        replaceFragment(R.id.main_container,new FacturationUtilisationMaterielFragment_(),true,true);
    }

    @Click(R.id.moin)
    void onClickMoin(){
        if(timeSize - 1 >= 0) {
            timeSize = timeSize - 1;
        }
        showDuree(timeSize);
    }

    @Click(R.id.plus)
    void onClickPlus(){
        if(timeSize + 1 < tempsInterventions.size()) {
            timeSize = timeSize + 1;
        }
        showDuree(timeSize);
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

    public int getDureeMinute() {
        return dureeMinute;
    }

    public void setDureeMinute(int dureeMinute) {
        this.dureeMinute = dureeMinute;
    }

    public List<TempsInterventionDto> getTempsInterventions() {
        return tempsInterventions;
    }
    public void setTempsInterventions(List<TempsInterventionDto> tempsInterventions) {
        this.tempsInterventions = tempsInterventions;
    }
    public int getTimeSize() {
        return timeSize;
    }
    public void setTimeSize(int timeSize) {
        this.timeSize = timeSize;
    }
}
