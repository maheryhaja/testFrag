package fr.hdb.artibip.presentation.fragment.client.commentaire;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.client.statuts.EnCoursFragment_;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.client.ClientSA;
import fr.hdb.artibip.service.applicatif.client.ClientSAImpl;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.keyboard.KeyboardManager;
import fr.hdb.artibip.utils.network.NetworkManager;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;


@EFragment(R.layout.detail_intervention)
public class ClientCommentaireFragment extends GenericFragment{

    @ViewById(R.id.text_commentaire)
    TextView textCommentaire;

    @ViewById(R.id.edit_text_commentaire)
    EditText editCommentaire;

    @Bean(DateSAImpl.class)
    DateSA dateSA;

    @Bean(ClientSAImpl.class)
    ClientSA clientSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @AfterViews
    void afterView(){
        setApplicatinDesing();
    }

    void setApplicatinDesing(){
        PAGE_CURRENT = 4;
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        textCommentaire.setText(getString(R.string.commentaire_client));
        //Avec toolbar
        setToolbarVisibility(true,"DEMANDE DU "+ dateSA.toDayWithoutYear(),false);
        setHeaderVisibility(true,true);
        setFooterVisibility(true);
        editCommentaire.setText("");
    }

    private void attemptToPostIntervention(){

        String comment = editCommentaire.getText().toString().trim();

            if (NetworkManager.isNetworkAvailable(getActivity())) {
                if(getMainActivity() != null){
                    getMainActivity().getInterventionPost().setCommentaire(comment);
                    getMainActivity().getInterventionPost().setStatutArtisan(DemandeStatus.ID_EN_COURS);
                }
                showLoader();
                createIntervention();
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
      //  }
    }

    @Click(R.id.button_create_intervention)
    void onClikcValidate(){
        KeyboardManager.hideSoftKeyboard(getActivity());
        attemptToPostIntervention();
    }

    /**
     * Enregistrement intervention + envoye sms et notification à l'employé prioritaire
     */
    @Background
    void createIntervention(){
        ResponseDto result = clientSA.createIntervention(getMainActivity().getInterventionPost());
        hideLoader();
        if(result == null){
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            return;
        }
        if(result.isHasError()){
            showErrorDialog(getString(R.string.information), result.getMessage());
            return;
        }
        refreshScreen();
    }

    @UiThread
    void refreshScreen(){
        ((MainActivity)getActivity()).setInterventionPost(null);
        MainActivity.listFragment = null;
        ((MainActivity)getActivity()).clearBackStack();

        View.OnClickListener showEncourListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getCustomDialog().dismiss();
                EnCoursFragment_ enCoursFragment = new EnCoursFragment_();
                replaceFragment(R.id.main_container,enCoursFragment,true,false);
                /*DialogManager.getCustomDialog().dismiss();
                NavDrawerItem navDrawerItem = new NavDrawerItem();
                navDrawerItem.setTitle(Menu.DEMANDE_EN_COURS);
                DrawerFragment.myInstance.fragmentToShow(navDrawerItem);*/
            }
        };

        showSuccesDialog(getString(R.string.information)
                , getString(R.string.demande_en_cours)
                ,showEncourListner
        );
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
