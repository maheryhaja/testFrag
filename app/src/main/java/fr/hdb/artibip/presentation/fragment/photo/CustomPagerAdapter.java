package fr.hdb.artibip.presentation.fragment.photo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import java.util.List;
import fr.hdb.artibip.presentation.activity.GenericActivity;
import me.relex.circleindicator.CircleIndicator;


public class CustomPagerAdapter extends FragmentPagerAdapter {

    public  List<ListPhotoFragment> lf ;
    public FragmentManager fm;

    public CustomPagerAdapter(FragmentManager fragmentManager, List<ListPhotoFragment> lf) {
        super(fragmentManager);
        this.lf=lf;
        this.fm = fragmentManager;
    }

    // enlever un fragment du ViewPager
    public void removeItem(int pos, ViewPager vp, List<ListPhotoFragment> fr, CircleIndicator ci){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fr.get(pos));
        transaction.commitAllowingStateLoss();
        for(int i=0;i<lf.size();i++){
            if(i>=pos){
                FragmentManager manager1 = GenericActivity.getCurrentActivity().getSupportFragmentManager();
                FragmentTransaction transaction1 = fm.beginTransaction();
                transaction1.remove(fr.get(i));
                transaction1.commitAllowingStateLoss();
            }
        }
        lf.remove(pos);
        notifyDataSetChanged();
        vp.setAdapter(this);
        ci.setViewPager(vp);
        if(pos<lf.size()){
            vp.setCurrentItem(pos);
        }else{
            vp.setCurrentItem(pos-1);
        }
    }

    @Override
    public int getCount() {
        return lf.size();
    }

    @Override
    public Fragment getItem(int position) {
        return lf.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }



}