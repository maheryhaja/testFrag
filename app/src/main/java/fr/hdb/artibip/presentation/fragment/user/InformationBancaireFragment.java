package fr.hdb.artibip.presentation.fragment.user;

import android.content.Context;
import android.os.Build;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.multidots.fingerprintauth.FingerPrintUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.event.EventSetEtablissementDto;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.infobancaire.InfoBancairePostDto;
import fr.hdb.artibip.presentation.activity.AccueilActivity_;
import fr.hdb.artibip.presentation.activity.MainActivity_;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;

@EFragment(R.layout.information_bancaire)
public class InformationBancaireFragment extends GenericFragment{

    @ViewById(R.id.numero_bancaire)
    EditText numeroBancaire;

    @ViewById(R.id.code_paiement)
    EditText codePaiement;

    @ViewById(R.id.confirmation_code_paiement)
    EditText confirmationCodePaiement;

    @ViewById(R.id.layout_main_header)
    View logo;

    @ViewById(R.id.linear_under_logo)
    LinearLayout linearUnderLogo;

    @ViewById(R.id.finger_print)
    ImageView fingerPrint;

    @AfterViews
    void afterView(){
        if(getActivity() instanceof MainActivity_){
            setToolbarVisibility(true,getString(R.string.modification_information_generale),false);
            setHeaderVisibility(false,false);
            logo.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) linearUnderLogo.getLayoutParams();
            margin.topMargin=0;
            linearUnderLogo.setLayoutParams(margin);
        }else{
            setToolbarVisibility(true,getString(R.string.inscription_client),false);
            setHeaderVisibility(false,true);
            logo.setVisibility(View.VISIBLE);
        }
        testFingerPrint(getContext());
    }

    @UiThread
    void testFingerPrint(final Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(FingerprintManagerCompat.from(context).isHardwareDetected()) {
                if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
                    View.OnClickListener leftButtonListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogManager.getCustomDialog().dismiss();
                        }
                    };

                    View.OnClickListener rightButtonListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogManager.getCustomDialog().dismiss();
                            FingerPrintUtils.openSecuritySettings(context);
                        }
                    };
                    DialogManager.showMessageCustom(getActivity(), getString(R.string.information), getString(R.string.enregistrement_finger_print), getString(R.string.annuler), getString(R.string.ok), leftButtonListener, rightButtonListener);
                }else{
                    showErrorDialog(getString(R.string.information),getString(R.string.finger_print_deja_existant));
                }
            }else{
                showErrorDialog(getString(R.string.information),getString(R.string.finger_print_non_supportee));
            }
        }else{
            showErrorDialog(getString(R.string.information),getString(R.string.finger_print_non_supportee));
        }
    }


    @Click(R.id.suivant)
    void clickSuivant(){
        List<EditText> listEditText = new ArrayList<>();
        listEditText.add(numeroBancaire);
        listEditText.add(codePaiement);
        listEditText.add(confirmationCodePaiement);
        if(!isEmpty(listEditText)) {
            if(!isNotValide(listEditText)) {
                if (getActivity() instanceof AccueilActivity_) {
                    InfoBancairePostDto infoBancairePostDto = new InfoBancairePostDto();
                    infoBancairePostDto.setNumeroCarteBancaire(numeroBancaire.getText().toString());
                    infoBancairePostDto.setCodeDePaiement(codePaiement.getText().toString());
                    getResumerInfoClientPostDto().setInfoBancairePostDto(infoBancairePostDto);
                    replaceFragment(R.id.accueil_main_container, new DemandeEtablissementSecondaireFragment_(), false, true);
                }
            }
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
        return result;
    }

    public boolean isNotValide(List<EditText> listEditText) {
        boolean result = false;
        for (EditText editText : listEditText) {
            switch (editText.getId()) {
                case R.id.confirmation_code_paiement:
                {
                    if(!editText.getText().toString().equals(codePaiement.getText().toString())){
                        editText.setError(getString(R.string.confirmation_code_de_paiement_incorrecte));
                        editText.requestFocus();
                        result=true;
                    }
                }
                break;
            }
        }
        return result;
    }

}
