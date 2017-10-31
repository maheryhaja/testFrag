package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.utilisationmateriel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.MaterielDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.artisan.facturationetape.nouvelleintervention.FacturationNouvelleInterventionFragment_;

import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;


@EFragment(R.layout.facturation_utilisation_materiel_artisan)
public class FacturationUtilisationMaterielFragment extends GenericFragment {
    @AfterViews
    void afterView(){
        setApplicationDesing();
    }

    void setApplicationDesing(){
        PAGE_CURRENT = 2;
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        setFooterVisibility(true);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.utilisation_materiel_toolbar),false);
    }
    @Click(R.id.relative_layout_oui)
    void onClickoui(){
        MainActivity.listMateriel = new ArrayList<MaterielDto>();
        PAGE_CURRENT = 3;
        FACTURATION_POST.setListeMateriels(new ArrayList<MaterielDto>());
        FACTURATION_POST.setListeMateriels(MainActivity.listMateriel);
        FACTURATION_POST.setTotalMateriels(prixTotal(MainActivity.listMateriel));
        replaceFragment(R.id.main_container,new FacturationListeMaterielFragment_(),true,true);
    }

    @Click(R.id.relative_layout_non)
    void onClicknon(){
        MainActivity.listMateriel = new ArrayList<MaterielDto>();
        FACTURATION_POST.setListeMateriels(null);
        FACTURATION_POST.setListeMateriels(MainActivity.listMateriel);
        FACTURATION_POST.setTotalMateriels(prixTotal(MainActivity.listMateriel));
        PAGE_CURRENT = 4;
        replaceFragment(R.id.main_container,new FacturationNouvelleInterventionFragment_(),true,true);
    }

    public double prixTotal(List<MaterielDto> materielDto){
        double total = 0;
        for(int i = 0 ; i < materielDto.size();i++){
            total = total+ materielDto.get(i).getPrixMaterielHt();
        }
        return total;
    }
}
