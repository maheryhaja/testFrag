package fr.hdb.artibip.presentation.fragment.user;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.permission.Permission;
import fr.hdb.artibip.donnee.dto.event.EventSetEtablissementDto;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.etablissementsecondaire.EtablissementSecondairePostDto;
import fr.hdb.artibip.presentation.fragment.AbstractFragment;
import fr.hdb.artibip.presentation.fragment.connexion.InscriptionListEtablissementAdapter;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.validator.ValidatorManager;
import static android.app.Activity.RESULT_OK;

@EFragment(R.layout.inscription_etablissement)
public class InscriptionEtablissementFragment extends AbstractFragment {

    @ViewById(R.id.adresse_etablissement)
    TextView adresseEtablissementTextView;

    @ViewById(R.id.numero)
    EditText numero;

    @ViewById(R.id.mot_de_passe)
    EditText motDePasse;

    @ViewById(R.id.confirmation_mot_de_passe)
    EditText confirmationMotDePasse;

    @ViewById(R.id.list_etablissement)
    ListView listEtablissement;

    int itemSelected;
    String numEtablissement;
    private String nomEtablissement;
    private String adresseEtablissement;
    List<EtablissementSecondairePostDto> etablissementSecondairePostDtoList =  new ArrayList<>();
    int REQUEST_CODE_PLACE_HOLDER=124;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventSetEtablissementDto eventSetEtablissementDto){
        etablissementSecondairePostDtoList.remove(eventSetEtablissementDto.getPosition());
        initList(etablissementSecondairePostDtoList);

    }

    @AfterViews
    void afterView(){
        setToolbarVisibility(true,getString(R.string.inscription_client),false);
        setHeaderVisibility(false,true);
    }

    @Click(R.id.adresse_etablissement)
    void clickAdresseEtablissement(){
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
                nomEtablissement= place.getName().toString();
                adresseEtablissement= place.getAddress().toString();
                adresseEtablissementTextView.setText(etab.toUpperCase());
            }
        }
    }

    @Click(R.id.ajout_etablissement)
    void clickAjoutEtablissement(){
        List<EditText> editTextList= new ArrayList<>();
        editTextList.add(numero);
        editTextList.add(motDePasse);
        editTextList.add(confirmationMotDePasse);
        if(!isEmpty(editTextList)) {
            if(!isNotValide(editTextList)) {
                EtablissementSecondairePostDto etablissementSecondairePostDto= new EtablissementSecondairePostDto();
                etablissementSecondairePostDto.setNomEtablissement(nomEtablissement);
                etablissementSecondairePostDto.setAdresseEtablissement(adresseEtablissement);
                etablissementSecondairePostDto.setNumeroResponsable(numero.getText().toString());
                etablissementSecondairePostDto.setMotDePasse(motDePasse.getText().toString());
                etablissementSecondairePostDtoList.add(etablissementSecondairePostDto);
                initList(etablissementSecondairePostDtoList);
                setEmpty(editTextList);
            }
        }
    }


    public void initList(List<EtablissementSecondairePostDto> etablissementSecondairePostDtoList){
        InscriptionListEtablissementAdapter inscriptionListEtablissementAdapter= new InscriptionListEtablissementAdapter(getContext(),etablissementSecondairePostDtoList);
        listEtablissement.setAdapter(inscriptionListEtablissementAdapter);
        listEtablissement.setOnItemClickListener(new AdapterListener());
        setListViewEtablissementHeight(etablissementSecondairePostDtoList.size());
        listEtablissement.setSelection(etablissementSecondairePostDtoList.size()-1);
    }

    @Click(R.id.suivant)
    void clickSuivant(){
        if(etablissementSecondairePostDtoList.size()>0) {
            getResumerInfoClientPostDto().setEtablissementSecondairePostDto(etablissementSecondairePostDtoList);
            replaceFragment(R.id.accueil_main_container, new DemandeIndependanceArtisanFragment_(), false, true);
        }else{
            showErrorDialog(getString(R.string.information),getString(R.string.ajout_materiel_obligation));
        }
    }

    public boolean isEmpty(List<EditText> listEditText){
        boolean result=false;
        for(EditText editText:listEditText){
            if(TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(getString(R.string.error_field_required));
                editText.requestFocus();
                result=true;
            }
        }
        if(adresseEtablissementTextView.getText().toString().equals(getString(R.string.adresse_etablissement))){
            adresseEtablissementTextView.setError(getString(R.string.error_field_required));
            adresseEtablissementTextView.requestFocus();
            result=true;
        }
        return result;
    }

    public void setEmpty(List<EditText> listEditText){
        for(EditText editText:listEditText){
            editText.setText("");
        }
        adresseEtablissementTextView.setText(getString(R.string.adresse_etablissement));
    }

    public boolean isNotValide(List<EditText> listEditText){
        boolean result=false;
        for(EditText editText:listEditText){
            switch (editText.getId()){
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

    private class AdapterListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            for (int j = 0; j < adapterView.getChildCount(); j++) {
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
            }
            view.setBackgroundResource(R.color.color_primary_dark);
            final TextView numEtab= (TextView) view.findViewById(R.id.numero_etablissement);
            LinearLayout identiteEtab = (LinearLayout) view.findViewById(R.id.identite_etablissement);
            numEtab.setOnClickListener(new NumEtabClickListener(adapterView,view,position,numEtab));
            identiteEtab.setOnClickListener(new IdentiteEtabClickListener(adapterView,view,position));
        }
    }

     private class NumEtabClickListener implements View.OnClickListener{
         protected AdapterView<?> adapterView;
         protected View view;
         protected int position;
         protected TextView textView;

         public NumEtabClickListener(AdapterView<?> adapterView,View view,int position,TextView textView){
             this.adapterView=adapterView;
             this.view=view;
             this.position=position;
             this.textView=textView;
         }
         @Override
         public void onClick(View v) {
             for (int j = 0; j < adapterView.getChildCount(); j++) {
                 adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
             }
             view.setBackgroundResource(R.color.color_primary_dark);
             if(itemSelected==position) {
                 numEtablissement=textView.getText().toString();
                 makeCall(textView.getText().toString());
             }
             itemSelected=position;
         }
     }

    private class IdentiteEtabClickListener extends NumEtabClickListener{
        public IdentiteEtabClickListener(AdapterView<?> adapterView,View view,int position){
            super(adapterView,view,position,null);
        }
        @Override
        public void onClick(View v) {
            for (int j = 0; j < adapterView.getChildCount(); j++) {
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
            }
            view.setBackgroundResource(R.color.color_primary_dark);
            if(itemSelected==position) {
                View.OnClickListener leftButtonListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getCustomDialog().dismiss();
                    }
                };

                View.OnClickListener rightButtonListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new EventSetEtablissementDto(position));
                        DialogManager.getCustomDialog().dismiss();
                    }
                };
                DialogManager.showMessageCustom(getActivity(), getString(R.string.information), getString(R.string.supprimer_etablissement), getString(R.string.annuler), getString(R.string.supprimer), leftButtonListener, rightButtonListener);
            }
            itemSelected=position;
        }
    }

    void setListViewEtablissementHeight(int count) {
        ViewGroup.LayoutParams params = listEtablissement.getLayoutParams();
        params.height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * count, getResources().getDisplayMetrics());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        listEtablissement.setLayoutParams(params);
        listEtablissement.requestFocus();
    }

    private void marshmallowReadPemissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, Permission.PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Permission.PHONE_CALL && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numEtablissement));
            startActivity(intent);
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.phone_call_requise));
        }

    }

    void makeCall(String num){
        if ( ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
            marshmallowReadPemissionCheck();
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num));
            startActivity(intent);
        }
    }

}
