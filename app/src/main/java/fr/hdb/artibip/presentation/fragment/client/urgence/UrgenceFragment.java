package fr.hdb.artibip.presentation.fragment.client.urgence;

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
import fr.hdb.artibip.donnee.dto.UrgenceDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListUrgenceResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.client.etablissementliste.EtablissementListFragment_;
import fr.hdb.artibip.service.applicatif.client.ClientSA;
import fr.hdb.artibip.service.applicatif.client.ClientSAImpl;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl;
import fr.hdb.artibip.utils.network.NetworkManager;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;


@EFragment(R.layout.fragment_probleme_client)
public class UrgenceFragment extends GenericFragment {

    private List<UrgenceDto> urgenceDtos;
    private UrgenceAdapter urgenceAdapter;

    @Bean(DateSAImpl.class)
    DateSA dateSA;

    @Bean(ClientSAImpl.class)
    ClientSA clientSA;

    @ViewById(R.id.grid_probleme)
    GridView gridViewProbleme;

    @AfterViews()
    void afterViews(){
        //Dessiné l'ecran
        setApplicationDesgin();
        //Init les données
        urgenceDtos= new ArrayList<UrgenceDto>();
        urgenceAdapter = new UrgenceAdapter(getContext(),urgenceDtos);
        gridViewProbleme.setAdapter(urgenceAdapter);
        gridViewProbleme.setOnItemClickListener(onUrgenceClickListener);
        //Récupérer la liste des « types d’urgence »
        showLoader();
        getListUrgence();
    }

    /*
    * Dessiné l'ecran
     */
    void setApplicationDesgin(){
        PAGE_CURRENT = 1;
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        //Avec toolbar
        setToolbarVisibility(true,"DEMANDE DU "+ dateSA.toDayWithoutYear(),false);
        setHeaderVisibility(true,true);
        setFooterVisibility(true);
        gridViewProbleme.setVisibility(View.GONE);
    }

    /**
     * Récupérer la liste des « types d’urgence »
     */
    @Background
    void getListUrgence(){
        ListUrgenceResponseDto result = clientSA.getListUrgence();
        if(result == null) {
            noDataFound(getString(R.string.aucun_urgence));
            return;
        }
        if(!result.isHasError()){
            loadListUrgence(result.getUrgences());
        }else {
            noDataFound(result.getMessage());
        }
    }

    /**
     * Afficher la liste des problèmes
     */
    @UiThread
    void loadListUrgence(List<UrgenceDto> urgences){
        hideLoader();
        if(urgences == null){
            noDataFound(getString(R.string.error_ws));
            return;
        }
        if(urgences.size()> 0) {
            urgenceDtos.clear();
            urgenceDtos.addAll(urgences);
            urgenceAdapter.notifyDataSetChanged();
            gridViewProbleme.setVisibility(View.VISIBLE);
        } else {
            noDataFound(getString(R.string.aucun_urgence));
            return;
        }
    }

    /**
     * Clique sur un item
     */
    AdapterView.OnItemClickListener onUrgenceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                if(urgenceDtos.get(i)== null){
                    return;
                }
                UrgenceDto urgenceDto = urgenceDtos.get(i);
                if(getMainActivity() != null){
                    getMainActivity().getInterventionPost().setNatureProbleme(urgenceDto.getId());
                }

                PAGE_CURRENT = 2;
                replaceFragment(R.id.main_container,new EtablissementListFragment_(),true,true);
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
        hideLoader();
        showErrorDialog(getString(R.string.information), message);
        gridViewProbleme.setVisibility(View.GONE);
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
