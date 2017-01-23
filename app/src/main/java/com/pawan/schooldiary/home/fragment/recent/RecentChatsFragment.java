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
import com.pawan.schooldiary.home.model.Chat;
import com.pawan.schooldiary.home.model.RecentChats;
import com.pawan.schooldiary.home.model.User;
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

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_recent_chats)
public class RecentChatsFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    private ContactAdapter contactAdapter;
    private List<User> contactList = new ArrayList<>();
    private CommonService commonService;

    private interface RecentChatCallback {
        public void loadRecentChats(List<User> userList);
        public void saveRecentChats(List<User> userList);
    }

    RecentChatCallback recentChatCallback = new RecentChatCallback() {
        @Override
        public void loadRecentChats(List<User> userList) {
            if(userList != null) {
                contactAdapter = new ContactAdapter(userList, RecentChatsFragment.this);

            } else {
                FileDBUtils<RecentChats> fileDBUtils = new FileDBUtils<>(getContext().getApplicationContext(), FileDBUtils.RECENT_CHATS, RecentChats.class, FileDBUtils.USER_DIR);
                RecentChats recentChats = fileDBUtils.readObject();
                if(recentChats != null)
                    contactAdapter = new ContactAdapter(recentChats.getUserList(), RecentChatsFragment.this);
                else
                    contactAdapter = new ContactAdapter(new ArrayList<User>(), RecentChatsFragment.this);
            }
            recyclerView.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();
        }

        @Override
        public void saveRecentChats(List<User> userList) {
            FileDBUtils<RecentChats> fileDBUtils = new FileDBUtils<>(getContext().getApplicationContext(), FileDBUtils.RECENT_CHATS, RecentChats.class, FileDBUtils.USER_DIR);
            fileDBUtils.saveObject(new RecentChats(userList));
        }
    };

    @AfterViews
    public void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getRecentChats();
    }

    private void getRecentChats() {
        Chat chat = new Chat(
            Utils.readPreferenceData(getContext().getApplicationContext(), Constants.LOGIN_TYPE, "").equals("T") ? Utils.readPreferenceData(getContext().getApplicationContext(), Constants.TEACHER_EMAIL_KEY, "") : Utils.readPreferenceData(getContext(), Constants.PARENTS_EMAIL_KEY, ""),
            Utils.readPreferenceData(getContext().getApplicationContext(), Constants.LOGIN_TYPE, "")
        );
        commonService.getRecentChats(chat)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(recentChatCallback != null)
                            recentChatCallback.loadRecentChats(null);
                    }

                    @Override
                    public void onNext(List<User> userList) {
                        if(recentChatCallback != null) {
                            recentChatCallback.loadRecentChats(userList);
                            recentChatCallback.saveRecentChats(userList);
                        }

                    }
                });
    }
}
