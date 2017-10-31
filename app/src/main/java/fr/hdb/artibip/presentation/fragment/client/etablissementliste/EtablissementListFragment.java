package fr.hdb.artibip.presentation.fragment.client.etablissementliste;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.EtablissementDto;
import fr.hdb.artibip.donnee.dto.event.EventSetListEtabDto;
import fr.hdb.artibip.donnee.dto.ws.client.etablissement.contact.ContactResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ListEtablissementResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.photo.PhotoFragment_;
import fr.hdb.artibip.service.applicatif.client.ClientSA;
import fr.hdb.artibip.service.applicatif.client.ClientSAImpl;
import fr.hdb.artibip.service.applicatif.client.etablissement.contact.ContactSA;
import fr.hdb.artibip.service.applicatif.client.etablissement.contact.ContactSAImpl;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.url.EnvironmentManager;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static fr.hdb.artibip.presentation.activity.GenericActivity.getCurrentActivity;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;


@EFragment(R.layout.fragment_list_etablissement)
public class EtablissementListFragment extends GenericFragment {

    private List<EtablissementDto> etablissementDtos;
    private EtablissementAdapter etablissementAdapter;
    private EtablissementDto selectedEtab;
    private boolean fromMenu = false;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Bean(DateSAImpl.class)
    DateSA dateSA;

    //données établissement
    private boolean isEtablissement = false;
    private String etablissement;
    private String nomPersonne;
    private String numero;

    //données contact
    private boolean isContact = false;

    @Bean(ClientSAImpl.class)
    ClientSA clientSA;

    @ViewById(R.id.scrollView_etablissement)
    ScrollView scrollView;

    @ViewById(R.id.listview_etablissement)
    ListView listViewEtablissement;

    @ViewById(R.id.text_aucun_etablissement)
    TextView textAucunEtablissement;

    @ViewById(R.id.text_modif_etablissement)
    TextView textModifEtablissement;

    @ViewById(R.id.text_supp_etablissement)
    TextView textSuppEtablissement;

    @ViewById(R.id.text_ajout_etablissement)
    TextView textAjoutEtablissement;

    @ViewById(R.id.layout_add_etablissement)
    LinearLayout layoutEtablissement;

    @ViewById(R.id.text_view_adresse_etablissment)
    TextView  textViewAdresseEtab;

    @ViewById(R.id.edit_nom_personne)
    EditText editNomPersonne;

    @ViewById(R.id.edit_numero)
    EditText editNumero;

    @ViewById(R.id.button_add_etablissement)
    Button buttonAddEtablissement;

    @ViewById(R.id.button_change_contact)
    Button buttonChangeContact;

    @ViewById(R.id.layout_name_num)
    RelativeLayout layoutNameNum;

    @ViewById(R.id.text_nom_contact)
    TextView textNomContact;

    @ViewById(R.id.text_numero_contact)
    TextView textNumeroContact;

    @ViewById(R.id.text_changer_contact)
    TextView textChangerContact;

    @ViewById(R.id.layout_change_contact)
    LinearLayout layoutContact;

    @ViewById(R.id.edit_contact_personne)
    EditText editContactPersonne;

    @ViewById(R.id.edit_contact_numero)
    EditText editContactNumero;

    @ViewById(R.id.tab_questions)
    RelativeLayout tabQuestions;

    @ViewById(R.id.tab_etablissements)
    RelativeLayout tabEtablissements;

    @ViewById(R.id.lin_questions)
    LinearLayout linQuestions;

    @ViewById(R.id.lin_etablissements)
    LinearLayout linEtablissements;

    @Bean(ContactSAImpl.class)
    protected ContactSA contactSA;

    int tabActive;

    @AfterViews()
    void afterViews(){
        //Dessiné l'ecran
        setApplicationDesgin();
        //Init les données
        etablissementDtos = new ArrayList<EtablissementDto>();
        etablissementAdapter = new EtablissementAdapter(getContext(),etablissementDtos);
        listViewEtablissement.setAdapter(etablissementAdapter);
        listViewEtablissement.setOnItemClickListener(onEtablisementClickListener);

        //Récupérer la liste des « établissements » pour un compte client
        //showProgress(getString(R.string.progress_list_etablissement));
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            showLoader();
            getListEtablissement();
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
        showEtablissements();
    }

    /*
    * Dessiné l'ecran
     */
    void setApplicationDesgin(){
        String htmlString;
        //Init Header
        setHeaderVisibility(true,true);
        if(isFromMenu()){
            setToolbarVisibility(true,getString(R.string.nav_liste_etablissements),false);
            setHeaderVisibility(true,false);
            setFooterVisibility(false);
        } else {
            PAGE_CURRENT = 2;
            clientPager.setCurrentItem(PAGE_CURRENT,true);
            //Avec toolbar
            setToolbarVisibility(true,"DEMANDE DU "+ dateSA.toDayWithoutYear(),false);
            setFooterVisibility(true);
        }

        //Init textview
        htmlString="<u>"+getString(R.string.modifier_etablissement)+"</u>";
        textModifEtablissement.setText(Html.fromHtml(htmlString));

        htmlString="<u>"+getString(R.string.supprimer_etablissement)+"</u>";
        textSuppEtablissement.setText(Html.fromHtml(htmlString));

        htmlString="<u>"+getString(R.string.ajouter_etablissement)+"</u>";
        textAjoutEtablissement.setText(Html.fromHtml(htmlString));

        htmlString="<u>"+getString(R.string.changer_contact)+"</u>";
        textChangerContact.setText(Html.fromHtml(htmlString));

        //Ne pas afficher la listView et le textView
        listViewEtablissement.setVisibility(View.GONE);
        textAucunEtablissement.setVisibility(View.GONE);
        //Ne pas afficher les layout d'edition
        layoutNameNum.setVisibility(View.GONE);
        layoutEtablissement.setVisibility(View.GONE);
        isEtablissement = false;
        layoutContact.setVisibility(View.GONE);
        isContact = false;
        selectedEtab = null;
    }

    @Click(R.id.tab_questions)
    void showQuestions(){
        tabActive = 0;
        tabQuestions.setBackgroundResource(R.color.jaune);
        linEtablissements.setVisibility(View.GONE);
        tabEtablissements.setBackgroundResource(R.color.gris);
        linQuestions.setVisibility(View.VISIBLE);
    }

    @Click(R.id.tab_etablissements)
    void showEtablissements(){
        tabActive = 1;
        tabEtablissements.setBackgroundResource(R.color.jaune);
        linQuestions.setVisibility(View.GONE);
        tabQuestions.setBackgroundResource(R.color.gris);
        linEtablissements.setVisibility(View.VISIBLE);
    }

    @UiThread
    void setListViewEtablissementHeight(int count) {
        ViewGroup.LayoutParams params = listViewEtablissement.getLayoutParams();
        if (EnvironmentManager.isTablet(getCurrentActivity())) {
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125 * count, getResources().getDisplayMetrics());
            params.height=params.height+ (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        }
        else {
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90 * count, getResources().getDisplayMetrics());
        }
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        listViewEtablissement.setLayoutParams(params);
        listViewEtablissement.requestFocus();
    }

    @UiThread
    void showHeader(){
        setHeaderVisibility(true);
    }

    /**
     *Récupérer la liste des « établissements » pour un compte client
     */
    @Background
    void getListEtablissement(){
        showHeader();
        ListEtablissementResponseDto result = clientSA.getListEtablissement();
        if(result == null){
            hideLoader();
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            return;
        }

        if(!result.isHasError()){
            loadListEtablissement(result.getEtablissements());
        } else {
            hideLoader();
            showErrorDialog(getString(R.string.information)
                    , result.getError().toUpperCase().replace("_"," ")
            );
        }
    }

    /**
     * Afficher la liste des établissements
     */
    @UiThread
    void loadListEtablissement(List<EtablissementDto> etablissements){
        hideLoader();

        if(etablissements == null){
            showErrorDialog(getString(R.string.information), getString(R.string.aucun_etablissement));
            return;
        }

        if(etablissements.size() > 0){
            if(etablissementDtos!= null){
                etablissementDtos.clear();
            }
            etablissementDtos.addAll(etablissements);
            etablissementAdapter.notifyDataSetChanged();
            etablissementAdapter.setEtablissementInterface(etablissementInterface);
            textAucunEtablissement.setVisibility(View.GONE);
            listViewEtablissement.setVisibility(View.VISIBLE);
            setListViewEtablissementHeight(etablissements.size());
        }else {
            showErrorDialog(getString(R.string.information), getString(R.string.aucun_etablissement));
        }
    }

    @Click(R.id.text_ajout_etablissement)
    void onClickEtablissemnt(){
        if(isEtablissement) {
            layoutEtablissement.setVisibility(View.GONE);
            setHeaderVisibility(true);
        } else {
            layoutEtablissement.setVisibility(View.VISIBLE);
            layoutContact.setVisibility(View.GONE);
            isContact = false;
            layoutEtablissement.requestFocus();
            setHeaderVisibility(false);
        }
        isEtablissement = !isEtablissement;
    }

    @Click(R.id.text_view_adresse_etablissment)
    protected void onAdressClicked() {
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

                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                showErrorDialog(getString(R.string.information), getString(R.string.error_google_play));
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                showErrorDialog(getString(R.string.information), getString(R.string.error_google_play));
                // TODO: Handle the error.
            }
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                etablissement = place.getName().toString()+", "+place.getAddress().toString();
                etablissement = etablissement.replace("\n"," ");
                String etab = place.getName()
                        +"\n" + place.getAddress();
                        //+"\n" + place.getPhoneNumber();

                textViewAdresseEtab.setText(etab.toUpperCase());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Click(R.id.text_changer_contact)
    void onClickContact(){
        if(isContact) {
            layoutContact.setVisibility(View.GONE);
            setHeaderVisibility(true);
        } else {
            layoutContact.setVisibility(View.VISIBLE);
            layoutEtablissement.setVisibility(View.GONE);
            isEtablissement = false;
            layoutContact.requestFocus();
            setHeaderVisibility(false);
        }
        isContact = !isContact;
    }

    @Click(R.id.button_add_etablissement)
    void onClickAddEtablissement(){
        attemptToAddEtablissment();
    }

    /**
     * Ajouter un « établissement » pour un compte client
     */
    @Background
    void insertEtablissement(){
        ResponseDto result = clientSA.insertEtablissement(etablissement, nomPersonne, numero);
        if(result == null){
            hideLoader();
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            return;
        }
        if(result.isHasError()){
            hideLoader();
            showErrorDialog(getString(R.string.information), result.getError().toUpperCase());
            return;
        }
        refreshListEtablissement();
    }

    @UiThread
    void refreshListEtablissement(){
        hideLoader();
        clearEtablissementData();
        textViewAdresseEtab.setMaxLines(2);
        textViewAdresseEtab.setLines(2);
        onClickEtablissemnt();
        showEtabDialog(getString(R.string.information), getString(R.string.ajout_etablissement));
    }

    void clearEtablissementData(){
        textViewAdresseEtab.setText("");
        editNomPersonne.setText("");
        editNumero.setText("");
    }

    void clearContactData() {
        layoutNameNum.setVisibility(View.GONE);
        textNomContact.setText("");
        textNumeroContact.setText("");
        listViewEtablissement.setSelection(-1);
        editContactNumero.setText("");
        editContactPersonne.setText("");
        selectedEtab = null;
    }

    @Click(R.id.button_change_contact)
    void onClickChangeContact(){
        if (selectedEtab == null) {
            showErrorDialog(getString(R.string.information), getString(R.string.nope_contact));
            return;
        }
        attemptToSetEtablissmentContact();
    }

    void attemptToSetEtablissmentContact(){
        editContactPersonne.setError(null);
        editContactNumero.setError(null);
        View focusView = null;
        boolean cancel = false;
        String name = editContactPersonne.getText().toString().trim();
        String tel = editContactNumero.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            cancel = true;
            focusView = editContactPersonne;
            editContactPersonne.setError(getString(R.string.error_field_required));

        } else if (TextUtils.isEmpty(tel)) {
            cancel = true;
            focusView = editContactNumero;
            editContactNumero.setError(getString(R.string.error_field_required));
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (NetworkManager.isNetworkAvailable(getActivity())) {
               postSetContact(selectedEtab.getId(), tel, name);
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    }

    void attemptToAddEtablissment(){

        editNomPersonne.setError(null);
        editNumero.setError(null);
        View focusView = null;
        boolean cancel = false;
        nomPersonne = editNomPersonne.getText().toString().trim();
        numero = editNumero.getText().toString().trim();

         if (TextUtils.isEmpty(nomPersonne)) {
            cancel = true;
            focusView = editNomPersonne;
            editNomPersonne.setError(getString(R.string.error_field_required));

        } else if (TextUtils.isEmpty(numero)) {
            cancel = true;
            focusView = editNumero;
            editNumero.setError(getString(R.string.error_field_required));
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                showLoader();
                insertEtablissement();
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    }

    /**
     * Clique sur un item
     */
    AdapterView.OnItemClickListener onEtablisementClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            view.setSelected(true);
            for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

            // change the background color of the selected element
            view.setBackgroundResource(R.color.color_primary_dark);

            if(etablissementDtos.get(i)== null){
                return;
            }
            EtablissementDto etablissementDto = etablissementDtos.get(i);
            selectedEtab = etablissementDto;
            showContact(etablissementDto.getNomContact(),etablissementDto.getNumeroPorable());
        }
    };

    private EtablissementAdapter.EtablissementInterface etablissementInterface = new EtablissementAdapter.EtablissementInterface() {
        @Override
        public void selectEtablissement(EtablissementDto itemDto, int position) {
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                if(itemDto == null || isFromMenu()) {
                    return;
                }

                etablissementAdapter.notifyDataSetChanged();
                if(getMainActivity() != null){
                    getMainActivity().getInterventionPost().setEtablissement(itemDto.getId());
                }
                PAGE_CURRENT = 3;
                selectedEtab = null;
                //delete all photos
                MainActivity.listFragment = null;
                replaceFragment(R.id.main_container,new PhotoFragment_(),true,true);
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    };

    void showContact(String nom,String numero){
        layoutNameNum.setVisibility(View.VISIBLE);
        if(nom == null)
            textNomContact.setText(getResources().getString(R.string.aucune_presonne));
        else
            textNomContact.setText(nom.toUpperCase());
        if(numero == null)
            textNumeroContact.setText(getResources().getString(R.string.aucun_numero));
        else
            textNumeroContact.setText(numero.toUpperCase());
    }

    @UiThread
    void showResultError(String message){
        hideLoader();
        showToast(message);
    }

    @Override
    public void onStop(){
        EventBus.getDefault().unregister(this);
        super.onStop();
        //Hide keyboard
        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e){
        }
    }

    /**  set etablissement contact */
    private void postSetContact(final int id, final String tel ,  final String name) {
        showLoader();
        new AsyncHelper<ContactResponseDto>() {
            @Override
            protected ContactResponseDto background() throws Exception {
                return contactSA.setEtablissmentContact(id, tel, name);
            }
            @Override
            protected void success(ContactResponseDto response) {
                hideLoader();
                if (response == null) {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                    return;
                }
                if (response.getHasError()) {
                    showErrorDialog(getString(R.string.information), getString(R.string.set_contact_error));
                    return;
                }
                if (response.getMessage().equalsIgnoreCase("success_mis_a_jour_etablissement")) {
                    //success
                    clearContactData();
                    showEtabDialog(getString(R.string.information), getString(R.string.set_contact_succes));
                } else {
                    showErrorDialog(getString(R.string.information), getString(R.string.set_contact_error));
                }
            }
        }.launch(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(EventSetListEtabDto event) {
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            showLoader();
            getListEtablissement();
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

    public boolean isFromMenu() {
        return fromMenu;
    }

    public void setFromMenu(boolean fromMenu) {
        this.fromMenu = fromMenu;
    }
}
