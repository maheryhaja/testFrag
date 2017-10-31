package fr.hdb.artibip.presentation.fragment.user.passwordinfo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.Strings;
import fr.hdb.artibip.donnee.dto.constantes.login.Identifiant;
import fr.hdb.artibip.donnee.dto.ws.response.LoginOubliResponseDto;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.service.applicatif.client.ClientSA;
import fr.hdb.artibip.service.applicatif.client.ClientSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.validator.ValidatorManager;


@EFragment(R.layout.mot_de_passe_oublie)
public class PasswordForgotFragment extends GenericFragment {

    @ViewById(R.id.edit_text_mail)
    EditText editTextMail;

    @ViewById(R.id.edit_text_tel)
    EditText editTextTel;

    @ViewById(R.id.edit_text_nom_etabli)
    EditText editTextNomEtabli;

    private String email;
    private String telephon;
    private String nomEtabli;
    private int field;

    @Bean(ClientSAImpl.class)
    ClientSA clientSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @AfterViews
    void afterView(){
        setFooterVisibility(false);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.mot_de_pass_oublie_toolbar),false);
        email= Strings.EMPTY;
        telephon = Strings.EMPTY;
        nomEtabli= Strings.EMPTY;
        field = Identifiant.DEFAULT;
    }

    @Click(R.id.button_connexion)
    void onClickConnexion(){
        attemptSetPassword();
    }

    @TextChange(R.id.edit_text_mail)
    void onMailChanged() {
        field = Identifiant.MAIL;
        //cancel = false;
        if (!editTextMail.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
            if (!editTextNomEtabli.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextNomEtabli.setText(Strings.EMPTY);
            }
            if (!editTextTel.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextTel.setText(Strings.EMPTY);
            }
        }
    }

    @TextChange(R.id.edit_text_tel)
    void onTelChanged() {
        field = Identifiant.NUMERO;
        //cancel = false;
        if (!editTextTel.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
            if (!editTextMail.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextMail.setText(Strings.EMPTY);
            }
            if (!editTextNomEtabli.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextNomEtabli.setText(Strings.EMPTY);
            }
        }
    }

    @TextChange(R.id.edit_text_nom_etabli)
    void onNomEtabliChanged() {
        field = Identifiant.NOM_ETABLISSEMENT;
        //cancel = false;
        if (!editTextNomEtabli.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
            if (!editTextMail.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextMail.setText(Strings.EMPTY);
            }
            if (!editTextTel.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
                editTextTel.setText(Strings.EMPTY);
            }
        }
    }

    @Background
    void postOubliLogin(final String login, final int type){
        LoginOubliResponseDto result = clientSA.loginOubli(login,type);
        if(result == null) {
            hideLoader();
            showErrorDialog(getString(R.string.information), getString(R.string.error_ws));
        } else {
           if(!result.isHasError()) {
               hideLoader();
               if (result.getMessage().equals("mail_sent")  ) {
                   showErrorDialog(getString(R.string.information), getString(R.string.mdp_success));
               } else {
                   showErrorDialog(getString(R.string.information), getString(R.string.error_mdp));
               }
           }
        }
    }

    private void attemptSetPassword() {
        // Reset errors.
        editTextMail.setError(null);
        editTextTel.setError(null);
        editTextNomEtabli.setError(null);
        View focusView = null;
        boolean cancel = false;
        email = editTextMail.getText().toString();
        telephon = editTextTel.getText().toString();
        nomEtabli = editTextNomEtabli.getText().toString();

        // Check if mail and telephon are empties.
        if ((TextUtils.isEmpty(email) && TextUtils.isEmpty(telephon) && TextUtils.isEmpty(nomEtabli))) {
            editTextMail.setError(getString(R.string.error_field_tel_or_mail_or_etabli));
            focusView = editTextMail;
            cancel = true;
        } else if (!ValidatorManager.isValidEmailAddress(email) && !TextUtils.isEmpty(email)) {
            editTextMail.setError(getString(R.string.error_invalid_email));
            focusView = editTextMail;
            cancel = true;
        } else if (!ValidatorManager.isTelephonValid(telephon) && !TextUtils.isEmpty(telephon)){
            editTextTel.setError(getString(R.string.error_invalid_tel));
            focusView = editTextTel;
            cancel = true;
        } else if (!ValidatorManager.isNomEtabliValide(nomEtabli)  && !TextUtils.isEmpty(nomEtabli) ){
            editTextNomEtabli.setError(getString(R.string.error_invalid_nom_etabli));
            focusView = editTextNomEtabli;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            /**
             * Represents an asynchronous login/registration task used to authenticate
             * the user.
             */
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                showLoader();
                switch (field){
                    case Identifiant.MAIL : postOubliLogin(email, field);
                        break;
                    case Identifiant.NUMERO :postOubliLogin(telephon, field);
                        break;
                    case Identifiant.NOM_ETABLISSEMENT : postOubliLogin(nomEtabli, field);
                        break;
                }
            }
            else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    }

}


