package fr.hdb.artibip.presentation.fragment.client.etablissementliste;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.EtablissementDto;


public class EtablissementAdapter extends BaseAdapter {

    private List<EtablissementDto> etablissements;
    private boolean onOff = true;
    private Context context;
    LayoutInflater inflater;
    private EtablissementInterface etablissementInterface;
    private int currentEtab = -1;

    public EtablissementAdapter (Context context, List<EtablissementDto> list){
        this.etablissements = list;
        this.context= context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return etablissements.size();
    }

    @Override
    public EtablissementDto getItem(int position) {
        return etablissements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        final EtablissementDto etablissementDto = getItem(position);
        final ViewEtablissementHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_etablissement, parent, false);
            holder = new ViewEtablissementHolder();
            holder.barLeft = (View) convertView.findViewById(R.id.view_bar_left);
            holder.nom = (TextView) convertView.findViewById(R.id.text_nom_etablissement);
            holder.adresse = (TextView) convertView.findViewById(R.id.text_adresse_etablissement);
            holder.radio = (ImageView) convertView.findViewById(R.id.image_circle);
            convertView.setTag(holder);
        }else {
            holder = (ViewEtablissementHolder) convertView.getTag();
        }
        if(etablissementDto != null ){
            if(onOff)
                holder.barLeft.setBackgroundResource(R.color.jaune);
            else
                holder.barLeft.setBackgroundResource(R.color.gris);

            if(etablissementDto.getNomEtablissement() != null)
                holder.nom.setText(etablissementDto.getNomEtablissement().toUpperCase());
            if(etablissementDto.getAdresse() != null)
                holder.adresse.setText(etablissementDto.getAdresse().toUpperCase());

            if(position == currentEtab) {
                holder.radio.setImageResource(R.mipmap.ic_circle_on);
            } else {
                holder.radio.setImageResource(R.mipmap.ic_circle);
            }
            onOff=!onOff;
            holder.radio.setOnClickListener(new OnClickEtablissementListener(etablissementDto,position));
        }
        return convertView;
    }

    /*
    * Interface choix d'une établissement
     */
    public interface EtablissementInterface {
        void selectEtablissement(EtablissementDto itemDto, int position);
    }

    /**
     * Listener selection d'un établissemnt
     */
    public class OnClickEtablissementListener implements View.OnClickListener {
        private EtablissementDto etablissement;
        private int position;

        public OnClickEtablissementListener(EtablissementDto etablissement, int getPosition){
            this.etablissement = etablissement;
            this.position = getPosition;
        }

        @Override
        public void onClick(View view) {
            if (etablissementInterface != null) {
                etablissementInterface.selectEtablissement(this.etablissement,this.position);
                currentEtab = this.position;
            }
        }
    }

    public void setEtablissementInterface(EtablissementInterface etablissementInterface){
        this.etablissementInterface = etablissementInterface;
    }

    public class ViewEtablissementHolder {
        View barLeft;
        TextView nom;
        TextView adresse;
        TextView numero;
        ImageView radio;
    }
}
