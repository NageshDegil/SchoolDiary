package com.pawan.schooldiary.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.pawan.schooldiary.R;

/**
 * Created by pawan on 18/1/17.
 */

public class OnBackStackChangedListener implements FragmentManager.OnBackStackChangedListener {
    private Fragment selectedFragment;
    private FragmentManager fragmentManager;

    public OnBackStackChangedListener(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_teacher_home);
        if(fragment != null)
            this.selectedFragment = fragment;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_teacher_home);
        if(fragment != null)
            selectedFragment = fragment;
    }

    public Fragment getSelectedFragment() {
        return selectedFragment;
    }

    public void setSelectedFragment(Fragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }
}
