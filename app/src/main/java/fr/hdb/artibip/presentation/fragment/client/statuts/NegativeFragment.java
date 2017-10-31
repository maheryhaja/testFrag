package fr.hdb.artibip.presentation.fragment.client.statuts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.wang.avi.AVLoadingIndicatorView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.constantes.permission.Permission;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;


@EFragment(R.layout.demande_negative_client)
public class NegativeFragment extends GenericFragment{

    @ViewById(R.id.text_view_num_conseiller)
    protected TextView textViewNumConseiller;

    @ViewById(R.id.image_view_status_neg)
    protected ImageView imageThumbnail;

    @ViewById(R.id.avi_loading_negative)
    AVLoadingIndicatorView indicatorNegative;

    @Click(R.id.text_view_num_conseiller)
    void call(){
        if ( ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
            marshmallowReadPemissionCheck();
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textViewNumConseiller.getText()));
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
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textViewNumConseiller.getText()));
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
        if(getImageRefuse() != null){
            PicassoUtils.startLoadingImage(getContext()
                ,getImageRefuse()
                ,imageThumbnail
                ,indicatorNegative
           );
        } else {
            indicatorNegative.setVisibility(View.GONE);
        }
        if(getNumConseiller()!= null){
            textViewNumConseiller.setText(getNumConseiller());
        }


    }

    private DemandeClientDto demande;
    public DemandeClientDto getDemande() {
        return demande;
    }
    public void setDemande(DemandeClientDto demande) {
        this.demande = demande;
    }

}
