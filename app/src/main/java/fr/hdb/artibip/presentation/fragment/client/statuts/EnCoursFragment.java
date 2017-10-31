package fr.hdb.artibip.presentation.fragment.client.statuts;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.wang.avi.AVLoadingIndicatorView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.ws.response.ClientDemandeResponseDto;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSA;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSAImpl;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;


@EFragment(R.layout.demande_en_cours_client)
public class EnCoursFragment extends GenericFragment{

    private boolean isSent = false; //Flag gestion si une intervention vien d'être envoyé;

    @Bean(ClientDemandeSAImpl.class)
    protected ClientDemandeSA clientDemandeSA;

    @ViewById(R.id.image_statut_en_cours)
    protected ImageView imageViewThumbnail;

    @ViewById(R.id.avi_loading_encour)
    AVLoadingIndicatorView indicatorEncour;

    @AfterViews
    void afterView(){
        setToolbarVisibility(true, "STATUT", false);
        setHeaderVisibility(true,false);
        setFooterVisibility(false);
        if(getImageEncours() != null){
            PicassoUtils.startLoadingImage(getContext()
                ,getImageEncours()
                ,imageViewThumbnail
                ,indicatorEncour
           );
        } else {
            showLoader();
            getListDemande();
            //indicatorEncour.setVisibility(View.GONE);
        }
    }

    @Background
    void getListDemande(){
        ClientDemandeResponseDto result =  clientDemandeSA.getClientDemande();
        if(result != null){
            refreshScreen(result);
        } else {
            hideLoader();
            setTousDesDemandesDto(null);
        }
    }

    @UiThread
    void refreshScreen(ClientDemandeResponseDto result){
        hideLoader();
        if(!result.isHasError()){
            if(result.getImageEncours() != null){
                setImageEncours(result.getImageEncours());
            }

            if(result.getNumeroConseiller()!= null){
                setNumConseiller(result.getNumeroConseiller());
            }

            if(result.getImageRefuse()!= null){
                setImageRefuse(result.getImageRefuse());
            }

            if(result.getListDemande().size() > 0){
                setTousDesDemandesDto(result.getListDemande());
            } else {
                setTousDesDemandesDto(null);
            }
            if(result.getImageEncours() != null){
                hideLoader();
                PicassoUtils.startLoadingImage(getContext()
                        ,result.getImageEncours()
                        ,imageViewThumbnail
                        ,indicatorEncour
                );
            } else {
                indicatorEncour.setVisibility(View.GONE);
            }
        }
    }

    private DemandeClientDto demande;
    public DemandeClientDto getDemande() {
        return demande;
    }
    public void setDemande(DemandeClientDto demande) {
        this.demande = demande;
    }
    public boolean isSent() {
        return isSent;
    }
    public void setSent(boolean sent) {
        isSent = sent;
    }
}
