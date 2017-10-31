package fr.hdb.artibip.presentation.fragment.facturationfinale;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wang.avi.AVLoadingIndicatorView;
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
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.constantes.user.UserType;
import fr.hdb.artibip.donnee.dto.event.EventSetSignatureDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.photo.ListPhotoFragment;
import fr.hdb.artibip.presentation.fragment.photo.ListPhotoFragment_;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.artisan.facturation.FacturationSA;
import fr.hdb.artibip.service.applicatif.artisan.facturation.FacturationSAImpl;
import fr.hdb.artibip.service.applicatif.artisan.resumeintervention.ResumeInterventionSA;
import fr.hdb.artibip.service.applicatif.artisan.resumeintervention.ResumeInterventionSAImpl;
import fr.hdb.artibip.service.applicatif.image.ImageSA;
import fr.hdb.artibip.service.applicatif.image.ImageSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.url.EnvironmentManager;
import me.relex.circleindicator.CircleIndicator;
import static fr.hdb.artibip.presentation.activity.GenericActivity.getCurrentActivity;
import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.idInterventionArtisan;


@EFragment(R.layout.facturation_finale)
public class FacturationFragment extends GenericFragment {

    @ViewById(R.id.avi_loading_image)
    AVLoadingIndicatorView indicatorPicasso;

    @ViewById(R.id.view_bar_intervention)
    View viewBarIntervention;

    @ViewById(R.id.text_signature)
    TextView textSignature;

    @ViewById(R.id.lay_artisan)
    LinearLayout layArtisan;

    @ViewById(R.id.text_nom_artisan)
    TextView textNomArtisan;

    @ViewById(R.id.relative_layout_intervention)
    RelativeLayout relativeLayoutIntervention;

    @ViewById(R.id.view_bar_apres_intervention)
    View viewBarApresIntervention;

    @ViewById(R.id.relative_layout_apres_intervention)
    RelativeLayout relativeLayoutApresIntervention;

    @ViewById(R.id.plage_horaire)
    TextView txtPlageHoraire;

    @ViewById(R.id.duree_deplacement)
    TextView txtDureeDeplacement;

    @ViewById(R.id.duree_exacte)
    TextView txtDureeExact;

    @ViewById(R.id.prix_total_materiel)
    TextView prixTotalMateriel;

    @ViewById(R.id.cout_total_ht)
    TextView prixCoutTotalHt;

    @ViewById(R.id.txt_lebeele_tva)
    TextView libelleTva;

    @ViewById(R.id.cout_total_tva)
    TextView prixCoutTva;

    @ViewById(R.id.cout_total_ttc)
    TextView prixCoutTotalTtc;

    @ViewById(R.id.autre_intervention)
    TextView autreIntervention;

    @ViewById(R.id.commentaire)
    WebView commentaire;

    @ViewById(R.id.label_commentaire)
    TextView labelCommentaire;

    @ViewById(R.id.image_view_signature)
    protected ImageView imageViewSignature;

    @ViewById(R.id.label_photo)
    TextView labelPhoto;

    @ViewById(R.id.view_pager_photo)
    ViewPager viewPagerPhoto;

    @ViewById(R.id.indicator)
    CircleIndicator indicator;

    @ViewById(R.id.materiel)
    TextView materiel;

    @ViewById(R.id.label_rix_total_materiel)
    TextView labelPrixTotalMateriel;

    @ViewById(R.id.list_view_materiel)
    ListView listMateriel;

    @ViewById(R.id.button_send_facturation)
    Button buttonSendFacturation;

    @Bean(ResumeInterventionSAImpl.class)
    ResumeInterventionSA resumeInterventionSA;

    @Bean(ImageSAImpl.class)
    ImageSA imageSA;

    ListeMaterielFacturationAdapter listMaterielAdapter;
    ListMaterielResumeAdapter listMaterielResumeAdapter;
    private DemandeClientDto demandeClient;
    private CustomPagerAdapterFacture adapter;
    private List<ListPhotoFragment> listePhotot;
    private double coutTotalHt;
    private double coutTotalTtc;
    private double coutTva;
    private double coutDeplacement;
    private int duree;
    private int tauxHoraire;
    private double coutMateriel;
    private String signature;
    private String signatureBase64Init;
    private String signatureBase64Final;
    int tva;
    String plageHoraire;
    String dureeDeplacement;
    String dureeExact;
    double dureeintervention;

    @Bean(FacturationSAImpl.class)
    protected FacturationSA facturationSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    private boolean isFromMenu = true;
    private boolean isFacturationClient = false;

    @AfterViews
    void afterViews(){
        //Dessiné l'écran
        setApplicationDesgin();
    }

    @Click(R.id.image_view_signature)
    void onImageClicked() {
        if(!isStatutsTraiterArtisan() ){
            if(!isFacturationClient) {
                DialogManager.showSigning(getActivity());
            }
        }
    }

    /*
    * Dessiné l'écran
     */
    void setApplicationDesgin(){
        //Avec toolbar
        setToolbarVisibility(true,getString(R.string.bon_intervention),true);
        //Pas de header et footer
        setFooterVisibility(false);
        setHeaderVisibility(false, isFromMenu);

        switch (preferencesSA.getRole()){
            case UserType.CLIENT :
                buttonSendFacturation.setVisibility(View.GONE);
                break;
            case UserType.EMPLOYE:
                buttonSendFacturation.setVisibility(View.VISIBLE);
                break;
        }

        if (isFacturationClient) {
            buttonSendFacturation.setVisibility(View.GONE);
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                getFactureIntervention(demandeClient.getIdIntervention());
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        } else if(isStatutsTraiterArtisan()) {
            buttonSendFacturation.setVisibility(View.GONE);
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                getFactureIntervention(MainActivity.idInterventionArtisan);
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }else{
            if((BitmapDrawable) imageViewSignature.getDrawable()!=null) {
                BitmapDrawable drawable = (BitmapDrawable) imageViewSignature.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                signatureBase64Init= imageSA.getEncoded64ImageString(bitmap);
                signatureBase64Final=signatureBase64Init;
            }
            indicatorPicasso.setVisibility(View.GONE);
            ResumeInterventionResponseDto resume = new ResumeInterventionResponseDto();
            initPhotosfacture(resume);
            initChampsFacture(resume);
        }
        libelleTva.setText("TVA "+tva+"%");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(EventSetSignatureDto event) {
        if(event.signature!=null) {
            signatureBase64Final = imageSA.getEncoded64ImageString(event.signature);
            imageViewSignature.setImageBitmap(event.signature);
            FACTURATION_POST.setSignature(imageSA.getEncoded64ImageString(event.signature));
        }else{
            signatureBase64Final=signatureBase64Init;
            imageViewSignature.setImageDrawable(getResources().getDrawable(R.drawable.check));
        }
    }

    private void initPhotosfacture(ResumeInterventionResponseDto resume) {
        if(isStatutsTraiterArtisan() || isFacturationClient){
            if(resume.getIntervention().isAutreInterventionPlanifie()) {
                if (resume.getIntervention().getPhotosTraite()!=null) {
                    List<ListPhotoFragment_> photos = new ArrayList<>();
                    for (int i = 0; i < resume.getIntervention().getPhotosTraite().size(); i++) {
                        ListPhotoFragment_ listPhotoFragment = new ListPhotoFragment_();
                        listPhotoFragment.setUrl(resume.getIntervention().getPhotosTraite().get(i));
                        photos.add(listPhotoFragment);
                    }
                    adapter = new CustomPagerAdapterFacture(getChildFragmentManager(), photos);
                    viewPagerPhoto.setAdapter(adapter);
                    indicator.setViewPager(viewPagerPhoto);
                    viewPagerPhoto.setCurrentItem(0);
                }else{
                    labelPhoto.setVisibility(View.GONE);
                    viewPagerPhoto.setVisibility(View.GONE);
                    indicator.setVisibility(View.GONE);
                }
            }else{
                labelCommentaire.setVisibility(View.GONE);
                commentaire.setVisibility(View.GONE);
                labelPhoto.setVisibility(View.GONE);
                viewPagerPhoto.setVisibility(View.GONE);
            }
        }else{
            if (getBitmaps() != null) {
                List<ListPhotoFragment_> photos = new ArrayList<>();
                for (Bitmap bitmap: getBitmaps()) {
                    ListPhotoFragment_ photoFragment = new ListPhotoFragment_();
                    photoFragment.setClickable(false);
                    photoFragment.setBitmap(bitmap);
                    photos.add(photoFragment);
                }
                adapter = new CustomPagerAdapterFacture(getChildFragmentManager(), photos);
                viewPagerPhoto.setAdapter(adapter);
                indicator.setViewPager(viewPagerPhoto);
                viewPagerPhoto.setCurrentItem(0);
            }else{
                labelPhoto.setVisibility(View.GONE);
                viewPagerPhoto.setVisibility(View.GONE);
                indicator.setVisibility(View.GONE);
            }
        }
        ajustViewApresIntervention();
    }

    void initChampsArtisan(String artisanName){
        layArtisan.setVisibility(View.VISIBLE);
        textNomArtisan.setText(Html.fromHtml("<u>"+artisanName+"</u>"));
    }

    void initChampsFacture(ResumeInterventionResponseDto result){
        //Afficher les données liés à la déplacement
        initDepalcementLayout(result);
        //Affihcer les données liés à l'utilisation des matériaux
        initMaterietLayout(result);
        //Après intervention
        initAfterInterventinLayout(result);
        //Calculer le coût de l'intervention
        calculCoutTotal(result);
    }

    void initDepalcementLayout(ResumeInterventionResponseDto result){
        if(isStatutsTraiterArtisan() || isFacturationClient){
            txtPlageHoraire.setText(result.getIntervention().getPlageHoraire().toUpperCase());
            txtDureeDeplacement.setText(result.getIntervention().getDureeDeplacement());
        }else{
            txtPlageHoraire.setText(plageHoraire);
            txtDureeDeplacement.setText(dureeDeplacement);
        }
    }

    void initMaterietLayout(ResumeInterventionResponseDto result){

        if(isStatutsTraiterArtisan() || isFacturationClient){
                if(result.getIntervention().getMateriel_intervention().size()>0) {
                    setListViewMaterielHeight(result.getIntervention().getMateriel_intervention().size());
                    listMaterielResumeAdapter= new ListMaterielResumeAdapter(getContext(), result.getIntervention().getMateriel_intervention());
                    listMateriel.setAdapter(listMaterielResumeAdapter);
                    prixTotalMateriel.setText(round(result.getIntervention().getCoutMateriel(),2) + " € H.T");
                }else{
                    materiel.setVisibility(View.GONE);
                    listMateriel.setVisibility(View.GONE);
                    labelPrixTotalMateriel.setVisibility(View.GONE);
                    prixTotalMateriel.setVisibility(View.GONE);
                    coutMateriel = 0;
                }
        }else{
            if(FACTURATION_POST != null) {
                if(FACTURATION_POST.getListeMateriels()!=null){
                    if(FACTURATION_POST.getListeMateriels().size()>0) {
                        setListViewMaterielHeight(FACTURATION_POST.getListeMateriels().size());
                        prixTotalMateriel.setText(String.valueOf(FACTURATION_POST.getTotalMateriels()) + " € H.T");
                        listMaterielAdapter= new ListeMaterielFacturationAdapter(getContext(), FACTURATION_POST.getListeMateriels());
                        listMateriel.setAdapter(listMaterielAdapter);
                        coutMateriel = FACTURATION_POST.getTotalMateriels();
                    }else{
                        materiel.setVisibility(View.GONE);
                        listMateriel.setVisibility(View.GONE);
                        labelPrixTotalMateriel.setVisibility(View.GONE);
                        prixTotalMateriel.setVisibility(View.GONE);
                        coutMateriel = 0;
                    }
                }else{
                    materiel.setVisibility(View.GONE);
                    listMateriel.setVisibility(View.GONE);
                    labelPrixTotalMateriel.setVisibility(View.GONE);
                    prixTotalMateriel.setVisibility(View.GONE);
                    coutMateriel = 0;
                }
            }
        }
      ajustViewIntervention();
    }

    void initAfterInterventinLayout(ResumeInterventionResponseDto result){
        if(isStatutsTraiterArtisan() || isFacturationClient){
            if(result.getIntervention().isAutreInterventionPlanifie()){
                autreIntervention.setText("OUI");
                if(result.getIntervention().getCommentaireTraite()!=null) {
                    if(!TextUtils.isEmpty(result.getIntervention().getCommentaireTraite())) {
                        labelCommentaire.setVisibility(View.VISIBLE);
                        commentaire.setVisibility(View.VISIBLE);
                        makeJustify(result.getIntervention().getCommentaireTraite());
                    }else{
                        labelCommentaire.setVisibility(View.GONE);
                        commentaire.setVisibility(View.GONE);
                    }
                }
            }else {
                    autreIntervention.setText("NON");
                    makeJustify("");

            }
        }else{
            if(FACTURATION_POST != null){
                if(FACTURATION_POST.isAutreIntervention()) {
                    autreIntervention.setText("OUI");
                    if (!TextUtils.isEmpty(FACTURATION_POST.getCommentaires())){
                        labelCommentaire.setVisibility(View.VISIBLE);
                        commentaire.setVisibility(View.VISIBLE);
                        makeJustify(FACTURATION_POST.getCommentaires());
                    }else{
                        labelCommentaire.setVisibility(View.GONE);
                        commentaire.setVisibility(View.GONE);                    }
                }else{
                    autreIntervention.setText("NON");
                    makeJustify("");
                }
            }else{
                autreIntervention.setText("NON");
                makeJustify("");
            }
        }
    }

    void calculCoutTotal(ResumeInterventionResponseDto result) {
        //Afficher les données liés à l'intervention
        if (isStatutsTraiterArtisan() || isFacturationClient) {
            String artisanName;
            artisanName = result.getIntervention().getArrivee().getTechnicien().getPrenom();
            artisanName = artisanName+" "+result.getIntervention().getArrivee().getTechnicien().getNom();
            initChampsArtisan(artisanName.toUpperCase());
            txtDureeExact.setText(""+result.getIntervention().getDureeIntervention());
            prixCoutTotalHt.setText(round(result.getIntervention().getCoutTotalIntervention(),2)+" € H.T");
            libelleTva.setText("TVA  "+ result.getIntervention().getValueTva()+ " %");
            prixCoutTva.setText(round((result.getIntervention().getCoutTotalIntervention()*result.getIntervention().getValueTva()/100),2) +" € H.T");
            prixCoutTotalTtc.setText(round(result.getIntervention().getCoutTotalTTC(),2)+" € TTC");
        } else {
            layArtisan.setVisibility(View.GONE);
            txtDureeExact.setText(dureeExact);
            if(dureeintervention <= 60) {
                coutTotalHt = tauxHoraire + coutDeplacement + coutMateriel;
            } else {
                coutTotalHt = ((dureeintervention * tauxHoraire) /60) + coutDeplacement + coutMateriel;
            }

            prixCoutTotalHt.setText(round(coutTotalHt,2) + " €");
            coutTva = (tva * coutTotalHt) / 100;
            prixCoutTva.setText(round(coutTva,2) + " €");
            coutTotalTtc = coutTotalHt + coutTva;
            prixCoutTotalTtc.setText(round(coutTotalTtc,2) + " €");
            if (FACTURATION_POST != null) {
                FACTURATION_POST.setIdIntervention(idInterventionArtisan);
                FACTURATION_POST.setCoutTotal(coutTotalHt);
                FACTURATION_POST.setTva(tva);
            } else {
                showErrorDialog(getString(R.string.information), "DONNEES MANQUANTES :-( !");
            }
        }
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    void setListViewMaterielHeight(int count) {
        ViewGroup.LayoutParams params = listMateriel.getLayoutParams();
        if (EnvironmentManager.isTablet(getCurrentActivity())) {
            params.height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90 * count, getResources().getDisplayMetrics());
        }
        else {
            params.height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50 * count, getResources().getDisplayMetrics());
        }
       //ajout marge
        params.height= params.height + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        listMateriel.setLayoutParams(params);
        listMateriel.requestFocus();
    }

    public void ajustViewIntervention(){
        ViewTreeObserver vto = relativeLayoutIntervention.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayoutIntervention.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = relativeLayoutIntervention.getMeasuredHeight();
                ViewGroup.LayoutParams pv2 = viewBarIntervention.getLayoutParams();
                pv2.height= height;
                viewBarIntervention.setLayoutParams(pv2);
            }
        });
    }

    public void ajustViewApresIntervention(){
        ViewTreeObserver vto2 = relativeLayoutApresIntervention.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayoutApresIntervention.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = relativeLayoutApresIntervention.getMeasuredHeight();
                ViewGroup.LayoutParams pv2 = viewBarApresIntervention.getLayoutParams();
                pv2.height= height;
                viewBarApresIntervention.setLayoutParams(pv2);
            }
        });
    }

    @Click(R.id.button_send_facturation)
    void onClikcValidate(){
        if(!signatureBase64Init.equals(signatureBase64Final)) {
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                showLoader();
                sendFacturation();
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }else{
            showErrorDialog(getString(R.string.information), getString(R.string.signature_obligation));
        }
    }

    /**
     * Ajout des éléments pour la facturation
     */
    @Background
    void sendFacturation(){
        ResponseDto result = facturationSA.sendFacturation(FACTURATION_POST);
        hideLoader();
        if(result == null){
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            return;
        }
        if(!result.isHasError()){
            Log.e("FACTURATION",FACTURATION_POST.toString());
            goToHome(result.getMessage());
        } else {
            if(result.getMessage() != null){
                showErrorDialog(getString(R.string.information), result.getMessage().toUpperCase().replace("_"," "));
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            }
        }
    }

    @UiThread
    void goToHome(String message){
        FACTURATION_POST = null;
        MainActivity.listFragment = null;
        showMenuDialog(getString(R.string.information), getString(R.string.facturation_envoyee));
    }

    @Background
    public void getFactureIntervention(int idIntervention){
        ResumeInterventionResponseDto result= resumeInterventionSA.getResumeIntervention(idIntervention);
        boolean resultNull= false;
        if (result == null) {
            hideLoader();
            resultNull=true;
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            if(resultNull){
                pop(false);
            }
        } else {
            if (!result.isHasError()) {
                   hideLoader();
                   initFacturationPost(result);
            } else {
                hideLoader();
                resultNull=true;
                showErrorDialog(getString(R.string.information), getString(R.string.demande_error));
                if(resultNull){
                    pop(false);
                }
            }
        }
    }

    @UiThread
    public void initFacturationPost(ResumeInterventionResponseDto result){
        textSignature.setText(getString(R.string.text_signature_traiter));
        try{
            if(result.getIntervention().getCheminSignature() != null) {
                PicassoUtils.startLoadingImage(getContext()
                        ,result.getIntervention().getCheminSignature()
                        ,imageViewSignature
                        ,indicatorPicasso
                );
            } else {
                indicatorPicasso.setVisibility(View.GONE);
            }
            //holder.image.setImageResource(mThumbIds[position]);
        } catch (Exception e){
            indicatorPicasso.setVisibility(View.GONE);
        }
        initPhotosfacture(result);
        initChampsFacture(result);
    }

    /** FACTURE CLIENT */
    private void showFactureClient(DemandeClientDto demandeClient) {

        txtPlageHoraire.setText(showEmptyIfNull(demandeClient.getPlageHoraire()));
        txtDureeDeplacement.setText(showEmptyIfNull(demandeClient.getDureeDeplacement()));
        txtDureeExact.setText(showEmptyIfNull("" + demandeClient.getDureeIntervention()));
        //listView
        prixTotalMateriel.setText(showEmptyIfNull("" + demandeClient.getCoutMateriel()) + " €");
        prixCoutTotalHt.setText(showEmptyIfNull("" + demandeClient.getCoutTotalTtc()) + " €");
        libelleTva.setText("TVA "+demandeClient.getValueTva()+"%");
        prixCoutTotalTtc.setText(showEmptyIfNull("" + demandeClient.getCoutTotalIntervention())+ " €");
        //signature
        getSignature(demandeClient.getCheminSignature());
        //autre intervention
        autreIntervention.setText(demandeClient.isAutreInterventionPlanifie() == true ?"OUI": "NON");

    }

    private String showEmptyIfNull(String input) {
        return input == null ? "" : input;
    }

    @UiThread
    protected void getSignature(String path){
        try{
            PicassoUtils.startLoadingImage(getContext()
                    ,path
                    ,imageViewSignature
                    ,indicatorPicasso
            );
        } catch (Exception e){
            indicatorPicasso.setVisibility(View.GONE);
        }
    }

    public void makeJustify(String texte){
        String text;
        String sizeTexte;
        if (EnvironmentManager.isTablet(getActivity())) {
            sizeTexte = "x-large";
        }
        else {
            sizeTexte = "small";
        }
        texte = texte.replace("\n","</br>");
        text = "<html><head><style type=\"text/css\">" +
                "body{color: #FFFFFF; background-color: #000000; font-size :"+sizeTexte+"; }" +
                "</style></head><body><p align=\"justify\">";
        text+= texte;
        text+= "</p></body></html>";
        commentaire.setBackgroundColor(Color.TRANSPARENT);
        commentaire.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        commentaire.setLayerType(WebView.LAYER_TYPE_NONE, null);
        commentaire.loadDataWithBaseURL(null, text, "text/html", null, null);
        WebSettings settings = commentaire.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        commentaire.setWebChromeClient(new WebChromeClient());
        commentaire.setWebViewClient(new WebViewClient());
    }

    public View getViewBarIntervention() {
        return viewBarIntervention;
    }
    public void setViewBarIntervention(View viewBarIntervention) {
        this.viewBarIntervention = viewBarIntervention;
    }
    public int getTva() {
        return tva;
    }
    public void setTva(int tva) {
        this.tva = tva;
    }
    public String getPlageHoraire() {
        return plageHoraire;
    }
    public void setPlageHoraire(String plageHoraire) {
        this.plageHoraire = plageHoraire;
    }
    public String getDureeExact() {
        return dureeExact;
    }
    public void setDureeExact(String dureeExact) {
        this.dureeExact = dureeExact;
    }
    public double getDureeintervention() {
        return dureeintervention;
    }
    public void setDureeintervention(double dureeintervention) {
        this.dureeintervention = dureeintervention;
    }
    public double getCoutDeplacement() {
        return coutDeplacement;
    }
    public void setCoutDeplacement(double coutDeplacement) {
        this.coutDeplacement = coutDeplacement;
    }
    public int getTauxHoraire() {
        return tauxHoraire;
    }
    public void setTauxHoraire(int tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }
    public double getCoutTotalHt() {
        return coutTotalHt;
    }
    public void setCoutTotalHt(double coutTotalHt) {
        this.coutTotalHt = coutTotalHt;
    }
    public double getCoutTotalTtc() {
        return coutTotalTtc;
    }
    public void setCoutTotalTtc(double coutTotalTtc) {
        this.coutTotalTtc = coutTotalTtc;
    }
    public double getCoutTva() {
        return coutTva;
    }
    public void setCoutTva(double coutTva) {
        this.coutTva = coutTva;
    }
    public double getCoutMateriel() {
        return coutMateriel;
    }
    public void setCoutMateriel(double coutMateriel) {
        this.coutMateriel = coutMateriel;
    }
    public String getDureeDeplacement() {
        return dureeDeplacement;
    }
    public void setDureeDeplacement(String dureeDeplacement) {
        this.dureeDeplacement = dureeDeplacement;
    }
    public boolean isFromMenu() {
        return isFromMenu;
    }
    public void setFromMenu(boolean fromMenu) {
        isFromMenu = fromMenu;
    }
    public boolean isFacturationClient() {
        return isFacturationClient;
    }
    public void setFacturationClient(boolean facturationClient) {
        isFacturationClient = facturationClient;
    }
    public DemandeClientDto getDemandeClient() {
        return demandeClient;
    }
    public void setDemandeClient(DemandeClientDto demandeClient) {
        this.demandeClient = demandeClient;
    }


}
