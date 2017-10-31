package fr.hdb.artibip.presentation.fragment.connexion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.etablissementsecondaire.EtablissementSecondairePostDto;

public class InscriptionListEtablissementAdapter extends BaseAdapter {
    private List<EtablissementSecondairePostDto> etablissementSecondairePostDtos = new ArrayList<EtablissementSecondairePostDto>();
    private Context context;
    LayoutInflater inflater;

    public InscriptionListEtablissementAdapter(Context context, List<EtablissementSecondairePostDto> etablissementSecondairePostDtos){
        this.context=context;
        this.etablissementSecondairePostDtos=etablissementSecondairePostDtos;
        this.inflater=LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return etablissementSecondairePostDtos.size();
    }

    @Override
    public EtablissementSecondairePostDto getItem(int position) {
        return etablissementSecondairePostDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        View v;
        if(convertView == null){
            v = inflater.inflate(R.layout.inscription_list_item_etablissement, parent, false);
        }else {
            v = (View) convertView;
        }
        TextView nomEtablissement= (TextView)v.findViewById(R.id.etablissement_nom);
        TextView adresseEtablissement= (TextView)v.findViewById(R.id.etablissement_adresse);
        TextView numeroEtablissement= (TextView)v.findViewById(R.id.numero_etablissement);
        View viewBar= (View)v.findViewById(R.id.view_bar);
        nomEtablissement.setText(etablissementSecondairePostDtos.get(position).getNomEtablissement());
        adresseEtablissement.setText(etablissementSecondairePostDtos.get(position).getAdresseEtablissement());
        numeroEtablissement.setText(etablissementSecondairePostDtos.get(position).getNumeroResponsable());
        if(position%2==0){
            viewBar.setBackgroundResource(R.color.color_primary_dark);
        }else{
            viewBar.setBackgroundResource(R.color.gris_claire);
        }
        return v;
    }

}

