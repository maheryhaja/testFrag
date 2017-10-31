package fr.hdb.artibip.presentation.fragment.client.urgence;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.List;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.UrgenceDto;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;


public class UrgenceAdapter extends BaseAdapter {

    private List<UrgenceDto> urgenceDtos;
    Context context;
    LayoutInflater inflater;

    public UrgenceAdapter(Context context, List<UrgenceDto> list){
        this.urgenceDtos = list;
        this.context= context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return urgenceDtos.size();
    }

    @Override
    public UrgenceDto getItem(int position) {
        return urgenceDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {

        final UrgenceDto urgenceDto = getItem(position);
        final ViewUrgenceHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_card, parent, false);
            holder = new ViewUrgenceHolder();
            holder.urgence = (TextView) convertView.findViewById(R.id.text_card_title);
            holder.image = (ImageView) convertView.findViewById(R.id.image_card_icon);
            holder.detail = (TextView) convertView.findViewById(R.id.text_card_desc);
            holder.indicator = (AVLoadingIndicatorView) convertView.findViewById(R.id.avi_loading_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewUrgenceHolder) convertView.getTag();
        }

        if(urgenceDto != null) {
            String libelle;
            if (urgenceDto.getLebelle() != null) {
                libelle = urgenceDto.getLebelle();
            } else {
                libelle = "...";
            }
            if (libelle.equalsIgnoreCase("Autre")) {
                holder.urgence.setVisibility(View.GONE);
                holder.image.setVisibility(View.GONE);
                holder.indicator.setVisibility(View.GONE);
                holder.detail.setVisibility(View.VISIBLE);
                holder.detail.setText(urgenceDto.getLebelle().toUpperCase());
            } else {
                holder.detail.setVisibility(View.GONE);
                holder.urgence.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.VISIBLE);
                holder.urgence.setText(libelle.toUpperCase());
                try {
                    if (urgenceDto.getUrlPhoto() != null) {
                        PicassoUtils.startLoadingImage(context
                                , urgenceDto.getUrlPhoto()
                                , holder.image
                                , holder.indicator
                        );
                    } else {
                        holder.indicator.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    holder.indicator.setVisibility(View.GONE);
                }
            }
        }
        return convertView;
    }

    public class ViewUrgenceHolder {
        TextView urgence;
        ImageView image;
        TextView detail;
        AVLoadingIndicatorView indicator;
    }
}
