package fr.hdb.artibip.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fr.hdb.artibip.R;

/**
 * .
 */

public class DrawerAdapter extends BaseAdapter {

    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public DrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public NavDrawerItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavDrawerItem navDrawerItem = getItem(position);
        final View views = this.inflater.inflate(R.layout.nav_drawer_row,parent,false);
        TextView title = (TextView) views.findViewById(R.id.nav_item_title);
        ImageView icon = (ImageView) views.findViewById(R.id.nav_item_icon);

        title.setText(navDrawerItem.getTitle());
        icon.setImageResource(navDrawerItem.getImageRessource());

        return views;
    }


}
