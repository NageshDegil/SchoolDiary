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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.teacher.adapter.GroupMemberAdapter;
import com.pawan.schooldiary.home.teacher.service.TeacherHomeService;
import com.pawan.schooldiary.home.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_group_details)
public class GroupDetailsFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.linear_layout_edit)
    LinearLayout linearLayoutEdit;

    @ViewById(R.id.linear_layout_change)
    LinearLayout linearLayoutChange;

    @ViewById(R.id.text_view_group_name)
    TextView textViewGroupName;

    @ViewById(R.id.edit_text_group_name)
    EditText editTextGroupName;

    private GroupMemberAdapter adapter;
    private Group group = null;
    private TeacherHomeService teacherHomeService;
    private List<User> removeMemberList;

    public List<User> getRemoveMemberList() {
        return removeMemberList;
    }

    public void setRemoveMemberList(List<User> removeMemberList) {
        this.removeMemberList = removeMemberList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        group = (Group) getArguments().getSerializable(Constants.GROUP_DETAILS);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {
        teacherHomeService = schoolDiaryApplication.retrofit.create(TeacherHomeService.class);
        linearLayoutChange.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GroupMemberAdapter(group.getUsers(), GroupDetailsFragment.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Click(R.id.button_edit)
    public void changeGroupName() {
        linearLayoutEdit.setVisibility(View.GONE);
        linearLayoutChange.setVisibility(View.VISIBLE);
    }

    @Click(R.id.button_save)
    public void saveGroupName() {
        // TODO add validation
        group.setGroupName(editTextGroupName.getText().toString().trim());
        teacherHomeService.changeGroupName(group)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Status status) {
                        textViewGroupName.setText(editTextGroupName.getText().toString().trim());
                        linearLayoutEdit.setVisibility(View.VISIBLE);
                        linearLayoutChange.setVisibility(View.GONE);
                    }
                });

    }

    @Click(R.id.button_remove_member)
    public void removeMember() {
        // TODO add validations
        // 1. check atleast one member exist in group
        // 2. list not be empty
        if(removeMemberList.size() > 0) {
            Group sendGroup = new Group(group.getGroupID(), removeMemberList);
            teacherHomeService.removeMembers(sendGroup)
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            List<User> userList = group.getUsers();
                            userList.removeAll(removeMemberList);
                            adapter.setUserList(userList);
                            adapter.notifyDataSetChanged();
                        }
                    });
        }

    }

}
