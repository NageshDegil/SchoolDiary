package com.pawan.schooldiary.home.fragment.recent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.adapter.ContactAdapter;
import com.pawan.schooldiary.home.fragment.contacts.ContactsFragment;
import com.pawan.schooldiary.home.model.RecentChats;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.utils.FileDBUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_recent_chats)
public class RecentChatsFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    private ContactAdapter contactAdapter;
    private List<User> contactList = new ArrayList<>();

    @AfterViews
    public void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getRecentChats();
    }

    private void getRecentChats() {
        FileDBUtils<RecentChats> fileDBUtils = new FileDBUtils<>(getContext().getApplicationContext(), FileDBUtils.RECENT_CHATS, RecentChats.class, FileDBUtils.USER_DIR);
        RecentChats recentChats = fileDBUtils.readObject();
        if(recentChats != null)
            contactList = new ArrayList<User>(recentChats.getUserMap().values());
        contactAdapter = new ContactAdapter(contactList, RecentChatsFragment.this);
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

    }
}
