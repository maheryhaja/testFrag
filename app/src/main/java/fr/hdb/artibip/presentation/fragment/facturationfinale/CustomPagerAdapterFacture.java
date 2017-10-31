package fr.hdb.artibip.presentation.fragment.facturationfinale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

import fr.hdb.artibip.presentation.fragment.photo.ListPhotoFragment_;


public class CustomPagerAdapterFacture extends FragmentPagerAdapter {

    public List<ListPhotoFragment_> lf ;
    public FragmentManager fm;

    public CustomPagerAdapterFacture(FragmentManager fragmentManager, List<ListPhotoFragment_> lf) {
        super(fragmentManager);
        this.lf=lf;
        this.fm = fragmentManager;
    }

    @Override
    public int getCount() {
        return lf.size();
    }

    @Override
    public Fragment getItem(int position) {
        return lf.get(position);
    }
}
