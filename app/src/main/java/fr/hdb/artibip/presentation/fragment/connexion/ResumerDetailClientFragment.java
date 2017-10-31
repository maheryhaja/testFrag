package fr.hdb.artibip.presentation.fragment.connexion;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.ResumerInfoClientPostDto;
import fr.hdb.artibip.presentation.fragment.AbstractFragment;

@EFragment(R.layout.resumer_detail_client)
public class ResumerDetailClientFragment extends AbstractFragment{

    @ViewById(R.id.raison_sociale_resumer)
    TextView raisonSociale;

    @ViewById(R.id.nom_prenom_resumer)
    TextView nomPrenom;

    @ViewById(R.id.adresse_mail_resumer)
    TextView adresseMail;

    @ViewById(R.id.numero_client_resumer)
    TextView numeroClient;

    @ViewById(R.id.adresse_facturation_resumer)
    TextView adresseFacturation;

    @ViewById(R.id.numero_bancaire_resumer)
    TextView numeroBancaire;

    @ViewById(R.id.demande_etablissement_secondaire_resumer)
    TextView demandeEtablissementSecondaire;

    @ViewById(R.id.demande_independance_paiement_resumer)
    TextView demandeIndependancePaiement;

    @ViewById(R.id.demande_independance_artisan_resumer)
    TextView demandeIndependanceArtisan;

    @ViewById(R.id.list_etabliesement_resumer)
    ListView listEtablissementResumer;

    @ViewById(R.id.linear_info_etabliessement)
    LinearLayout liearInfoEtablissement;

    @AfterViews
    void afterView(){
        setToolbarVisibility(true, getString(R.string.incription),true);
        setHeaderVisibility(true,true);
        initChamp(getResumerInfoClientPostDto());
    }


    @UiThread
    public void initChamp(ResumerInfoClientPostDto resumerInfoClientPostDto){
        //info client
        raisonSociale.setText(resumerInfoClientPostDto.getInfoCLientPostDto().getRaisonSociale());
        nomPrenom.setText(resumerInfoClientPostDto.getInfoCLientPostDto().getNomPrenom());
        adresseMail.setText(resumerInfoClientPostDto.getInfoCLientPostDto().getAdresseEmail());
        numeroClient.setText(resumerInfoClientPostDto.getInfoCLientPostDto().getNumero());
        adresseFacturation.setText(resumerInfoClientPostDto.getInfoCLientPostDto().getAdresseFacturation());

        //info bancaire
        numeroBancaire.setText(resumerInfoClientPostDto.getInfoBancairePostDto().getNumeroCarteBancaire());

        //info etablissement secondaire
        demandeEtablissementSecondaire.setText(resumerInfoClientPostDto.isDemandeEtablissementSecondaire() ? getString(R.string.oui):getString(R.string.non));
        demandeIndependancePaiement.setText(resumerInfoClientPostDto.isDemandeIndependancePaiement()? getString(R.string.oui):getString(R.string.non));
        demandeIndependanceArtisan.setText(resumerInfoClientPostDto.isDemandeIndependanceArtisan()? getString(R.string.oui):getString(R.string.non));
        if(resumerInfoClientPostDto.getEtablissementSecondairePostDto().size()>0) {
            InscriptionListEtablissementAdapter inscriptionListEtablissementAdapter = new InscriptionListEtablissementAdapter(getContext(), resumerInfoClientPostDto.getEtablissementSecondairePostDto());
            listEtablissementResumer.setAdapter(inscriptionListEtablissementAdapter);
            setListViewEtablissementHeight(resumerInfoClientPostDto.getEtablissementSecondairePostDto().size() - 1);
        }else{
            listEtablissementResumer.setVisibility(View.GONE);
        }

    }

    void setListViewEtablissementHeight(int count) {
        ViewGroup.LayoutParams params = liearInfoEtablissement.getLayoutParams();
        params.height =params.height+ (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60 * count, getResources().getDisplayMetrics());
        params.height =params.height+ (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        liearInfoEtablissement.setLayoutParams(params);
    }

}
