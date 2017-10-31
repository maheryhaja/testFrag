package fr.hdb.artibip.presentation.fragment.facturationfinale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.MaterielDto;


public class ListeMaterielFacturationAdapter extends BaseAdapter {

    Context ct;
    LayoutInflater inflate;
    List<MaterielDto> materiel = new ArrayList<MaterielDto>();

    public ListeMaterielFacturationAdapter(Context ct, List<MaterielDto> materiel) {
        this.ct = ct;
        this.materiel = materiel;
        this.inflate = LayoutInflater.from(ct);
    }

    @Override
    public int getCount() {
        return materiel.size();
    }

    @Override
    public Object getItem(int position) {
        return materiel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = (View) inflate.inflate(R.layout.liste_item_materiel_facture_finale, parent, false);
        } else {
            v = (View) convertView;
        }
        TextView nom= (TextView)v.findViewById(R.id.nom_materiel);
        TextView prix= (TextView)v.findViewById(R.id.prix_materiel);
        nom.setText(materiel.get(position).getNomMateriel());
        prix.setText(materiel.get(position).getPrixMaterielHt()+ "  € H.T");
        return v;
    }
}
