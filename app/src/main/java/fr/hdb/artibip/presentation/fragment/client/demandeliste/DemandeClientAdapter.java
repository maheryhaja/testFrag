package fr.hdb.artibip.presentation.fragment.client.demandeliste;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl_;


public class DemandeClientAdapter extends BaseAdapter {

    private List<DemandeClientDto> demandes;
    private DateSA dateSA;
    private Context context;
    LayoutInflater inflater;

    public DemandeClientAdapter (Context context, List<DemandeClientDto> list){
        this.demandes = list;
        this.context= context;
        dateSA = DateSAImpl_.getInstance_(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return demandes.size();
    }

    @Override
    public DemandeClientDto getItem(int position) {
       return demandes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        View v;

        if(convertView == null){
           v = inflater.inflate(R.layout.liste_item_demande_client, parent, false);
        }else {
            v = (View) convertView;
        }

        TextView demande = (TextView) v.findViewById(R.id.text_nom_demande);
        TextView statut = (TextView) v.findViewById(R.id.text_statut_demande);
        ImageView imageStatut = (ImageView) v.findViewById(R.id.image_statut);
        demande.setText("DEMANDE DU "+ convertStringDate(demandes.get(position).getDateIntervention()));

        switch(demandes.get(position).getEtatIntervention()){
            case DemandeStatus.ID_ENVOYEE:
                statut.setText(context.getString(R.string.client_status_envoyee));
               imageStatut.setImageResource(R.drawable.smiley_envoyer);
            break;
            case DemandeStatus.ID_EN_COURS:
                statut.setText(context.getString(R.string.client_status_en_cours));
                imageStatut.setImageResource(R.drawable.smiley_en_cour);
                break;
            case DemandeStatus.ID_TERMINEE:
                statut.setText(context.getString(R.string.client_status_terminee));
                imageStatut.setImageResource(R.drawable.smiley_terminer);
                break;
            case DemandeStatus.ID_ANNULEE:
                statut.setText(context.getString(R.string.client_status_annulee));
                imageStatut.setImageResource(R.drawable.smiley_annuler);
                break;
        }
        v.setTag(position);
        return v;
    }

    public String convertStringDate(String datePub){
        if (datePub != null && !TextUtils.isEmpty(datePub)) {
            String mois = datePub.substring(5,7);
            String jour= datePub.substring(8,10);
            String s = jour + "/" + mois; /*+"   "+time ;*/
            return s;
        }
        return "";
    }

}
