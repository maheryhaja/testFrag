package fr.hdb.artibip.presentation.fragment.artisan.interventionliste;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeArtisanDto;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.donnee.dto.ws.response.ArtisanDemandeResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.artisan.facturationetape.plagehoraire.FacturationMomentInterventionFragment_;
import fr.hdb.artibip.presentation.fragment.artisan.interventiondetail.DemandeAvantAcceptationFragment_;
import fr.hdb.artibip.presentation.fragment.facturationfinale.FacturationFragment_;
import fr.hdb.artibip.service.applicatif.artisan.listedemande.ArtisanDemandeSA;
import fr.hdb.artibip.service.applicatif.artisan.listedemande.ArtisanDemandeSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;


@EFragment(R.layout.fragment_demande_artisan)
public class ArtisanDemandeListFragment extends GenericFragment {

    List<DemandeArtisanDto> demandes =  new ArrayList<DemandeArtisanDto>();
    DemandeArtisanAdapter demandeAdapter ;

    @ViewById(R.id.liste_view)
    ListView lv;

    @ViewById(R.id.text_pas_intervention)
    TextView textNoData;

    @Bean(ArtisanDemandeSAImpl.class)
    ArtisanDemandeSA artisanDemandeSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @AfterViews
    void afterViews(){
        //Dessiné l'écran
        setApplicationDesgin();
        //Init les données
        demandes = new ArrayList<>();
        getListeDemande();
    }

    void setApplicationDesgin(){
        setFooterVisibility(false);
        setToolbarVisibility(true,getString(R.string.liste_demande),true);
        setHeaderVisibility(false, false);
        textNoData.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);
    }

    @Background
    public void getListeDemande(){
        ArtisanDemandeResponseDto result = artisanDemandeSA.getListDemande();
        if(result == null) {
            noDataFound(getString(R.string.error_ws));
        } else {
            if (!result.isHasError()) {
                loadListeDemande(result.getListDemande());
            } else {
                noDataFound(result.getMessage().replace("_"," "));
            }
        }
    }

    @UiThread
    public void loadListeDemande(List<DemandeArtisanDto> demandeArtisanList){
        if(demandeArtisanList == null) {
            noDataFound(getString(R.string.pas_dintevention));
        } else {
            if(demandeArtisanList.size()>0){
                hideLoader();
                demandes.clear();
                demandes.addAll(demandeArtisanList);
                setDemandesArtisan(demandeArtisanList);
                demandeAdapter= new DemandeArtisanAdapter(getContext(),demandes );
                demandeAdapter.addListener(new AdapterListener());
                textNoData.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                lv.setAdapter(demandeAdapter);
                demandeAdapter.notifyDataSetChanged();
            } else {
                noDataFound(getString(R.string.pas_dintevention));
            }
        }
    }

    public class AdapterListener implements DemandeArtisanAdapter.AdapterListener{
        @Override
        public void onClickItem(DemandeArtisanDto item, int position) {
            if(item.getStatus() == 3) {
                setStatutsTraiterArtisan(true);
                MainActivity.idInterventionArtisan=item.getIdDemande();
                FacturationFragment_ facturationFragment = new FacturationFragment_();
                facturationFragment.setFromMenu(false);
                showLoader();
                replaceFragment(R.id.main_container, facturationFragment, true, true);
            } else {
                setStatutsTraiterArtisan(false);
                MainActivity.idInterventionArtisan=item.getIdDemande();
                DemandeAvantAcceptationFragment_ demandeAvantAcceptation = new DemandeAvantAcceptationFragment_();
                demandeAvantAcceptation.setDemandeArtisan(item);
                demandeAvantAcceptation.setUserRefused(item.isCanModify());
                if(item.getStatus() == DemandeStatus.ID_ANNULEE) {
                    demandeAvantAcceptation.setUserRefused(true);
                }
                showLoader();
                replaceFragment(R.id.main_container, demandeAvantAcceptation, true, true);
            }
        }
        @Override
        public void onClickItemFacturer(DemandeArtisanDto item, int position) {
            setStatutsTraiterArtisan(false);
            MainActivity.idInterventionArtisan=item.getIdDemande();
            FacturationMomentInterventionFragment_ facturation = new FacturationMomentInterventionFragment_();
            if(item.getDureeDeplacement() == 0) {
                facturation.setTempsDeplacements(60);
            } else {
                facturation.setTempsDeplacements(item.getDureeDeplacement());
            }
            replaceFragment(R.id.main_container, facturation , true, true);

        }
    }
    /*
    * aucune donnée
     */
    @UiThread
    void noDataFound(String message){
        setDemandesArtisan(null);
        hideLoader();
        lv.setVisibility(View.GONE);
        textNoData.setText(message.toUpperCase());
        textNoData.setVisibility(View.VISIBLE);
    }

}
