package fr.hdb.artibip.presentation.fragment.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.infoclient.InfoCLientPostDto;
import fr.hdb.artibip.presentation.activity.AccueilActivity_;
import fr.hdb.artibip.presentation.activity.MainActivity_;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.validator.ValidatorManager;
import static android.app.Activity.RESULT_OK;

@EFragment(R.layout.inscription_client)
public class InscriptionClientFragment extends GenericFragment{

    @ViewById(R.id.adresse_facture)
    TextView adresseFacture;

    @ViewById(R.id.raison_sociale)
    EditText raisonSociale;

    @ViewById(R.id.nom_prenom)
    EditText nomPrenom;

    @ViewById(R.id.adresse_mail)
    EditText adresseMail;

    @ViewById(R.id.numero)
    EditText numero;

    @ViewById(R.id.mot_de_passe)
    EditText motDePasse;

    @ViewById(R.id.confirmation_mot_de_passe)
    EditText confirmationMotDePasse;

    @ViewById(R.id.layout_main_header)
    View logo;

    @ViewById(R.id.linear_under_logo)
    LinearLayout linearUnderLogo;

    private int REQUEST_CODE_PLACE_HOLDER=123;

    @AfterViews
    void afterView(){
        if(getActivity() instanceof MainActivity_){
            setToolbarVisibility(true,getString(R.string.modification_information_generale),false);
            setHeaderVisibility(false,false);
            logo.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) linearUnderLogo.getLayoutParams();
            margin.topMargin=0;
            linearUnderLogo.setLayoutParams(margin);
        }else{
            setToolbarVisibility(true,getString(R.string.inscription_client),false);
            setHeaderVisibility(false,true);
            logo.setVisibility(View.VISIBLE);
        }


    }

    @Click(R.id.suivant)
    void clickSuivant(){
        List<EditText> listEditText = new ArrayList<>();
        listEditText.add(raisonSociale);
        listEditText.add(nomPrenom);
        listEditText.add(adresseMail);
        listEditText.add(numero);
        listEditText.add(motDePasse);
        listEditText.add(confirmationMotDePasse);
        if(!isEmpty(listEditText)) {
            if(!isNotValide(listEditText)) {
                if(getActivity() instanceof AccueilActivity_) {
                    InfoCLientPostDto infoCLientPostDto = new InfoCLientPostDto();
                    infoCLientPostDto.setRaisonSociale(raisonSociale.getText().toString());
                    infoCLientPostDto.setNomPrenom(nomPrenom.getText().toString());
                    infoCLientPostDto.setAdresseEmail(adresseMail.getText().toString());
                    infoCLientPostDto.setNumero(numero.getText().toString());
                    infoCLientPostDto.setMotDePasse(motDePasse.getText().toString());
                    infoCLientPostDto.setAdresseFacturation(adresseFacture.getText().toString());
                    getResumerInfoClientPostDto().setInfoCLientPostDto(infoCLientPostDto);
                    replaceFragment(R.id.accueil_main_container, new InformationBancaireFragment_(), false, true);
                }
            }
        }
    }

    @Click(R.id.adresse_facture)
    void clickAdresseFacture(){
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            try {
                AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                        .setTypeFilter(Place.TYPE_COUNTRY)
                        .setCountry("FR")
                        .build();

                Intent intent = new PlaceAutocomplete.IntentBuilder(
                        PlaceAutocomplete.MODE_OVERLAY)
                        .setFilter(autocompleteFilter)
                        .build(getActivity());

                startActivityForResult(intent, REQUEST_CODE_PLACE_HOLDER);
            } catch (GooglePlayServicesRepairableException e) {
                showErrorDialog(getString(R.string.information), getString(R.string.error_google_play));
            } catch (GooglePlayServicesNotAvailableException e) {
                showErrorDialog(getString(R.string.information), getString(R.string.error_google_play));
            }
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACE_HOLDER) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                String etab = place.getName() +"\n" + place.getAddress();
                adresseFacture.setText(etab.toUpperCase());
            }
        }
    }

    public boolean isEmpty(List<EditText> listEditText){
        boolean result=false;
        for(EditText editText:listEditText){
            if(TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(getString(R.string.error_field_required));
                editText.requestFocus();
                result=true;
            }else{
                editText.setError(null);
            }
        }
        if(adresseFacture.getText().toString().equals(getString(R.string.click_adresse_facturation))){
           adresseFacture.setError(getString(R.string.adresse_facture_requise));
            adresseFacture.requestFocus();
           result=true;
        }
        return result;
    }

    public boolean isNotValide(List<EditText> listEditText){
        boolean result=false;
        for(EditText editText:listEditText){
           switch (editText.getId()){
               case R.id.adresse_mail:
               {
                   if(!ValidatorManager.isValidEmailAddress(editText.getText().toString())){
                       editText.setError(getString(R.string.error_invalid_email));
                       editText.requestFocus();
                       result=true;
                   }
               }
               break;
               case R.id.numero:
               {
                   if(!ValidatorManager.isTelephonValid(editText.getText().toString())){
                       editText.setError(getString(R.string.error_invalid_tel));
                       editText.requestFocus();
                       result=true;
                   }
               }
               break;
               case R.id.confirmation_mot_de_passe:
               {
                   if(!editText.getText().toString().equals(motDePasse.getText().toString())){
                       editText.setError(getString(R.string.confirmation_mot_de_pass_incorrecte));
                       editText.requestFocus();
                       result=true;
                   }
               }
               break;
           }
        }
        return result;
    }


}
