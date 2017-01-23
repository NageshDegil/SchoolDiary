package com.pawan.schooldiary.home.parents.fragment.home;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.fragment.contacts.ContactsFragment_;
import com.pawan.schooldiary.home.model.ViewPagerHelper;
import com.pawan.schooldiary.home.parents.activity.ParentsHomeActivity;
import com.pawan.schooldiary.home.parents.adapter.ParentViewPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_parent_home)
public class ParentsHomeFragment extends Fragment implements ViewPagerHelper{

    @ViewById(R.id.tab_layout)
    TabLayout tabLayout;

    @ViewById(R.id.view_pager)
    ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    @AfterViews
    void init() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        ParentsHomeActivity parentHomeActivity = (ParentsHomeActivity) getActivity();
        parentHomeActivity.initViewPagerHelper(this);
    }

    public void setupViewPager(ViewPager viewPager) {
        ParentViewPagerAdapter adapter = new ParentViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new ContactsFragment_(), "Recent");
        adapter.addFragment(new ContactsFragment_(), "Contacts");
        viewPager.setAdapter(adapter);
    }


    @Override
    public ViewPager getViewPager() {
        return this.viewPager;
    }

}
