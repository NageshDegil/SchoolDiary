package com.pawan.schooldiary.home.teacher.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pawan.schooldiary.home.fragment.contacts.ContactsFragment_;
import com.pawan.schooldiary.home.teacher.fragment.group.TeacherGroupFragment_;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 17/1/17.
 */

public class TeacherViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public TeacherViewPagerAdapter(FragmentManager fm) {
        super(fm);
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
}
