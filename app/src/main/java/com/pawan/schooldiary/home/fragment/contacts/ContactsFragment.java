package com.pawan.schooldiary.home.fragment.contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.adapter.ContactAdapter;
import com.pawan.schooldiary.home.fragment.chat.ChatFragment_;
import com.pawan.schooldiary.home.model.Contact;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.model.offline.RecentChats;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.utils.Constants;
import com.pawan.schooldiary.home.utils.FileDBUtils;
import com.pawan.schooldiary.home.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_contacts)
public class ContactsFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    private ContactAdapter contactAdapter;
    private List<User> contactList = new ArrayList<>();
    private CommonService commonService;
    private interface ContactsCallback {
        public void loadContacts(List<User> userList);
        public void saveContacts(List<User> userList);
    }

    private ContactsCallback contactsCallback = new ContactsCallback() {
        @Override
        public void loadContacts(List<User> userList) {
            if(userList != null) {
                contactAdapter = new ContactAdapter(userList, ContactsFragment.this);

            } else {
                FileDBUtils<RecentChats> fileDBUtils = new FileDBUtils<>(getContext().getApplicationContext(), FileDBUtils.CONTACTS, RecentChats.class, FileDBUtils.USER_DIR);
                RecentChats recentChats = fileDBUtils.readObject();
                if(recentChats != null)
                    contactAdapter = new ContactAdapter(recentChats.getUserList(), ContactsFragment.this);
                else
                    contactAdapter = new ContactAdapter(new ArrayList<User>(), ContactsFragment.this);
            }
            recyclerView.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();
        }

        @Override
        public void saveContacts(List<User> userList) {
            FileDBUtils<RecentChats> fileDBUtils = new FileDBUtils<>(getContext().getApplicationContext(), FileDBUtils.CONTACTS, RecentChats.class, FileDBUtils.USER_DIR);
            fileDBUtils.saveObject(new RecentChats(userList));
        }
    };

    @AfterViews
    void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        contactList();
    }

    private void contactList() {
        commonService.getContacts(new Status(Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.LOGIN_TYPE, "")))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(contactsCallback != null)
                            contactsCallback.loadContacts(null);
                    }

                    @Override
                    public void onNext(List<User> users ) {
                        if(contactsCallback != null) {
                            contactsCallback.loadContacts(users);
                            contactsCallback.saveContacts(users);
                        }
                    }
                });
    }

    public void loadChat(User user, int id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RECEIVER_EMAIL, user);
        ChatFragment_ chatFragment = new ChatFragment_();
        chatFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(id, chatFragment).addToBackStack(null).commit();
    }

}
