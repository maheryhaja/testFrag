package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.utilisationmateriel;


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

public class ListMaterielAdapter extends BaseAdapter {
    Context ct;
    LayoutInflater inflate;
    List<MaterielDto> materiel = new ArrayList<MaterielDto>();

    public ListMaterielAdapter(Context ct, List<MaterielDto> materiel) {
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
            v = (View) inflate.inflate(R.layout.list_item_materiel, parent, false);
        } else {
            v = (View) convertView;
        }
        View img_view= (View)v.findViewById(R.id.img_view);
        TextView nom= (TextView)v.findViewById(R.id.nom_materiel);
        TextView prix= (TextView)v.findViewById(R.id.prix_materiel);
        nom.setText(materiel.get(position).getNomMateriel());
        prix.setText(materiel.get(position).getPrixMaterielHt()+ "  € H.T");

        if(position%2 ==0){
            img_view.setBackgroundColor(ct.getResources().getColor(R.color.color_primary_dark));
        }else{
            img_view.setBackgroundColor(ct.getResources().getColor(R.color.gris));
        }
        v.setTag(position);

        return v;
    }


}
