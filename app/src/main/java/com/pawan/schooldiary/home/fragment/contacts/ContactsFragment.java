package com.pawan.schooldiary.home.fragment.contacts;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.pawan.schooldiary.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_contacts)
public class ContactsFragment extends Fragment {

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews
    void init() {

    }

}
