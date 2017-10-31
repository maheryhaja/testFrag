package fr.hdb.artibip.presentation.fragment.connexion;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.Strings;
import fr.hdb.artibip.donnee.dto.constantes.login.Identifiant;
import fr.hdb.artibip.donnee.dto.ws.push.NotificationResponseDto;
import fr.hdb.artibip.donnee.dto.ws.response.LoginResponseDto;
import fr.hdb.artibip.presentation.activity.AccueilActivity_;
import fr.hdb.artibip.presentation.fragment.AbstractFragment;;
import fr.hdb.artibip.presentation.fragment.user.passwordinfo.PasswordForgotFragment_;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.client.ClientSA;
import fr.hdb.artibip.service.applicatif.client.ClientSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.applicatif.push.NotificationSA;
import fr.hdb.artibip.service.applicatif.push.NotificationSAImpl;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.validator.ValidatorManager;

@EFragment(R.layout.connexion_fragment)
public class ConnexionFragment extends AbstractFragment {

    @ViewById(R.id.edit_text_mail)
    EditText editTextMail;

    @ViewById(R.id.edit_text_tel)
    EditText editTextTel;

    @ViewById(R.id.edit_text_password)
    EditText editTextPass;

    @ViewById(R.id.layout_cgu)
    View layoutCgu;

    @ViewById(R.id.checkbox_cgu_login)
    CheckBox checkBoxCgu;

    @Bean(ClientSAImpl.class)
    ClientSA clientSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @Bean(NotificationSAImpl.class)
    protected NotificationSA notificationSA;

    private String email;
    private String telephon;
    private int field;
    boolean cancel = false;
    boolean checked = false;
    private String password;

    public static ConnexionFragment_ getInstance(String password) {
        ConnexionFragment_ frag = new ConnexionFragment_();
        frag.setPassword(password);
        return frag;
    }

    @AfterViews
    void afterView() {

        permissionToDrawOverlays();
        checkGooglePlayAvailability();

        //((MainActivity)getActivity()).drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setFooterVisibility(false);
        setHeaderVisibility(true,false);
        setToolbarVisibility(false,getString(R.string.connexion),false);
        // initialize login variables
        email= Strings.EMPTY;
        telephon = Strings.EMPTY;
        field = Identifiant.DEFAULT;
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), getString(R.string.lato_regular));
        editTextMail.setTypeface(typeface);
        // set password if reset password
        if (!TextUtils.isEmpty(password)) {
            editTextPass.setText(password);
            editTextPass.setEnabled(false);
            checkBoxCgu.setChecked(true);
        } else {
            editTextPass.setText("");
            editTextPass.setEnabled(true);
            checkBoxCgu.setChecked(false);
        }
    }

    @Click(R.id.text_view_forgot_password)
    void onClickTxt(){
        replaceFragment(R.id.accueil_main_container,new PasswordForgotFragment_(),true,true);
    }

    @Click(R.id.click_inscription)
    void onClickInscription(){
        replaceFragment(R.id.accueil_main_container,new DemandeMetierFragment_(),true,true);
    }

    @Click(R.id.text_view_cgu)
    void onClickCondition(){
        if (NetworkManager.isNetworkAvailable(getActivity())) {
            getCgv();
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
        }
    }

    @Click(R.id.button_login)
    void onClickConnexion(){
        attemptLogin();
    }

   @TextChange (R.id.edit_text_tel)
    void onTelChanged() {
        field = Identifiant.NUMERO;
        cancel = false;
       if (!editTextTel.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
           if (!editTextMail.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
               editTextMail.setText(Strings.EMPTY);
           }
       }
    }

    @TextChange(R.id.edit_text_mail)
    void onMailChanged() {
        field = Identifiant.MAIL;
        cancel = false;
        if (!editTextMail.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
            if (!editTextTel.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextTel.setText(Strings.EMPTY);
            }
        }
    }

    @TextChange(R.id.edit_text_password)
    void onPassChange(){
        cancel=false;
    }

    @UiThread
    void reInit() {
        editTextPass.setEnabled(true);
        checkBoxCgu.setChecked(false);
    }

/*    @UiThread
    void showFragmentClient(){
        hideLoader();
        replaceFragment(R.id.main_container,new BesoinFragment_(),true,true);
    }*/

    @Background
    void postLogin(final String login, final String password){
        LoginResponseDto result = clientSA.login(login, password);
        if(result == null) {
            hideLoader();
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
        } else {
            if(!result.isHasError() && !result.getToken().isEmpty()){
                preferences.setLogin(result.getEmail());
                preferences.setToken(result.getToken());
                preferences.setRole(result.getRole());
                preferences.setEmail(result.getEmail());
                preferences.setId(result.getIdClient());
                preferences.setTypeUser(result.getTypeUser());
                preferences.setPassword(editTextPass.getText().toString());

                /*
                try {
                    initGcm(result);
                } catch (Exception e) {

                }*/
                if (FirebaseInstanceId.getInstance().getToken() != "") {
                    postUid(FirebaseInstanceId.getInstance().getToken(), result);
                }
            } else {
                hideLoader();
                showErrorDialog(getString(R.string.information), getString(R.string.error_login));
            }
        }
    }

    /** Notification Services */
    private void postUid(final String regId, final LoginResponseDto result) {
        new AsyncHelper<NotificationResponseDto>() {
            @Override
            protected NotificationResponseDto background() throws Exception {
                return notificationSA.postUid(regId);
            }
            @Override
            protected void success(NotificationResponseDto sended) {
                hideLoader();
                if (sended != null) {
                    if (!sended.isError()) {
                        ((AccueilActivity_) getActivity()).goToHomePage();
                    } else {
                        showErrorDialog(getString(R.string.information), sended.getMessage().toUpperCase().replace("_"," "));
                    }
                } else {
                    showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
                }
            }
        }.launch(getActivity());
    }

    /** login validator */
    private void attemptLogin() {
        // Reset errors.
        editTextMail.setError(null);
        editTextPass.setError(null);
        editTextTel.setError(null);
        checkBoxCgu.setError(null);
        email = editTextMail.getText().toString();
        telephon = editTextTel.getText().toString();
        // Store values at the time of the login attempt.
        final String password =  editTextPass.getText().toString();
        boolean cancel = false;
        View focusView = null;
        View otherFocusView = null;

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(telephon)) {
            editTextMail.setError(getString(R.string.error_field_tel_or_mail));
            editTextTel.setError(getString(R.string.error_field_tel_or_mail));
            focusView = editTextMail;
            otherFocusView = editTextPass;
            cancel = true;

        } else if (TextUtils.isEmpty(password)) {
            editTextPass.setError(getString(R.string.error_field_required));
            otherFocusView = editTextPass;
            cancel = true;

        } else if (!ValidatorManager.isValidEmailAddress(email) && !TextUtils.isEmpty(email)) {
            editTextMail.setError(getString(R.string.error_invalid_email));
            focusView = editTextMail;
            cancel = true;

        } else if (!ValidatorManager.isTelephonValid(telephon) && !TextUtils.isEmpty(telephon)) {
            editTextTel.setError(getString(R.string.error_invalid_tel));
            focusView = editTextTel;
            cancel = true;

        } else if (!TextUtils.isEmpty(password) && !ValidatorManager.isPasswordValid(password)) {
            editTextPass.setError(getString(R.string.error_length_password ));
            otherFocusView = editTextPass;
            cancel = true;

        } else if (!ValidatorManager.isPasswordSecured(password)) {
            editTextPass.setError(getString(R.string.error_password_not_secured));
            otherFocusView = editTextPass;
            cancel = true;
        }

        if (!checkBoxCgu.isChecked()) {
            checkBoxCgu.setError(getString(R.string.error_cgu));
            focusView = checkBoxCgu;
            checked = false;
        } else {
            checked = true;
        }

        if (cancel || !checked) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
            if (otherFocusView != null) {
                otherFocusView.requestFocus();
            }
        } else {
            /**
             * Represents an asynchronous login/registration task used to authenticate
             * the user.
             */
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                // login
                final String login = field == Identifiant.MAIL ? email : telephon;
                showLoader();
                reInit();
                postLogin(login, password);
            } else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    }

/*    public void initGcm(final LoginResponseDto loginResponseDto) {
        GCMClientManager pushClientManager = new GCMClientManager(getActivity(), GcmConstant.PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                if (registrationId != null) {
                    if (!registrationId.equals("")) {
                        // post regId to server
                        Log.e("POST","reg id : "+registrationId);
                        postUid(registrationId, loginResponseDto);
                        if (isNewRegistration) {
                        } else {
                        }
                    }
                }
            }
            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }*/

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    private final static int GMS = 4321;
    private final String appPackageName = "com.google.android.gms";


    private void checkGooglePlayAvailability() {

        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
        if (isAvailable != ConnectionResult.SUCCESS) {
            showSuccesDialog(getString(R.string.information), getString(R.string.gms), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.getCustomDialog().dismiss();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    }catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });
        }
    }


    private final static int PERMISSION_REQUEST_DRAW_OVERLAYS = 1234;

    private void permissionToDrawOverlays() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getActivity())) {
                showSuccesDialog(getString(R.string.information), getString(R.string.overlay), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getCustomDialog().dismiss();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
                        startActivityForResult(intent, PERMISSION_REQUEST_DRAW_OVERLAYS);
                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERMISSION_REQUEST_DRAW_OVERLAYS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getActivity())) {
                   permissionToDrawOverlays();
                }
            }
        }
    }

}
