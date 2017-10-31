package fr.hdb.artibip.presentation.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.commun.constantes.Constante;
import fr.hdb.artibip.commun.constantes.Pager;
import fr.hdb.artibip.commun.constantes.application.DClicElecApplication;
import fr.hdb.artibip.donnee.dto.DemandeArtisanDto;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.ForfaitDeplacementDto;
import fr.hdb.artibip.donnee.dto.PlageHoraireDto;
import fr.hdb.artibip.donnee.dto.TauxHoraireDto;
import fr.hdb.artibip.donnee.dto.constantes.Strings;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.donnee.dto.constantes.popup.PopUpType;
import fr.hdb.artibip.donnee.dto.constantes.push.Notification;
import fr.hdb.artibip.donnee.dto.constantes.user.UserType;
import fr.hdb.artibip.donnee.dto.event.EventSetMaterielDto;
import fr.hdb.artibip.donnee.dto.event.EventSetPhotoDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.FacturationDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.MaterielDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.common.deconnexion.DeconnexionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.post.InterventionPostDto;
import fr.hdb.artibip.donnee.dto.ws.post.cgv.CgvDto;
import fr.hdb.artibip.donnee.dto.ws.response.ArtisanDemandeResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.ClientDemandeResponseDto;
import fr.hdb.artibip.presentation.fragment.PagerAdapter;
import fr.hdb.artibip.presentation.fragment.artisan.interventiondetail.DemandeAvantAcceptationFragment_;
import fr.hdb.artibip.presentation.fragment.artisan.interventiondetail.ListPhotoResumeFragment;
import fr.hdb.artibip.presentation.fragment.artisan.interventionliste.ArtisanDemandeListFragment_;
import fr.hdb.artibip.presentation.fragment.client.BesoinFragment_;
import fr.hdb.artibip.presentation.fragment.client.ConditionFragment_;
import fr.hdb.artibip.presentation.fragment.client.demandeliste.ClientDemandeFragment_;
import fr.hdb.artibip.presentation.fragment.client.statuts.EnCoursFragment_;
import fr.hdb.artibip.presentation.fragment.client.statuts.NegativeFragment_;
import fr.hdb.artibip.presentation.fragment.client.statuts.PositiveFragment_;
import fr.hdb.artibip.presentation.fragment.menu.DrawerFragment_;
import fr.hdb.artibip.presentation.fragment.photo.CustomPagerAdapter;
import fr.hdb.artibip.presentation.fragment.photo.ListPhotoFragment;
import fr.hdb.artibip.presentation.fragment.user.ModificationInfoGeneraleFragment_;
import fr.hdb.artibip.presentation.fragment.user.passwordinfo.PasswordChangeFragment_;
import fr.hdb.artibip.presentation.view.TypefaceUtils;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.artisan.listedemande.ArtisanDemandeSA;
import fr.hdb.artibip.service.applicatif.artisan.listedemande.ArtisanDemandeSAImpl;
import fr.hdb.artibip.service.applicatif.client.cgv.CgvSA;
import fr.hdb.artibip.service.applicatif.client.cgv.CgvSAImpl;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSA;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSAImpl;
import fr.hdb.artibip.service.applicatif.common.deconnexion.DeconnexionSA;
import fr.hdb.artibip.service.applicatif.common.deconnexion.DeconnexionSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;
import me.relex.circleindicator.CircleIndicator;

@WindowFeature(Window.FEATURE_ACTION_BAR)
@EActivity(R.layout.activity_main)
public class MainActivity extends GenericActivity {

    public static PagerAdapter clientPagerAdapter;
    public static int PAGE_CURRENT = 0;
    public static int ID_STATUS_DEMANDE = -1;
    public static List<ListPhotoFragment> listFragment;
    public static List<ListPhotoResumeFragment> lf;
    public static List<MaterielDto> listMateriel;
    public static CustomPagerAdapter photoAdapter;
    public InterventionPostDto interventionPost;
    public static int idInterventionArtisan;
    private boolean statusTraiterArtisan = false;
    private boolean statusTermineeClient = false;
    private String numConseiller;
    public static FacturationDto FACTURATION_POST;
    private TauxHoraireDto tauxHoraire = new TauxHoraireDto();
    private ForfaitDeplacementDto forfait;
    private PlageHoraireDto horaire;
    private DClicElecApplication dClicElecApplication;
    private String registerId = Strings.EMPTY;
    private boolean backToDetail = false;
    private ResumeInterventionResponseDto resumeInterventionResponseDto = new ResumeInterventionResponseDto();

    /**
     * DEMANDES CLIENT
     */
    private List<DemandeClientDto> tousDesDemandesDto;
    private DemandeClientDto demandeEnCoursDto;
    private String imageEncours;
    private String imageRefuse;
    @Bean(ClientDemandeSAImpl.class)
    protected ClientDemandeSA clientDemandeSA;

    /**
     * DEMANDES ARTISAN
     */
    private List<DemandeArtisanDto> demandesArtisan;
    private DemandeArtisanDto demandeEnCoursArtisan;
    @Bean(ArtisanDemandeSAImpl.class)
    ArtisanDemandeSA artisanDemandeSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @Bean(CgvSAImpl.class)
    protected CgvSA cgvSA;

    @ViewById(R.id.pager_fragment_client)
    public static ViewPager clientPager;

    @ViewById(R.id.drawer_layout)
    public DrawerLayout drawer;

    @Bean(DeconnexionSAImpl.class)
    protected DeconnexionSA deconnexionSA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getBoolean(Notification.AFTER_NOTIFICATION)) {
                setIdIntervention(bundle.getInt(Notification.ID_INTERVENTION));
            }
        }
    }

    @AfterViews
    protected void afterViews() {
        setScreenOrientation();
        TypefaceUtils.overrideFont(this);
        lf = new ArrayList<ListPhotoResumeFragment>();
        initMenuLeft();
        iniRedirection();
        /*initMenuLeft((getIdIntervention() != Constante.DEFAULT_INTERVENTION_ID
                || !TextUtils.isEmpty(getPassword())), getPassword());*/
    }

    void iniRedirection(){
        boolean isPass = false;
        boolean isSms = false;
        if(getIdIntervention() != Constante.DEFAULT_INTERVENTION_ID) {
            isPass = true;
        } else {
            if(!TextUtils.isEmpty(getPassword())){
                isSms = true;
            }
        }
        chooseScreen(isPass,isSms);
    }

    @UiThread
    protected void chooseScreen(boolean isPass,boolean isSms) {
        String userRole = preferences.getRole();
        if (isPass) {
            showInfo(getPassword());
            reset();
        } else {
            if(isSms){
                switch (userRole) {
                    case UserType.CLIENT:
                        getDemandeCouranteClient(true);
                        break;
                    case UserType.EMPLOYE:
                        getDemandeCouranteArtisan(true);
                        break;
                    default:
                        break;
                }
                reset();
            } else {
                showDemandeList(userRole);
            }
        }
    }

/*    void initRedirection() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getBoolean(Notification.AFTER_NOTIFICATION)) {
                int idIntervention = bundle.getInt(Notification.ID_INTERVENTION);

                idInterventionArtisan = idIntervention;
                chooseScreen(true, false, "");
            }
        } else {
            checkData();
        }
    }

    /*
    * Verification des données avant lancement de l'application
     */
 /*   public void checkData() {
        boolean isSms = false;
        boolean isPassword = false;
        dClicElecApplication = (DClicElecApplication) getApplication();
        Uri data = dClicElecApplication.getSms();
        dClicElecApplication.setSms(null);
        String password = "";
        if (data != null && !TextUtils.isEmpty(data.toString())) {
            String link = data.toString();
            //password or intervention
            link = link.replace(Sms.SMS_LINK, Strings.EMPTY);

            if (link.startsWith(Sms.SMS_PASSWORD)) {
                isPassword = true;
                password = link.replace(Sms.SMS_PASSWORD, Strings.EMPTY);
            } else {
                try {
                    idInterventionArtisan = Integer.parseInt(link);
                } catch (Exception e) {
                    idInterventionArtisan = Integer.parseInt(
                            link.substring(link.lastIndexOf("/"))
                    );
                }
                isSms = true;
            }
        }
        chooseScreen(isSms, isPassword, password);
    }*/

    public void initMenuLeft() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //Initialisation du Menu
        DrawerFragment_ drawerFragment = new DrawerFragment_();
        addFragment(R.id.left_drawer, drawerFragment, false, false);
    }

    public void showInfo(String password){
        clearBackStack();
        if (preferences.getRole().equalsIgnoreCase(UserType.CLIENT)) {
            replaceFragment(R.id.main_container,new ModificationInfoGeneraleFragment_(),false,false);
        }else{
            PasswordChangeFragment_ passwordChangeFragment = new PasswordChangeFragment_();
            passwordChangeFragment.setResetPassword(!TextUtils.isEmpty(password));
            passwordChangeFragment.setOldPassword(password);
            replaceFragment(R.id.main_container,passwordChangeFragment,true,false);
        }
    }

    public void showDemandeList(String role){
        showLoader();
        if(role.equalsIgnoreCase(UserType.CLIENT)) {
            ClientDemandeFragment_ clientDemandeFragment_ = new ClientDemandeFragment_();
            replaceFragment(R.id.main_container,clientDemandeFragment_,true,false);
        } else {
            ArtisanDemandeListFragment_ artisanDemandeListFragment_ = new ArtisanDemandeListFragment_();
            replaceFragment(R.id.main_container,artisanDemandeListFragment_,true,false);
        }
    }

    @Background
    public void getDemandeCouranteClient(boolean isFromData) {
        ClientDemandeResponseDto result = clientDemandeSA.getClientDemande();
        hideLoader();
        if(result == null) {
            showErrorDialog(getString(R.string.information),getString(R.string.error_ws));
            setTousDesDemandesDto(null);
            replaceFragment(R.id.main_container,new BesoinFragment_(),false,true);
        } else {
            if (!result.isHasError()) {
                //image à afficher selon le statut de la news
                setImageEncours(result.getImageEncours());
                setNumConseiller(result.getNumeroConseiller());
                setImageRefuse(result.getImageRefuse());
                showDemandeCouranteClient(result.getListDemande(),isFromData);
            }else {
                setTousDesDemandesDto(null);
                showErrorDialog(getString(R.string.information),result.getMessage().toUpperCase().replace("_"," "));
                replaceFragment(R.id.main_container,new BesoinFragment_(),false,true);
            }
        }
    }

    @UiThread
    public void showDemandeCouranteClient(List<DemandeClientDto> demandesClientDto,boolean isFromData){
        if (demandesClientDto == null) {
            setTousDesDemandesDto(null);
            showErrorDialog(getString(R.string.information), getString(R.string.aucune_demande_client));
            replaceFragment(R.id.main_container,new BesoinFragment_(),false,true);
        } else {
            setTousDesDemandesDto(demandesClientDto);
            DemandeClientDto demande = null;
            if (isFromData) {
                // SMS
                for (DemandeClientDto demandeClientDto : getTousDesDemandesDto()) {
                    if (demandeClientDto.getIdIntervention() == getIdIntervention()) {
                        demande = demandeClientDto;
                    }
                }
            } else {
                demande = getDemandeClientParStatut(demandesClientDto);
            }

            if (demande == null) {
                showErrorDialog(getString(R.string.information), getString(R.string.aucune_demande_artisan));
                replaceFragment(R.id.main_container,new ClientDemandeFragment_(),false,true);
            } else {
                setDemandeEnCoursDto(demande);
                switch(demande.getEtatIntervention()) {
                    case DemandeStatus.ID_ENVOYEE:
                        EnCoursFragment_ enCoursFragment = new EnCoursFragment_();
                        enCoursFragment.setDemande(demande);
                        replaceFragment(R.id.main_container,enCoursFragment,false,true);
                        break;

                    case DemandeStatus.ID_EN_COURS:
                        PositiveFragment_ positiveFragment = new PositiveFragment_();
                        positiveFragment.setDemande(demande);
                        replaceFragment(R.id.main_container,positiveFragment,false,true);
                        break;

                    case DemandeStatus.ID_ANNULEE:
                        NegativeFragment_ negativeFragment = new NegativeFragment_();
                        negativeFragment.setDemande(demande);
                        replaceFragment(R.id.main_container,negativeFragment,false,true);
                        break;

                    default:
                        replaceFragment(R.id.main_container,new BesoinFragment_(),false,true);
                        break;
                }
            }
        }
    }

    public DemandeClientDto getDemandeClientParStatut(List<DemandeClientDto> demandeClientDtos) {
        for (DemandeClientDto demandeClientDto : demandeClientDtos) {
            if (demandeClientDto.getEtatIntervention()  != DemandeStatus.ID_TERMINEE) {
                return demandeClientDto;
            }
        }
        return null;
    }

    @Background
    public void getDemandeCouranteArtisan(boolean isFromData) {
        ArtisanDemandeResponseDto result = artisanDemandeSA.getListDemande();
        if(result == null) {
            hideLoader();
            setDemandesArtisan(null);
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            replaceFragment(R.id.main_container,new ArtisanDemandeListFragment_(),false,true);
        } else {
            if (!result.isHasError()) {
                showDemandeEnCoursArtisan(result.getListDemande(),isFromData);
            } else {
                hideLoader();
                setDemandesArtisan(null);
                showErrorDialog(getString(R.string.information), result.getMessage().toUpperCase().replace("_"," "));
                replaceFragment(R.id.main_container,new ArtisanDemandeListFragment_(),false,true);
            }
        }
    }

    @UiThread
    public void showDemandeEnCoursArtisan(List<DemandeArtisanDto> demandesArtisanDto,boolean isFromData){
        if (demandesArtisanDto == null) {
            hideLoader();
            setDemandesArtisan(null);
            showErrorDialog(getString(R.string.information), getString(R.string.aucune_demande_artisan));
            replaceFragment(R.id.main_container,new ArtisanDemandeListFragment_(),false,true);
        } else {
            setDemandesArtisan(demandesArtisanDto);
            DemandeArtisanDto demande = null;
            if (isFromData) {
                // SMS
                for (DemandeArtisanDto demandeArtisanDto : getDemandesArtisan()) {
                    if (demandeArtisanDto.getIdDemande() == getIdIntervention()) {
                        demande = demandeArtisanDto;
                    }
                }
            } else {
                demande = getDemandeArtisanParStatut(demandesArtisanDto);
            }

            if (demande == null) {
                hideLoader();
                showErrorDialog(getString(R.string.information), getString(R.string.aucune_demande_artisan));
                replaceFragment(R.id.main_container,new ArtisanDemandeListFragment_(),false,true);
            } else {
                setDemandeEnCoursArtisan(demande);
                DemandeAvantAcceptationFragment_ demandeAvantAcceptationFragment = new DemandeAvantAcceptationFragment_();
                setBackToDetail(false);
                idInterventionArtisan = demande.getIdDemande();
                demandeAvantAcceptationFragment.setDemandeArtisan(demande);
                replaceFragment(R.id.main_container,demandeAvantAcceptationFragment,false,true);
            }
        }
    }

    public DemandeArtisanDto getDemandeArtisanParStatut(List<DemandeArtisanDto> demandeArtisanDtos) {
        for (DemandeArtisanDto demandeArtisanDto : demandeArtisanDtos) {
            if (demandeArtisanDto.getStatus() == DemandeStatus.ID_ENVOYEE) {
                setIdIntervention(demandeArtisanDto.getIdDemande());
                return demandeArtisanDto;
            }
        }
        return null;
    }

    public void getCgv() {
        showLoader();
        new AsyncHelper<CgvDto>() {

            @Override
            protected CgvDto background() throws Exception {
                return cgvSA.getCgv();
            }

            @Override
            protected void success(CgvDto sended) {
                hideLoader();
                if (sended != null) {
                    if (!sended.getHasError()) {
                        if (sended.getListCgv().size() > 0) {
                            ConditionFragment_ conditionFragment = new ConditionFragment_();
                            conditionFragment.setCgvDto(sended);
                            replaceFragment(R.id.main_container, conditionFragment, true, true);
                        } else {
                            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                        }
                    } else {
                        showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                    }
                } else {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                }
            }
        }.launch(this);
    }

    public void fillPager() {
        if (preferences.getRole().equalsIgnoreCase(UserType.CLIENT))
            clientPagerAdapter = new PagerAdapter(getSupportFragmentManager(), Pager.PAGE_CLIENT_COUNT);
        else
            clientPagerAdapter = new PagerAdapter(getSupportFragmentManager(), Pager.PAGE_EMPLOYE_COUNT);

        clientPager.setAdapter(clientPagerAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(clientPager);

        PAGE_CURRENT = 0;
        clientPager.setCurrentItem(PAGE_CURRENT, true);
    }

    @UiThread
    public void showCustomDialog(String title, String message, PopUpType popUpType, final int position) {
        String rightCaption = getString(R.string.ok);
        View.OnClickListener rightButtonListener = null;
        View.OnClickListener leftButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getCustomDialog().dismiss();
            }
        };
        if (popUpType == PopUpType.DELETE_PHOTO) {
            rightCaption = getString(R.string.supprimer);
            rightButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventSetPhotoDto(position));
                    DialogManager.getCustomDialog().dismiss();
                }
            };

        } else if (popUpType == PopUpType.OK_CANCEL) {
            rightCaption = getString(R.string.ok);
            rightButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.getCustomDialog().dismiss();
                }
            };

        } else if(popUpType == PopUpType.DECONNEXION) {
            rightCaption = getString(R.string.ok);
            rightButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.getCustomDialog().dismiss();
                    deconnect();
                }
            };

        }
        else if(popUpType == PopUpType.DELETE_MATERIEL) {
            rightCaption = getString(R.string.supprimer);
            rightButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventSetMaterielDto(position));
                    DialogManager.getCustomDialog().dismiss();
                }
            };
        }

        else if(popUpType == PopUpType.LOCATION) {
            rightCaption = getString(R.string.ok);
            rightButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    DialogManager.getCustomDialog().dismiss();
                }
            };

        }
        DialogManager.showMessageCustom(this, title, message, getString(R.string.annuler), rightCaption, leftButtonListener, rightButtonListener);
    }

    public void deconnect() {
        showLoader();
        new AsyncHelper<DeconnexionResponseDto>() {
            @Override
            protected DeconnexionResponseDto background() throws Exception {
                return deconnexionSA.deconnect();
            }

            @Override
            protected void success(DeconnexionResponseDto sended) {
                hideLoader();
                if (sended == null) {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                    return;
                }
                if (!sended.isHasError()) {
                    if (sended.getError().equalsIgnoreCase("user_deconnected")) {
                        // clear
                        clearAllDatas();
                    } else {
                        showErrorDialog(getString(R.string.information), getString(R.string.deconnect_error));
                    }
                } else {
                    if (sended.getError().equalsIgnoreCase("pas_userconnection")) {
                        clearAllDatas();
                    } else {
                        showErrorDialog(getString(R.string.information), sended.getError().toUpperCase().replace("_", " "));
                    }
                }

            }
        }.launch(this);
    }

    void clearAllDatas() {
        preferences.clearAll();
        registerId = "";
        ID_STATUS_DEMANDE = -1;
        listFragment = null;
        lf = null;
        listMateriel = null;
        photoAdapter = null;
        idInterventionArtisan = idDemande = Constante.DEFAULT_INTERVENTION_ID;
        bitmaps = null;
        tousDesDemandesDto = null;
        demandeEnCoursDto = null;
        tauxHoraire = null;
        forfait = null;
        horaire = null;
        registerId = null;
        Intent intent = new Intent(this, AccueilActivity_.class);
        this.startActivity(intent);
        finish();
    }

    /**
     * Marshmallow's permission validator
     */
    public void marshmallowPemissionChecker(String persmission, int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && this.checkSelfPermission(
                persmission) != PackageManager.PERMISSION_GRANTED
                && this.checkSelfPermission(
                persmission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{persmission}, type);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int num = 0;
            if (preferences.getToken() != null && !TextUtils.isEmpty(preferences.getToken())) {
                num = 1;
            }
            popBackStackFragment(num);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // SMS
        if(getIdIntervention() != Constante.DEFAULT_INTERVENTION_ID || !TextUtils.isEmpty(getPassword())) {
            afterViews();
        }
    }


    private List<Bitmap> bitmaps;

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public TauxHoraireDto getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(TauxHoraireDto tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public ForfaitDeplacementDto getForfait() {
        return forfait;
    }

    public void setForfait(ForfaitDeplacementDto forfait) {
        this.forfait = forfait;
    }

    public PlageHoraireDto getHoraire() {
        return horaire;
    }

    public void setHoraire(PlageHoraireDto horaire) {
        this.horaire = horaire;
    }

    public List<DemandeClientDto> getTousDesDemandesDto() {
        return tousDesDemandesDto;
    }

    public void setTousDesDemandesDto(List<DemandeClientDto> tousDesDemandesDto) {
        this.tousDesDemandesDto = tousDesDemandesDto;
    }

    public DemandeClientDto getDemandeEnCoursDto() {
        return demandeEnCoursDto;
    }

    public void setDemandeEnCoursDto(DemandeClientDto demandeEnCoursDto) {
        this.demandeEnCoursDto = demandeEnCoursDto;
    }

    private int idDemande;

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public String getImageEncours() {
        return imageEncours;
    }

    public void setImageEncours(String imageEncours) {
        this.imageEncours = imageEncours;
    }

    public String getImageRefuse() {
        return imageRefuse;
    }

    public void setImageRefuse(String imageRefuse) {
        this.imageRefuse = imageRefuse;
    }

    public List<DemandeArtisanDto> getDemandesArtisan() {
        return demandesArtisan;
    }

    public void setDemandesArtisan(List<DemandeArtisanDto> demandesArtisan) {
        this.demandesArtisan = demandesArtisan;
    }

    public DemandeArtisanDto getDemandeEnCoursArtisan() {
        return demandeEnCoursArtisan;
    }

    public void setDemandeEnCoursArtisan(DemandeArtisanDto demandeEnCoursArtisan) {
        this.demandeEnCoursArtisan = demandeEnCoursArtisan;
    }

    public boolean isStatusTraiterArtisan() {
        return this.statusTraiterArtisan;
    }

    public void setStatusTraiterArtisan(boolean statusTraiterArtisan) {
        this.statusTraiterArtisan = statusTraiterArtisan;
    }

    public boolean isBackToDetail() {
        return backToDetail;
    }

    public void setBackToDetail(boolean backToDetail) {
        this.backToDetail = backToDetail;
    }

    public boolean isStatutsTraiterArtisan() {
        return statusTraiterArtisan;
    }

    public void setStatutsTraiterArtisan(boolean statuts) {
        this.statusTraiterArtisan = statuts;
    }

    public boolean isStatusTermineeClient() {
        return statusTermineeClient;
    }

    public void setStatusTermineeClient(boolean statusTermineeClient) {
        this.statusTermineeClient = statusTermineeClient;
    }

    public ResumeInterventionResponseDto getResumeInterventionResponseDto() {
        return resumeInterventionResponseDto;
    }

    public void setResumeInterventionResponseDto(ResumeInterventionResponseDto resumeInterventionResponseDto) {
        this.resumeInterventionResponseDto = resumeInterventionResponseDto;
    }

    public InterventionPostDto getInterventionPost() {
        return interventionPost;
    }

    public void setInterventionPost(InterventionPostDto interventionPost) {
        this.interventionPost = interventionPost;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getNumConseiller() {
        return numConseiller;
    }

    public void setNumConseiller(String numConseiller) {
        this.numConseiller = numConseiller;
    }

}
