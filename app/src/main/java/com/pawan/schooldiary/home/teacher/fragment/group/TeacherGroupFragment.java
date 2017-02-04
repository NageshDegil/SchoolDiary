package com.pawan.schooldiary.home.teacher.fragment.group;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.model.offline.Groups;
import com.pawan.schooldiary.home.teacher.adapter.GroupAdapter;
import com.pawan.schooldiary.home.teacher.adapter.GroupMemberAdapter;
import com.pawan.schooldiary.home.teacher.fragment.groupChat.GroupChatFragment_;
import com.pawan.schooldiary.home.teacher.service.TeacherHomeService;
import com.pawan.schooldiary.home.utils.Constants;
import com.pawan.schooldiary.home.utils.FileDBUtils;
import com.pawan.schooldiary.home.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_teacher_group)
public class TeacherGroupFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    private GroupAdapter groupAdapter;
    private List<Group> groupList = new ArrayList<>();
    private TeacherHomeService teacherHomeService;
    private interface TeacherGroupCallback {
        public void loadGroups(List<Group> groups);
        public void saveGroups(Groups groups);
    }

    @AfterViews
    public void init() {
        teacherHomeService = schoolDiaryApplication.retrofit.create(TeacherHomeService.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getGroups();
    }

    public TeacherGroupCallback teacherGroupCallback = new TeacherGroupCallback() {

        @Override
        public void loadGroups(List<Group> groups) {
            if(groups != null) {
                groupAdapter = new GroupAdapter(groups, TeacherGroupFragment.this);
            } else {
                FileDBUtils<Groups> fileDBUtils = new FileDBUtils<>(getContext().getApplicationContext(), FileDBUtils.GROUPS, Groups.class, FileDBUtils.USER_DIR);
                Groups groups1 = fileDBUtils.readObject();
                if(groups1 != null)
                    groupAdapter = new GroupAdapter(groups1.getGroups(), TeacherGroupFragment.this);
                else
                    groupAdapter = new GroupAdapter(new ArrayList<Group>(), TeacherGroupFragment.this);
            }
            recyclerView.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
        }

        @Override
        public void saveGroups(Groups groups) {
            FileDBUtils<Groups> dbUtils = new FileDBUtils<>(getActivity(), FileDBUtils.GROUPS, Groups.class, FileDBUtils.USER_DIR);
            dbUtils.saveObject(groups);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.teacher_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.create_group) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_teacher_home, new AddGroupFragment_())
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getGroups() {
        teacherHomeService.getGroups(new Status(Utils.getLoggedInEmail(getContext())))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Group>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.networkError(getActivity(), "Network Error", "Please check your internet connectivity.", e);
                        if(teacherGroupCallback != null)
                            teacherGroupCallback.loadGroups(null);
                    }

                    @Override
                    public void onNext(List<Group> groupList ) {
                        if(teacherGroupCallback != null) {
                            teacherGroupCallback.loadGroups(groupList);
                            teacherGroupCallback.saveGroups(new Groups(groupList));
                        }
                    }
                });
    }

    public void loadGroupChatFragment(Group group) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("GROUP", group);
        GroupChatFragment_ groupChatFragment = new GroupChatFragment_();
        groupChatFragment.setArguments(bundle);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_teacher_home, groupChatFragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadGroupDetailsFragment(Group group) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.GROUP_DETAILS, group);
        GroupDetailsFragment_ groupDetailsFragment = new GroupDetailsFragment_();
        groupDetailsFragment.setArguments(bundle);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_teacher_home, groupDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadAddMemberFragment(Group group) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.GROUP_DETAILS, group);
        AddMemberFragment_ addMemberFragment = new AddMemberFragment_();
        addMemberFragment.setArguments(bundle);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_teacher_home, addMemberFragment)
                .addToBackStack(null)
                .commit();
    }
}
