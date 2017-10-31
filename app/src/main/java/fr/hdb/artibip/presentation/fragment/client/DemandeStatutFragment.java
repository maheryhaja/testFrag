package fr.hdb.artibip.presentation.fragment.client;

import android.view.View;
import android.widget.RelativeLayout;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.presentation.fragment.GenericFragment;


@EFragment(R.layout.fragment_statut_demande)
public class DemandeStatutFragment extends GenericFragment{

    private int statut;
    private boolean isSent = false; //Flag gestion si une intervention vien d'être envoyé;

    @ViewById(R.id.layout_en_cours)
    RelativeLayout layoutEnCours;

    @ViewById(R.id.layout_positive)
    RelativeLayout layoutPositive;

    @ViewById(R.id.layout_negative)
    RelativeLayout layoutNegative;

    @AfterViews
    void afterView(){
        //Dessiné l'écran
        setApplicationDesgin();
        //Init données
        loadLayoutStatut();
    }

    /*
    * Dessiné l'ecran
     */
    void setApplicationDesgin(){
        //Avec toolbar
        setToolbarVisibility(true,"STATUT",false);
        //Pas de footer
        if(!isSent)
            setHeaderVisibility(true,true);
        else
            setHeaderVisibility(true,false);

        setFooterVisibility(false);
    }

    @UiThread
    void loadLayoutStatut(){
        switch (statut){
            case DemandeStatus.ID_ENVOYEE:
                showPositive();
                break;
            case DemandeStatus.ID_EN_COURS:
                showEnCours();
                break;
            case DemandeStatus.ID_TERMINEE:
                break;
            case DemandeStatus.ID_ANNULEE:
                showNegative();
                break;
        }
    }

    void showEnCours(){
        layoutNegative.setVisibility(View.GONE);
        layoutPositive.setVisibility(View.GONE);
        layoutEnCours.setVisibility(View.VISIBLE);
    }

    void showPositive(){
        layoutNegative.setVisibility(View.GONE);
        layoutEnCours.setVisibility(View.GONE);
        layoutPositive.setVisibility(View.VISIBLE);
    }

    void showNegative(){
        layoutPositive.setVisibility(View.GONE);
        layoutEnCours.setVisibility(View.GONE);
        layoutNegative.setVisibility(View.VISIBLE);
    }

    public int getStatut() {
        return statut;
    }
    public void setStatut(int statut) {
        this.statut = statut;
    }
    public boolean isSent() {
        return isSent;
    }
    public void setSent(boolean sent) {
        isSent = sent;
    }
}
