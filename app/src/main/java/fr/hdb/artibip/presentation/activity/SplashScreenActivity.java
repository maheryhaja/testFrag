package fr.hdb.artibip.presentation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.WindowFeature;

import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.Strings;
import fr.hdb.artibip.donnee.dto.constantes.push.Notification;
import fr.hdb.artibip.donnee.dto.constantes.uri.Sms;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;


@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends GenericActivity {

    private static final int SPLASH_DURATION = 3000;
    private static final int GET_DATA_DURATION = 3000;
    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

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
    protected void afterviews() {
        try {
            setScreenOrientation();
            waitForSplash();
            manageSms(getIntent().getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            manageSms(appLinkData);
        }
    }

    void manageSms(Uri data) {
        if (data != null && !TextUtils.isEmpty(data.toString())) {
            String link = data.toString();
            //password or intervention
            link = link.replace(Sms.SMS_LINK, Strings.EMPTY);

            if (link.startsWith(Sms.SMS_PASSWORD)) {
                setPassword(link.replace(Sms.SMS_PASSWORD, Strings.EMPTY));
            } else {
                int idInterventionArtisan;
                try {
                    idInterventionArtisan = Integer.parseInt(link);
                } catch (Exception e) {
                    idInterventionArtisan = Integer.parseInt(
                            link.substring(link.lastIndexOf("/"))
                    );
                }
                setIdIntervention(idInterventionArtisan);
            }
        }
    }

    @Background(delay = SPLASH_DURATION)
    protected void waitForSplash() {
        goToNextActivity();
    }

    @UiThread
    protected void goToNextActivity() {

        if (preferences.getToken() == null || TextUtils.isEmpty(preferences.getToken())) {
            showActivity(AccueilActivity_.class, false);
        } else {
            showActivity(MainActivity_.class, false);
        }
        finish();
    }

    @UiThread(delay = GET_DATA_DURATION)
    protected void getData(){
        finish();
    }
}
