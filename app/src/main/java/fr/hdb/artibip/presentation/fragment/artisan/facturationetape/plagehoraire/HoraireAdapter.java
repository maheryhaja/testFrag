package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.plagehoraire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.PlageHoraireDto;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;

public class HoraireAdapter extends BaseAdapter {
    private List<PlageHoraireDto> PlageHoraireDtos;
    Context context;
    LayoutInflater inflater;

    public HoraireAdapter(Context context, List<PlageHoraireDto> list){
        this.PlageHoraireDtos = list;
        this.context= context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return PlageHoraireDtos.size();
    }

    @Override
    public PlageHoraireDto getItem(int position) {
        return PlageHoraireDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        final PlageHoraireDto PlageHoraireDto = getItem(position);
        final ViewUrgenceHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_card, parent, false);
            holder = new ViewUrgenceHolder();
            holder.horaire = (TextView) convertView.findViewById(R.id.text_card_title);
            holder.image = (ImageView) convertView.findViewById(R.id.image_card_icon);
            holder.detail = (TextView) convertView.findViewById(R.id.text_card_desc);
            holder.indicator = (AVLoadingIndicatorView) convertView.findViewById(R.id.avi_loading_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewUrgenceHolder) convertView.getTag();
        }
        if(PlageHoraireDto != null){
                holder.detail.setVisibility(View.GONE);
                holder.horaire.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.VISIBLE);
                holder.horaire.setText(PlageHoraireDto.getLibelle().toUpperCase());
                try{
                    if(PlageHoraireDto.getUrlPhoto() != null) {
                        PicassoUtils.startLoadingImage(context
                                ,PlageHoraireDto.getUrlPhoto()
                                ,holder.image
                                ,holder.indicator
                        );
                    } else {
                        holder.indicator.setVisibility(View.GONE);
                    }
                } catch (Exception e){
                    holder.indicator.setVisibility(View.GONE);
                }

        }
        return convertView;
    }

    public class ViewUrgenceHolder {
        TextView horaire;
        ImageView image;
        TextView detail;
        AVLoadingIndicatorView indicator;
    }
}
