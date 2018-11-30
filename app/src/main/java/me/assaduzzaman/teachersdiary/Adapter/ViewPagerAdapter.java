package me.assaduzzaman.teachersdiary.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList=new ArrayList<Fragment>();
    private final List<String> dayList=new ArrayList<String>();


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addfragment(Fragment f, String list){
        fragmentList.add(f);
        dayList.add(list);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return dayList.get(position);
    }





}
