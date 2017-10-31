package fr.hdb.artibip.presentation.fragment.artisan.interventionliste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeArtisanDto;
import fr.hdb.artibip.donnee.dto.constantes.demande.DemandeStatus;

public class DemandeArtisanAdapter extends BaseAdapter {
    Context ct;
    LayoutInflater inflate;
    List<DemandeArtisanDto> DemandeArtisan = new ArrayList<DemandeArtisanDto>();

    public DemandeArtisanAdapter(Context ct , List<DemandeArtisanDto> da){
        this.ct= ct;
        this.DemandeArtisan= da;
        this.inflate = LayoutInflater.from(ct);
    }
    @Override
    public int getCount() {
        return DemandeArtisan.size();
    }
    @Override
    public Object getItem(int position) {
        return DemandeArtisan.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rl;
        if (convertView == null) {
            rl = (View) inflate.inflate(R.layout.list_item_artisan_demande, parent, false);
        } else {
            rl = (View) convertView;
        }
        TextView demande = (TextView) rl.findViewById(R.id.text_demande);
        TextView facturer = (TextView) rl.findViewById(R.id.text_facturer);
        TextView status = (TextView) rl.findViewById(R.id.text_status);
        ImageView img= (ImageView) rl.findViewById(R.id.image_view_status);

        demande.setText("Demande du  "+ convertStringDate(DemandeArtisan.get(position).getDate()));

        switch(DemandeArtisan.get(position).getStatus()){
            case DemandeStatus.ID_ENVOYEE:
                status.setText(ct.getString(R.string.artisan_status_a_traiter));
                img.setImageResource(R.drawable.circle_orange);
                facturer.setVisibility(View.INVISIBLE);
                demande.setTag(position);
                demande.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                status.setTag(position);
                status.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                img.setTag(position);
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                facturer.setOnClickListener(null);
                break;
            case DemandeStatus.ID_EN_COURS:
                status.setText(ct.getString(R.string.artisan_status_en_cours));
                img.setImageResource(R.drawable.circle_jaune);
                facturer.setVisibility(View.VISIBLE);
                facturer.setTag(position);
                demande.setOnClickListener(null);
                status.setOnClickListener(null);
                img.setOnClickListener(null);
                facturer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListenerFacturer(DemandeArtisan.get(position), position);

                    }

                });

                break;
            case DemandeStatus.ID_TERMINEE:
                status.setText(ct.getString(R.string.artisan_status_traitee));
                img.setImageResource(R.drawable.circle_vert);
                facturer.setVisibility(View.INVISIBLE);
                demande.setTag(position);
                demande.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                status.setTag(position);
                status.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                img.setTag(position);
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                facturer.setOnClickListener(null);
                break;
            case DemandeStatus.ID_ANNULEE:
                status.setText(ct.getString(R.string.artisan_status_annulee));
                img.setImageResource(R.drawable.circle_orange);
                facturer.setVisibility(View.INVISIBLE);
                demande.setTag(position);
                demande.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                status.setTag(position);
                status.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                img.setTag(position);
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Integer position = (Integer)v.getTag();
                        sendListener(DemandeArtisan.get(position), position);

                    }

                });
                facturer.setOnClickListener(null);
                break;
        }
        return rl;
    }

    public interface AdapterListener {
        public void onClickItem(DemandeArtisanDto item, int position);
        public void onClickItemFacturer(DemandeArtisanDto item, int position);
    }

    private ArrayList<AdapterListener> mListListener = new ArrayList<AdapterListener>();

    public void addListener(AdapterListener aListener) {
        mListListener.add(aListener);
    }

    private void sendListener(DemandeArtisanDto item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickItem(item, position);
        }
    }

    private void sendListenerFacturer(DemandeArtisanDto item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickItemFacturer(item, position);
        }
    }

    public String convertStringDate(String date){
        try{
            if(date.length()>=10) {
                String mois = date.substring(5,7);
                String jour= date.substring(8,10);
                String s = jour+"/"+mois ;
                return s;
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }
}
