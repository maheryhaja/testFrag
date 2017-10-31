package fr.hdb.artibip.presentation.fragment.artisan.apresacceptation;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.menu.Menu;
import fr.hdb.artibip.donnee.dto.constantes.permission.Permission;
import fr.hdb.artibip.donnee.dto.constantes.popup.PopUpType;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.validation.ValidationInterventionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.DeplacementResponseDto;
import fr.hdb.artibip.presentation.fragment.menu.DrawerFragment;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.view.NavDrawerItem;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.artisan.depalcement.DeplacementSA;
import fr.hdb.artibip.service.applicatif.artisan.depalcement.DeplacementSAImpl;
import fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation.ValidationInterventionSA;
import fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation.ValidationInterventionSAImpl;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl;
import fr.hdb.artibip.service.applicatif.location.LocationFinderSA;
import fr.hdb.artibip.service.applicatif.location.LocationFinderSAImpl;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;
import fr.hdb.artibip.utils.network.NetworkManager;
import static fr.hdb.artibip.presentation.activity.MainActivity.idInterventionArtisan;


@EFragment(R.layout.detail_demande_apres_acceptation_artisan)
public class DemandeApresAcceptationFragment extends GenericFragment {
    @ViewById(R.id.edit_text_heure)
    EditText editHeure;

    @ViewById(R.id.edit_text_minute)
    EditText editMinute;

    @Bean(ValidationInterventionSAImpl.class)
    protected ValidationInterventionSA validationInterventionSA;

    @Bean(DeplacementSAImpl.class)
    protected DeplacementSA deplacementSA;

    @Bean(DateSAImpl.class)
    DateSA dateSA;

    private String splitTime[];
    private int heure;
    private int minute;
    private boolean recupTime=false;
    private Location defaultLocation;

    @Bean(LocationFinderSAImpl.class)
    protected LocationFinderSA locationFinderSA;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @AfterViews
    void afterView(){
        setBackToDetail(true);
        recupTime=false;
        setFooterVisibility(false);
        setHeaderVisibility(true,false);
        setToolbarVisibility(true,getString(R.string.demande_toolbar)+" "+dateSA.toDayWithoutYear(),false);

        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) ) {
        } else {
            marshmallowGPSPremissionCheck();
        }
    }

    @Click(R.id.text_view_temps_parcours)
    void onTimeClicked() {
        recupTime=true;
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) ) {

              defaultLocation = locationFinderSA.getCurrentLocation(getActivity());
              if(defaultLocation!=null) {
                  if (NetworkManager.isNetworkAvailable(getActivity())) {
                      getTime();
                  } else {
                      showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
                  }
              }else{
                  showCustomDialog(getString(R.string.information), getString(R.string.location), PopUpType.LOCATION, -1);
              }
        } else {
            marshmallowGPSPremissionCheck();
        }

    }

    void getTime() {
        showLoader();
        new AsyncHelper<DeplacementResponseDto>() {
            @Override
            protected DeplacementResponseDto background() throws Exception {
                return deplacementSA.calculTempsDeplacement(defaultLocation, idInterventionArtisan);
            }

            @Override
            protected void success(DeplacementResponseDto response) {
                hideLoader();
                if (response == null) {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                    return;
                }
                if (response.isHasError()) {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_calcul));
                    return;
                }

                if(response.getTime() == null){
                    heure = 0;
                    minute = 0;
                    return;
                }
                splitTime = response.getTime().split(" ");
                if(splitTime.length == 4){
                    heure = Integer.valueOf(splitTime[0]);
                    minute = Integer.valueOf(splitTime[2]);
                } else {
                    if(response.getTime().indexOf("heure") > -1){
                        heure = Integer.valueOf(splitTime[0]);
                        minute = 0;
                    } else {
                        heure = 0;
                        minute = Integer.valueOf(splitTime[0]);
                    }
                }
                editHeure.setText(""+heure);
                editMinute.setText(""+minute);
            }
        }.launch(getActivity());
    }

    @Click(R.id.button_oui)
    void onClickOui(){
        try{
            attemptToValidateIntervention();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void attemptToValidateIntervention() {
        editHeure.setError(null);
        editMinute.setError(null);
        boolean cancel = false;
        String hour = editHeure.getText().toString();
        String  minute = editMinute.getText().toString();
        View focusView = null;

        if (TextUtils.isEmpty(hour)){
            editHeure.setError(getString(R.string.error_field_required));
            focusView = editHeure;
            cancel = true;

        } else  if (TextUtils.isEmpty(minute)){
            editMinute.setError(getString(R.string.error_field_required));
            focusView = editMinute;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }

        } else {
            /**
             * Represents an asynchronous login/registration task used to authenticate
             * the user.
             */
            int hh = Integer.parseInt(hour);
            int mm = Integer.parseInt(minute);

            if (NetworkManager.isNetworkAvailable(getActivity())) {
                validateIntervention(hh, mm);
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    }

    private void validateIntervention(final int hour, final int min) {
        showLoader();
        new AsyncHelper<ValidationInterventionResponseDto>() {
            @Override
            protected ValidationInterventionResponseDto background() throws Exception {
                return validationInterventionSA.validateIntervention(getIdIntervention(), hour, min);
            }

            @Override
            protected void success(ValidationInterventionResponseDto response) {
                hideLoader();
                if (response == null) {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                } else {
                    if (response.isHasError()) {
                        showErrorDialog(getString(R.string.information), response.isResult().replace("_"," ").toUpperCase());
                    } else {
                        showArtisanListDemande();
                    }
                }
            }
        }.launch(getActivity());

    }

    void showArtisanListDemande(){
        View.OnClickListener showListeDemandeListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getCustomDialog().dismiss();
                NavDrawerItem navDrawerItem = new NavDrawerItem();
                navDrawerItem.setIndexMenu(Menu.DEMANDES);
                DrawerFragment.myInstance.fragmentToShow(navDrawerItem);
            }
        };
        showSuccesDialog(getString(R.string.demande_toolbar)+" "+dateSA.toDayWithoutYear()
                , getString(R.string.demande_envoyee_au_client)
                ,showListeDemandeListner
        );
    }

    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, Permission.LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Permission.LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            defaultLocation = locationFinderSA.getCurrentLocation(getActivity());
            if (defaultLocation == null) {
                    showCustomDialog(getString(R.string.information), getString(R.string.location), PopUpType.LOCATION, -1);
            }else {
                if(recupTime){
                    if (NetworkManager.isNetworkAvailable(getActivity())) {
                        getTime();
                    } else {
                        showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
                    }
                }
            }
        } else {
            showErrorDialog(getString(R.string.information),getString(R.string.location_permission));
        }

    }
}
