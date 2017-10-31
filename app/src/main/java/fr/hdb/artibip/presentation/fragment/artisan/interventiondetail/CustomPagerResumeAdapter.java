package fr.hdb.artibip.presentation.fragment.artisan.interventiondetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

public class CustomPagerResumeAdapter extends FragmentPagerAdapter {

    public final List<ListPhotoResumeFragment_> lf ;
    public FragmentManager fm;

    public CustomPagerResumeAdapter(FragmentManager fragmentManager, List<ListPhotoResumeFragment_> lf) {
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
