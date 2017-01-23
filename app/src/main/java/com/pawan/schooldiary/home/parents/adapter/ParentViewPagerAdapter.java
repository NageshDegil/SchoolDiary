package com.pawan.schooldiary.home.parents.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pawan.schooldiary.app.UpdateFragmentHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by divya on 21/01/17.
 */

public class ParentViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    private FragmentManager fragmentManager;


    public ParentViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof UpdateFragmentHelper) {
            ((UpdateFragmentHelper) object).updateFragment();
        }
        return super.getItemPosition(object);
    }
}
