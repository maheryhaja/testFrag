package fr.hdb.artibip.presentation.fragment.client;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.donnee.dto.constantes.intervention.InterventionType;
import fr.hdb.artibip.donnee.dto.ws.post.InterventionPostDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.client.urgence.UrgenceFragment_;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.network.NetworkManager;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;

@EFragment(R.layout.fragment_besoin_client)
public class BesoinFragment extends GenericFragment {

    @Bean(DateSAImpl.class)
    DateSA dateSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @AfterViews()
    void afterViews(){
        //Dessiné l'ecran
        PAGE_CURRENT = 0;
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        setApplicationDesgin();
        getMainActivity().fillPager();
    }

    /*
    * Dessiné l'ecran
     */
    void setApplicationDesgin(){
        //Avec toolbar
        setToolbarVisibility(true,"DEMANDE DU "+ dateSA.toDayWithoutYear(),false);
        setHeaderVisibility(true,false);
        setFooterVisibility(true);
    }

    @Click(R.id.layout_card_urgence)
    void onClickUrgence(){
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            if(getMainActivity() != null){
                getMainActivity().setInterventionPost(new InterventionPostDto());
                getMainActivity().getInterventionPost().setTypeIntervention(InterventionType.URGENCE);
                getMainActivity().getInterventionPost().setStatutClient(DemandeStatus.ID_EN_COURS);
            }
            PAGE_CURRENT = 1;
            replaceFragment(R.id.main_container,new UrgenceFragment_(),true,true);
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
    }

    @Click(R.id.layout_card_intervention)
    void onClickIntervention(){
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            if(getMainActivity() != null){
                getMainActivity().setInterventionPost(new InterventionPostDto());
                getMainActivity().getInterventionPost().setIdClient(preferencesSA.getId());
                getMainActivity().getInterventionPost().setTypeIntervention(InterventionType.INTERVENTION);
                getMainActivity().getInterventionPost().setStatutClient(DemandeStatus.ID_EN_COURS);
            }
            PAGE_CURRENT = 1;
            replaceFragment(R.id.main_container,new UrgenceFragment_(),true,true);
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
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