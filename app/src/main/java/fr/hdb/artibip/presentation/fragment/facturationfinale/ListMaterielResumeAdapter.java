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
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.intervention.MaterielResumeDto;


public class ListMaterielResumeAdapter extends BaseAdapter {
    Context ct;
    LayoutInflater inflate;
    List<MaterielResumeDto> materiel = new ArrayList<MaterielResumeDto>();

    public ListMaterielResumeAdapter(Context ct, List<MaterielResumeDto> materiel) {
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
        nom.setText(materiel.get(position).getLibelle());
        prix.setText(round(materiel.get(position).getCoutHT(),2)+ "  € H.T");
        return v;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}

