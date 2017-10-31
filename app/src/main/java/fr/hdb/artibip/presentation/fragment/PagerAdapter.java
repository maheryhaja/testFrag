package fr.hdb.artibip.presentation.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import fr.hdb.artibip.presentation.fragment.client.PagerFragment_;


public class PagerAdapter extends FragmentStatePagerAdapter {

    private static int NUM_PAGES;

    public PagerAdapter (FragmentManager fm,int numPage) {
        super(fm);
        NUM_PAGES = numPage;
    }

    @Override
    public Fragment getItem(int position) {
        return new PagerFragment_();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
