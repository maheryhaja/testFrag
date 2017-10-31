package fr.hdb.artibip.presentation.fragment.user.passwordinfo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.Strings;
import fr.hdb.artibip.donnee.dto.constantes.menu.Menu;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;
import fr.hdb.artibip.presentation.fragment.menu.DrawerFragment;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.view.NavDrawerItem;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.service.applicatif.client.ClientSA;
import fr.hdb.artibip.service.applicatif.client.ClientSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.network.NetworkManager;
import fr.hdb.artibip.utils.validator.ValidatorManager;


@EFragment(R.layout.fragment_change_mot_passe)
public class PasswordChangeFragment extends GenericFragment {

    @ViewById(R.id.edit_text_old_password)
    EditText editTextOld;

    @ViewById(R.id.edit_text_new_password)
    EditText editTextNew;

    @ViewById(R.id.edit_text_confirme_password)
    EditText editTextConfirme;

    @Bean(ClientSAImpl.class)
    ClientSA clientSA;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @ViewById(R.id.main_toolbar)
    protected View toolbar;

    private String oldPassword;
    private String newPassword;
    private String confirmation;
    private int field;
    private boolean isResetPassword;

    @AfterViews
    void afterView(){
        setApplicationDesign();
    }

    void setApplicationDesign(){
        setFooterVisibility(false);
        setHeaderVisibility(true,false);
        setToolbarVisibility(true,getString(R.string.change_mot_de_pass_toolbar),false);
       if (isResetPassword) {
           if (!oldPassword.equalsIgnoreCase(preferencesSA.getPassword())) {
               editTextOld.setText("");
               editTextOld.setEnabled(true);
           } else {
               editTextOld.setText(oldPassword);
               if (!TextUtils.isEmpty(oldPassword)) {
                   editTextOld.setEnabled(false);
               }
           }
       }
        newPassword = Strings.EMPTY;
        confirmation = Strings.EMPTY;
    }

    @Click(R.id.button_change_password)
    void onClickChange(){
        attemptSetPassword();
    }

    private void attemptSetPassword() {
        // Reset errors.
        editTextOld.setError(null);
        editTextNew.setError(null);
        editTextConfirme.setError(null);
        View focusView = null;
        oldPassword = editTextOld.getText().toString();
        newPassword = editTextNew.getText().toString();
        confirmation = editTextConfirme.getText().toString();
        boolean cancel = false;

        // Check if mail and telephon are empties.
        if ((TextUtils.isEmpty(oldPassword))) {
            editTextOld.setError(getString(R.string.error_field_required));
            focusView = editTextOld;
            cancel = true;
        }if (TextUtils.isEmpty(newPassword)) {
            editTextNew.setError(getString(R.string.error_field_required));
            focusView = editTextNew;
            cancel = true;
        }if (TextUtils.isEmpty(confirmation)) {
            editTextConfirme.setError(getString(R.string.error_field_required));
            focusView = editTextConfirme;
            cancel = true;
        } else if (!TextUtils.isEmpty(oldPassword) && !ValidatorManager.isPasswordValid(oldPassword)) {
            editTextOld.setError(getString(R.string.error_length_password));
            focusView  = editTextOld;
            cancel = true;
        } else if (!TextUtils.isEmpty(newPassword) && !ValidatorManager.isPasswordValid(newPassword)) {
            editTextNew.setError(getString(R.string.error_length_password));
            focusView  = editTextNew;
            cancel = true;
        } else if (!TextUtils.isEmpty(confirmation) && !ValidatorManager.isPasswordValid(confirmation)) {
            editTextConfirme.setError(getString(R.string.error_length_password));
            focusView = editTextConfirme;
            cancel = true;
        } else if(!ValidatorManager.isPasswordMatch(confirmation, newPassword)) {
            editTextConfirme.setError(getString(R.string.error_confirme_password));
            focusView = editTextConfirme;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt process and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            /**
             * Represents an asynchronous login/registration task used to authenticate
             * the user.
             */
            if (NetworkManager.isNetworkAvailable(getActivity())) {
                showLoader();
                editTextOld.setEnabled(true);
                updatePassword();
            }
            else {
                showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
            }
        }
    }

    @Background
    void updatePassword(){
        ResponseDto result = clientSA.loginChange(
                preferencesSA.getId()
                ,oldPassword
                ,newPassword
                ,preferencesSA.getRole()
                ,preferencesSA.getToken()
        );

        if(result == null) {
            noDataFound(getString(R.string.error_ws));
            return;
        }

        if (!result.isHasError()) {
            preferencesSA.setPassword(newPassword);
            showListDemande();
        }else {
            noDataFound(result.getMessage());
        }
    }

    @UiThread
    void showListDemande(){
        hideLoader();
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
                , getString(R.string.mdp_success)
                ,showListeDemandeListner
        );
    }

    /*
    * aucune donnée
     */
    @UiThread
    void noDataFound(String message){
        hideLoader();
        showErrorDialog(getString(R.string.information), message.toUpperCase());
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmation() {
        return confirmation;
    }
    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
    public boolean isResetPassword() {
        return isResetPassword;
    }
    public void setResetPassword(boolean resetPassword) {
        isResetPassword = resetPassword;
    }
}
