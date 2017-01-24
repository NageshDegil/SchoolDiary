package com.pawan.schooldiary.home.teacher.fragment.group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.adapter.ContactAdapter;
import com.pawan.schooldiary.home.fragment.contacts.ContactsFragment;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.teacher.adapter.GroupMemberAdapter;
import com.pawan.schooldiary.home.utils.Constants;
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

@EFragment(R.layout.fragment_add_group)
public class AddGroupFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.button_create_group)
    Button buttonCreateGroup;

    private GroupMemberAdapter adapter;
    private CommonService commonService;

    @AfterViews
    public void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        contactList();
        /*ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        marginLayoutParams.setMargins(0, 10, 0, 10);
        recyclerView.setLayoutParams(marginLayoutParams);

        buttonCreateGroup.getLayoutParams();*/

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

                    }

                    @Override
                    public void onNext(List<User> users ) {
                        adapter = new GroupMemberAdapter(users, AddGroupFragment.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}
