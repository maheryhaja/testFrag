package fr.hdb.artibip.presentation.activity;

import android.text.TextUtils;
import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import fr.hdb.artibip.R;
import fr.hdb.artibip.commun.constantes.Constante;
import fr.hdb.artibip.donnee.dto.ws.post.cgv.CgvDto;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.ResumerInfoClientPostDto;
import fr.hdb.artibip.presentation.fragment.connexion.ConnexionFragment;
import fr.hdb.artibip.presentation.fragment.client.ConditionFragment_;
import fr.hdb.artibip.presentation.fragment.connexion.ConnexionFragment_;
import fr.hdb.artibip.presentation.view.TypefaceUtils;
import fr.hdb.artibip.service.applicatif.client.cgv.CgvSA;
import fr.hdb.artibip.service.applicatif.client.cgv.CgvSAImpl;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;

@WindowFeature(Window.FEATURE_ACTION_BAR)
@EActivity(R.layout.activity_accueil)
public class AccueilActivity extends GenericActivity {

    private ResumerInfoClientPostDto resumerInfoClientPostDto = new ResumerInfoClientPostDto();

    @Bean(CgvSAImpl.class)
    protected CgvSA cgvSA;

    @AfterViews
    void initialize() {
        setScreenOrientation();
        TypefaceUtils.overrideFont(this);

        //Push or sms
        if(getIdIntervention() != Constante.DEFAULT_INTERVENTION_ID || !TextUtils.isEmpty(getPassword())) {
            ConnexionFragment_ frag = ConnexionFragment.getInstance(getPassword());
            addFragment(R.id.accueil_main_container, frag, false, false);
        } else {
            //TODO redirect to home page
            ConnexionFragment_ frag = ConnexionFragment.getInstance(getPassword());
            replaceFragment(R.id.accueil_main_container, frag, false, false);
        }
    }

    public void goToHomePage() {
        showActivity(MainActivity_.class, false);
        finish();
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
                            replaceFragment(R.id.accueil_main_container, conditionFragment, true, true);
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

    public ResumerInfoClientPostDto getResumerInfoClientPostDto() {
        return resumerInfoClientPostDto;
    }

    public void setResumerInfoClientPostDto(ResumerInfoClientPostDto resumerInfoClientPostDto) {
        this.resumerInfoClientPostDto = resumerInfoClientPostDto;
    }


}
