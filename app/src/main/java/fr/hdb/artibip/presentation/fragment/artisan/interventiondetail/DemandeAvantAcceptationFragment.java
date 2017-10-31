package fr.hdb.artibip.presentation.fragment.artisan.interventiondetail;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import org.androidannotations.annotations.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeArtisanDto;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.donnee.dto.constantes.intervention.InterventionType;
import fr.hdb.artibip.donnee.dto.constantes.menu.Menu;
import fr.hdb.artibip.donnee.dto.constantes.permission.Permission;
import fr.hdb.artibip.donnee.dto.ws.artisan.intervention.refus.RefusInterventionResponseDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.EtablissementInfoDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.menu.DrawerFragment;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.artisan.apresacceptation.DemandeApresAcceptationFragment_;
import fr.hdb.artibip.presentation.fragment.artisan.interventionliste.ArtisanDemandeListFragment_;
import fr.hdb.artibip.presentation.view.NavDrawerItem;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation.RefusInterventionSA;
import fr.hdb.artibip.service.applicatif.artisan.intervention.refusvalidation.RefusInterventionSAImpl;
import fr.hdb.artibip.service.applicatif.artisan.resumeintervention.ResumeInterventionSA;
import fr.hdb.artibip.service.applicatif.artisan.resumeintervention.ResumeInterventionSAImpl;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;
import fr.hdb.artibip.utils.network.NetworkManager;
import me.relex.circleindicator.CircleIndicator;

@EFragment(R.layout.detail_demande_avant_acceptation_artisan)
public class DemandeAvantAcceptationFragment extends GenericFragment {

    @ViewById(R.id.text_type)
    TextView type;

    @ViewById(R.id.text_nature)
    TextView nature;

    @ViewById(R.id.text_detail)
    TextView detail;

    @ViewById(R.id.text_nom_etablissement_artisan)
    TextView nomEtablissement;

    @ViewById(R.id.text_adresse_etablissement)
    TextView addresseEtablissement;

    @ViewById(R.id.text_nom_chef_etablissement)
    TextView nomChefEtablissement;

    @ViewById(R.id.text_tel_chef_etablissement)
    TextView numChefEtablissement;

    @ViewById(R.id.layout_button_answer)
    LinearLayout layoutButtonAnswer;

    DemandeArtisanDto demandeArtisan;

    private boolean userRefused;

    @ViewById(R.id.text_commentaire)
    TextView commentaire;

    @ViewById(R.id.view_pager_photo)
    ViewPager viewPagerPhoto;

    @ViewById(R.id.indicator)
    CircleIndicator indicator;

    @Bean(RefusInterventionSAImpl.class)
    protected RefusInterventionSA refusInterventionSA;

    @Bean(ResumeInterventionSAImpl.class)
    ResumeInterventionSA resumeInterventionSA;

    CustomPagerResumeAdapter adapter;

    @AfterViews
    void afterView(){
        setFooterVisibility(false);
        setHeaderVisibility(false,false);
        setToolbarVisibility(true
                ,getString(R.string.demande_toolbar) +" "+convertStringDate(demandeArtisan.getDate())
                ,true
        );
        if(userRefused){
            layoutButtonAnswer.setVisibility(View.GONE);
        } else {
            layoutButtonAnswer.setVisibility(View.VISIBLE);
        }
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            if (!isBackToDetail()) {
                getResumeIntervention();
            } else {
                hideLoader();
                setBackToDetail(false);
                showResume(getResumeInterventionResponseDto());
            }
        } else {
            hideLoader();
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
    }

    @Click(R.id.button_oui)
    void clickOui(){
        showAcceptFragment();
    }

    @Click(R.id.button_non)
    void clickNon(){
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            denyIntervention();
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }

    }

    @Click(R.id.text_tel_chef_etablissement)
    void call(){
        if ( ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
            marshmallowReadPemissionCheck();
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+numChefEtablissement.getText()));
            startActivity(intent);
        }
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
            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+numChefEtablissement.getText()));
            startActivity(intent);
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.phone_call_requise));
        }
    }

    @Background
    public void getResumeIntervention(){
        ResumeInterventionResponseDto result= resumeInterventionSA.getResumeIntervention(MainActivity.idInterventionArtisan);
        hideLoader();
        boolean resultNull= false;
        if (result == null) {
            resultNull=true;
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
            if(resultNull){
                pop(false);
            }
        } else {
            if (!result.isHasError()) {
                setResumeInterventionResponseDto(result);
                showResume(result);
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
    public void showResume(ResumeInterventionResponseDto resumeInterventionResponseDto){
        hideLoader();
        switch(resumeInterventionResponseDto.getIntervention().getType()){
            case InterventionType.URGENCE:
                type.setText(getActivity().getString(R.string.urgence_card_title));
                break;
            case InterventionType.INTERVENTION:
                type.setText(getActivity().getString(R.string.intervention_card_title));
                break;
        }
        nature.setText(resumeInterventionResponseDto.getIntervention().getNature());
        detail.setText(resumeInterventionResponseDto.getIntervention().getDetail());
        EtablissementInfoDto etablissementInfoDto= resumeInterventionResponseDto.getIntervention().getEtabliseement();
        nomEtablissement.setText(etablissementInfoDto.getNom());
        addresseEtablissement.setText(etablissementInfoDto.getRue());
        nomChefEtablissement.setText(etablissementInfoDto.getNomContact());
        numChefEtablissement.setText(etablissementInfoDto.getNumero());
        commentaire.setText(resumeInterventionResponseDto.getIntervention().getCommentaire());
       if(resumeInterventionResponseDto.getIntervention().getUrlPhoto().size()>0) {
           List<ListPhotoResumeFragment_> lf = new ArrayList<>();
           lf.clear();
            for(int i=0; i<resumeInterventionResponseDto.getIntervention().getUrlPhoto().size();i++) {
                ListPhotoResumeFragment_ lp = new ListPhotoResumeFragment_();
                lp.setUrl(resumeInterventionResponseDto.getIntervention().getUrlPhoto().get(i));
                lf.add(lp);
            }
           initPhotos(lf);
       }

        if(resumeInterventionResponseDto.getIntervention().getEtatIntervention() == DemandeStatus.ID_ENVOYEE) {
            layoutButtonAnswer.setVisibility(View.VISIBLE);
        } else {
            layoutButtonAnswer.setVisibility(View.GONE);
        }
    }

    @UiThread
    void showArtisanList() {
        replaceFragment(R.id.main_container,new ArtisanDemandeListFragment_(),true,true);
    }

    @UiThread
    void showAcceptFragment() {
        replaceFragment(R.id.main_container,new DemandeApresAcceptationFragment_(),true,true);
    }

    private void denyIntervention() {
        showLoader();
        new AsyncHelper<RefusInterventionResponseDto>() {
            @Override
            protected RefusInterventionResponseDto background() throws Exception {
                return refusInterventionSA.postRefusIntervention(MainActivity.idInterventionArtisan);
            }
            @Override
            protected void success(RefusInterventionResponseDto response) {
                hideLoader();
                if (response == null) {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                } else {
                    if (response.isHasError()) {
                        showErrorDialog(getString(R.string.information), response.getMessage());
                    } else {
                        if (response.getMessage().equalsIgnoreCase("Opération reussie")) {
                            //success
                            showArtisanListDemande();
                        } else {
                            showErrorDialog(getString(R.string.information), getString(R.string.demande_error));
                        }
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
        showSuccesDialog(getString(R.string.information)
                , getString(R.string.intervention_refus)
                ,showListeDemandeListner
        );
    }
    void initPhotos(List<ListPhotoResumeFragment_> lf) {
        adapter = new CustomPagerResumeAdapter(getChildFragmentManager(), lf);
        viewPagerPhoto.setAdapter(adapter);
        indicator.setViewPager(viewPagerPhoto);
    }

    public String convertStringDate(String date){
        try{
            if(date.length()>=10) {
                String mois = date.substring(5,7);
                String jour= date.substring(8,10);
                String s = mois +"/"+ jour ;
                return s;
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }

    public DemandeArtisanDto getDemandeArtisan() {
        return demandeArtisan;
    }

    public void setDemandeArtisan(DemandeArtisanDto demandeArtisan) {
        this.demandeArtisan = demandeArtisan;
    }

    public boolean isUserRefused() {
        return userRefused;
    }

    public void setUserRefused(boolean userRefused) {
        this.userRefused = userRefused;
    }
}
