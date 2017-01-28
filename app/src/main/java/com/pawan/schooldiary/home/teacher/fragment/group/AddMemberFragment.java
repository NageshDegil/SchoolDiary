package com.pawan.schooldiary.home.teacher.fragment.group;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.teacher.adapter.GroupMemberAdapter;
import com.pawan.schooldiary.home.teacher.service.TeacherHomeService;
import com.pawan.schooldiary.home.utils.Constants;
import com.pawan.schooldiary.home.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_add_member)
public class AddMemberFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    private TeacherHomeService teacherHomeService;
    private CommonService commonService;
    private Group group;
    private GroupMemberAdapter adapter;
    private List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        group = (Group) getArguments().getSerializable(Constants.GROUP_DETAILS);
    }

    @AfterViews
    public void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        teacherHomeService = schoolDiaryApplication.retrofit.create(TeacherHomeService.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initRecycler();
    }



    @Click(R.id.button_add_member)
    public void addMember() {
        // TODO add service to add member in group
        Group sendGroup = new Group(group.getGroupID(), userList);
        teacherHomeService.addMembers(sendGroup)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }
                });
    }

    private void initRecycler() {
        commonService.getContacts(new Status(Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.LOGIN_TYPE, "")))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<User> users ) {
                        List<User> list = new ArrayList<User>();
                        for (User user:users) {
                            for (User localUser:group.getUsers()) {
                                if(!user.getEmail().equals(localUser.getEmail()))
                                    list.add(user);
                            }
                        }
                        adapter = new GroupMemberAdapter(list, AddMemberFragment.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}
