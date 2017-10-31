package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.plagehoraire;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ForfaitDeplacementDto;
import fr.hdb.artibip.donnee.dto.PlageHoraireDto;
import fr.hdb.artibip.donnee.dto.TauxHoraireDto;
import fr.hdb.artibip.donnee.dto.TempsInterventionDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.FacturationDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListTempsHoraireResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.artisan.facturationetape.dureeintervention.FacturationDureeInterventionFragment_;
import fr.hdb.artibip.service.applicatif.artisan.temps.TempsSA;
import fr.hdb.artibip.service.applicatif.artisan.temps.TempsSAImpl;
import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;


@EFragment(R.layout.facturation_moment_deplacement_artisan)
public class FacturationMomentInterventionFragment extends GenericFragment {
    private List<PlageHoraireDto> horaires;
    private List<TempsInterventionDto> tempsInterventions;
    //private TauxValueDto tauxValue;
    private List<TauxHoraireDto> tauxHoraires;
    private ForfaitDeplacementDto forfaitDeplacement;
    private List<ForfaitDeplacementDto> forfaitDeplacements;
    private int tempsDeplacements;
    private HoraireAdapter horaireAdapter;

    @Bean(TempsSAImpl.class)
    TempsSA tempsSA;

    @ViewById(R.id.grid_plage_horaire)
    GridView gridViewHoraire;

    @AfterViews
    void afterView(){
        //Dessiné l'ecran
        setApplicationDesign();
        //Init les données
        horaires = new ArrayList<PlageHoraireDto>();
        tempsInterventions = new ArrayList<TempsInterventionDto>();
        tauxHoraires = new ArrayList<TauxHoraireDto>();
        forfaitDeplacements = new ArrayList<ForfaitDeplacementDto>();
        horaireAdapter = new HoraireAdapter(getContext(),horaires);
        gridViewHoraire.setAdapter(horaireAdapter);
        gridViewHoraire.setOnItemClickListener(onHoraireClickListener);
        //Récupérer la liste des horaires
        showLoader();
        getListPlageHoraire();
    }

    void setApplicationDesign(){
        setFooterVisibility(true);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getResources().getString(R.string.facturation),false);
        PAGE_CURRENT = 0;
        getMainActivity().fillPager();
    }

    /**
     * Récupérer la liste des horaires
     */
    @Background
    void getListPlageHoraire(){
        ListTempsHoraireResponseDto result = tempsSA.getListTempsHoraire();
        if(result == null) {
            noDataFound(getString(R.string.aucun_temps));
            return;
        }

        if(!result.isHasError()){
            //récupération liste temps d'intervention
            tempsInterventions.clear();
            tempsInterventions.addAll(result.getTempsInterventions());

            //récupération taux values
            //tauxValue = result.getTauxValue();

            //récupération liste forfait dépalcement
            forfaitDeplacements.clear();
            forfaitDeplacements.addAll(result.getForfaitDeplacements());

            //récupération liste taux haraire
            tauxHoraires.clear();;
            tauxHoraires.addAll(result.getTauxHoraires());

            loadListPlageHoraire(result.getPlageHoraires());
        }else {
            noDataFound(result.getMessage());
        }
    }

    /**
     * Afficher la liste des horaires
     */
    @UiThread
   void loadListPlageHoraire(List<PlageHoraireDto> ListHoraire){
        hideLoader();
        if(ListHoraire == null){
            noDataFound(getString(R.string.aucun_temps));
            return;
        }
        if(ListHoraire.size() > 0) {
            horaires.clear();
            horaires.addAll(ListHoraire);
            horaireAdapter.notifyDataSetChanged();
            gridViewHoraire.setVisibility(View.VISIBLE);
        } else {
            noDataFound(getString(R.string.aucun_temps));
            return;
        }
   }

    /**
     * Clique sur un item
     */
    AdapterView.OnItemClickListener onHoraireClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(horaires.get(i)== null){
                return;
            }
            PlageHoraireDto horaireDto = horaires.get(i);
            getMainActivity().setHoraire(horaireDto);
            initFacturation(horaireDto.getId());
        }
    };

    void initFacturation(int plageHoraire) {
        PAGE_CURRENT = 1;
        FACTURATION_POST = new FacturationDto();
        FACTURATION_POST.setPlageHoraire(plageHoraire);
        FACTURATION_POST.setIdIntervention(getIdIntervention());
        for(TauxHoraireDto taux : tauxHoraires){
            if(taux.getIdPlageHoraire() == plageHoraire){
                getMainActivity().setTauxHoraire(taux);
                break;
            }
        }
        forfaitDeplacement = getIdForfaitDeplacement(plageHoraire);
        if(forfaitDeplacement != null) {
            FACTURATION_POST.setForfaitDeplacement(forfaitDeplacement.getId());
            getMainActivity().setForfait(forfaitDeplacement);
            FacturationDureeInterventionFragment_ facturationDuree = new FacturationDureeInterventionFragment_();
            facturationDuree.setTempsInterventions(tempsInterventions);
            facturationDuree.setTimeSize(0);
            replaceFragment(R.id.main_container,facturationDuree,true,true);
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.aucun_forfait_deplacement));
        }

    }

    ForfaitDeplacementDto getIdForfaitDeplacement(int idPlageHoraire){
        ForfaitDeplacementDto result = null;
        //liste forfait selaon la plage
        List<ForfaitDeplacementDto> forfaitTemp = new ArrayList<ForfaitDeplacementDto>();
        //Filtre forfait selon la plage horaire
        for(ForfaitDeplacementDto forfait : forfaitDeplacements){
            if(forfait.getIdPlageHoraire() == idPlageHoraire) {
                forfaitTemp.add(forfait);
            }
        }

        // Trie forfait
        boolean haveChange = true;
        while(haveChange){
            haveChange = false;
            for(int i=0; i<forfaitTemp.size()-1; i++){
                if(forfaitTemp.get(i).getMinute()>forfaitTemp.get(i+1).getMinute()){
                    ForfaitDeplacementDto temp = forfaitTemp.get(i);
                    forfaitTemp.set(i,forfaitTemp.get(i+1));
                    forfaitTemp.set(i+1,temp);
                    haveChange = true;
                }
            }
        }
        //selection forfait selon le temps de déplacement
        //comparaison temps de déplacement % minute de la forfait
        for(ForfaitDeplacementDto forfait : forfaitTemp){
            if(tempsDeplacements <= forfait.getMinute()){
                result = forfait;
                return result;
            }
        }
        if(forfaitTemp.size()>0){
            return  forfaitTemp.get(forfaitTemp.size()-1);
        } else {
            return null;
        }

    }

    @UiThread
    void noDataFound(String message){
        hideLoader();
        showErrorDialog(getString(R.string.information), message);
        gridViewHoraire.setVisibility(View.GONE);
    }

    public List<PlageHoraireDto> getHoraires() {
        return horaires;
    }
    public void setHoraires(List<PlageHoraireDto> horaires) {
        this.horaires = horaires;
    }
    public List<TempsInterventionDto> getTempsInterventions() {
        return tempsInterventions;
    }
    public void setTempsInterventions(List<TempsInterventionDto> tempsInterventions) {
        this.tempsInterventions = tempsInterventions;
    }

    public List<ForfaitDeplacementDto> getForfaitDeplacements() {
        return forfaitDeplacements;
    }
    public void setForfaitDeplacements(List<ForfaitDeplacementDto> forfaitDeplacements) {
        this.forfaitDeplacements = forfaitDeplacements;
    }
    public int getTempsDeplacements() {
        return tempsDeplacements;
    }
    public void setTempsDeplacements(int tempsDeplacements) {
        this.tempsDeplacements = tempsDeplacements;
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
