package fr.hdb.artibip.presentation.fragment.client.statuts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.constantes.permission.Permission;
import fr.hdb.artibip.presentation.fragment.GenericFragment;


@EFragment(R.layout.demande_positive_client)
public class PositiveFragment extends GenericFragment {

    @ViewById(R.id.text_view_demande_pos_heure)
    protected TextView textViewHeure;

    @ViewById(R.id.text_view_demande_pos_tech)

    protected TextView textViewNomTechnicien;

    @ViewById(R.id.text_view_demande_pos_num)
    protected TextView textViewNumTechnicien;

    @Click(R.id.text_view_demande_pos_num)
    void call(){
        if ( ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
            marshmallowReadPemissionCheck();
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textViewNumTechnicien.getText()));
            startActivity(intent);
        }
    }

    private void marshmallowReadPemissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, Permission.PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Permission.PHONE_CALL && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textViewNumTechnicien.getText()));
            startActivity(intent);
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.phone_call_requise));
        }
    }

    @AfterViews
    void afterView(){
        setToolbarVisibility(true, "STATUT", false);
        setHeaderVisibility(true, false);
        setFooterVisibility(false);
        initChamps();
    }

    private DemandeClientDto demande;
    public DemandeClientDto getDemande() {
        return demande;
    }
    public void setDemande(DemandeClientDto demande) {
        this.demande = demande;
    }

    public void initChamps(){
        if(this.demande.getArrivee().getTechnicien().getNom()!=null) {
            textViewNomTechnicien.setText(this.demande.getArrivee().getTechnicien().getPrenom()+" "+this.demande.getArrivee().getTechnicien().getNom());
        }
        if(this.demande.getArrivee().getTechnicien().getPortable()!=null) {
            textViewNumTechnicien.setText(this.demande.getArrivee().getTechnicien().getPortable());
        }
        if(this.demande.getArrivee().getHeures()!= null && this.demande.getArrivee().getMinutes()!=null) {
            textViewHeure.setText(this.demande.getArrivee().getHeures() + " H " + this.demande.getArrivee().getMinutes());
        }
    }
}
