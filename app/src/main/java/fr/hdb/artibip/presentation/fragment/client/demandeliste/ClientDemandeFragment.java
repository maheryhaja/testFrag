package fr.hdb.artibip.presentation.fragment.client.demandeliste;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.donnee.dto.constantes.intervention.InterventionType;
import fr.hdb.artibip.donnee.dto.ws.response.ClientDemandeResponseDto;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.client.BesoinFragment_;
import fr.hdb.artibip.presentation.fragment.client.statuts.EnCoursFragment_;
import fr.hdb.artibip.presentation.fragment.client.statuts.NegativeFragment_;
import fr.hdb.artibip.presentation.fragment.client.statuts.PositiveFragment_;
import fr.hdb.artibip.presentation.fragment.facturationfinale.FacturationFragment_;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSA;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.network.NetworkManager;


@EFragment(R.layout.fragment_demande_client)
public class ClientDemandeFragment  extends GenericFragment{

    List<DemandeClientDto> demandes;
    List<DemandeClientDto> demandesUrgences;
    private DemandeClientAdapter demandesAdapter;

    @ViewById(R.id.tab_urgentes)
    RelativeLayout tabUrgentes;

    @ViewById(R.id.tab_non_urgentes)
    RelativeLayout tabNonUrgentes;

    @ViewById(R.id.listview_demande_client)
    ListView listViewDemande;

    @ViewById(R.id.text_aucune_demande)
    TextView textNoData;

    @Bean(ClientDemandeSAImpl.class)
    ClientDemandeSA clientDemandeSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    int tabActive;

    @Click(R.id.button_nouvelle_demande)
    void onClikcNouvelle(){
        replaceFragment(R.id.main_container,new BesoinFragment_(),true,true);
    }

    @AfterViews
    void afterViews(){
        //Dessiné l'écran
        setApplicationDesgin();
        //Init les données
        demandes = new ArrayList<>();
        demandesUrgences = new ArrayList<>();
        setStatusTermineeClient(false);
        getListeDemande();
    }

    @Background
    public void getListeDemande(){
        ClientDemandeResponseDto result = clientDemandeSA.getClientDemande();
        if(result == null) {
            noDataFound(getString(R.string.error_ws));
        } else {
            if (!result.isHasError()) {
                //image à afficher selon le statut de la news
                setImageEncours(result.getImageEncours());
                setNumConseiller(result.getNumeroConseiller());
                setImageRefuse(result.getImageRefuse());
                loadListeDemande(result.getListDemande());
            }else {
                noDataFound(result.getMessage().replace("_"," "));
            }
        }
    }

    @UiThread
    public void loadListeDemande(List<DemandeClientDto> demandeClientList){
        hideLoader();
        if(demandeClientList == null){
            noDataFound(getString(R.string.pas_dintevention));
        } else {
            if(demandeClientList.size() > 0){
                demandes.clear();
                setTousDesDemandesDto(demandeClientList);
                demandes.addAll(demandeClientList);
                showUrgenceListe();
                //demandesAdapter.notifyDataSetChanged();
            }else {
                noDataFound(getString(R.string.pas_dintevention));
            }
        }
    }


    @Click(R.id.tab_urgentes)
    void showUrgenceListe(){
        tabActive = 0;
        tabUrgentes.setBackgroundResource(R.color.jaune);
        tabNonUrgentes.setBackgroundResource(R.color.gris);
        demandesUrgences.clear();
        for(DemandeClientDto demandeClientDto : demandes){
            if(demandeClientDto.getTypeIntervention() == InterventionType.URGENCE){
                demandesUrgences.add(demandeClientDto);
            }
        }
        demandesAdapter = new DemandeClientAdapter(getContext(),demandesUrgences);
        listViewDemande.setAdapter(demandesAdapter);
        listViewDemande.setOnItemClickListener(onDemandeClickListener);
        demandesAdapter.notifyDataSetChanged();
        textNoData.setVisibility(View.GONE);
        listViewDemande.setVisibility(View.VISIBLE);
    }

    @Click(R.id.tab_non_urgentes)
    void showNonUrgenceListe(){
        tabActive = 1;
        tabNonUrgentes.setBackgroundResource(R.color.jaune);
        tabUrgentes.setBackgroundResource(R.color.gris);
        demandesUrgences.clear();
        for(DemandeClientDto demandeClientDto : demandes){
            if(demandeClientDto.getTypeIntervention() == InterventionType.INTERVENTION){
                demandesUrgences.add(demandeClientDto);
            }
        }
        demandesAdapter = new DemandeClientAdapter(getContext(),demandesUrgences);
        listViewDemande.setAdapter(demandesAdapter);
        listViewDemande.setOnItemClickListener(onDemandeClickListener);
        demandesAdapter.notifyDataSetChanged();
        textNoData.setVisibility(View.GONE);
        listViewDemande.setVisibility(View.VISIBLE);
    }

    /*
    * Dessiné l'écran
     */
    void setApplicationDesgin(){
        setToolbarVisibility(true,"DEMANDES",true);
        setHeaderVisibility(true,false);
        setFooterVisibility(false);
        textNoData.setVisibility(View.GONE);
        listViewDemande.setVisibility(View.GONE);
    }

    /**
     * Clique sur un item
     */
    AdapterView.OnItemClickListener onDemandeClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                if(demandesUrgences.get(i)== null){
                    return;
                }
                DemandeClientDto demandeClientDto = demandesUrgences.get(i);
                switch(demandeClientDto.getEtatIntervention()) {

                    case DemandeStatus.ID_ENVOYEE:
                        EnCoursFragment_ enCoursFragment = new EnCoursFragment_();
                        enCoursFragment.setDemande(demandeClientDto);
                        replaceFragment(R.id.main_container, enCoursFragment, true, true);
                        break;

                    case DemandeStatus.ID_EN_COURS:
                        PositiveFragment_  positiveFragment = new PositiveFragment_();
                        positiveFragment.setDemande(demandeClientDto);
                        replaceFragment(R.id.main_container, positiveFragment, true, true);
                        break;

                    case DemandeStatus.ID_TERMINEE:
                        setStatusTermineeClient(true);
                        FacturationFragment_ facturationFragment = new FacturationFragment_();
                        facturationFragment.setFromMenu(true);
                        facturationFragment.setFacturationClient(true);
                        facturationFragment.setDemandeClient(demandeClientDto);
                        showLoader();
                        replaceFragment(R.id.main_container, facturationFragment, true, true);
                        break;

                    case DemandeStatus.ID_ANNULEE:
                        NegativeFragment_ negativeFragment = new NegativeFragment_();
                        negativeFragment.setDemande(demandeClientDto);
                        replaceFragment(R.id.main_container, negativeFragment, true, true);
                        break;
                }
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    };

    /*
    * aucune donnée
     */
    @UiThread
    void noDataFound(String message){
        setTousDesDemandesDto(null);
        hideLoader();
        listViewDemande.setVisibility(View.GONE);
        textNoData.setText(message.toUpperCase());
        textNoData.setVisibility(View.VISIBLE);
    }
}
