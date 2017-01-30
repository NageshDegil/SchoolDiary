package com.pawan.schooldiary.home.teacher.fragment.group;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.adapter.ContactAdapter;
import com.pawan.schooldiary.home.fragment.contacts.ContactsFragment;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.teacher.adapter.GroupMemberAdapter;
import com.pawan.schooldiary.home.teacher.fragment.home.TeacherHomeFragment_;
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

@EFragment(R.layout.fragment_add_group)
public class AddGroupFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.button_create_group)
    Button buttonCreateGroup;

    @ViewById(R.id.edit_text_group_name)
    EditText editTextGroupName;

    private GroupMemberAdapter adapter;
    private CommonService commonService;
    private TeacherHomeService teacherHomeService;
    private List<String> groupMemberList = new ArrayList<>();

    public List<String> getGroupMemberList() {
        return groupMemberList;
    }

    public void setGroupMemberList(List<String> groupMemberList) {
        this.groupMemberList = groupMemberList;
    }

    @AfterViews
    public void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        teacherHomeService = schoolDiaryApplication.retrofit.create(TeacherHomeService.class);
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
                        Utils.networkError(getActivity(), "Network Error", "Please check your internet connectivity.", e);
                    }

                    @Override
                    public void onNext(List<User> users ) {
                        adapter = new GroupMemberAdapter(users, AddGroupFragment.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Click(R.id.button_create_group)
    public void createGroup() {
        Group group = new Group(Utils.getLoggedInEmail(getContext()), editTextGroupName.getText().toString(), groupMemberList.toArray(new String[groupMemberList.size()]));
        teacherHomeService.addGroup(group)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.networkError(getActivity(), "Network Error", "Please check your internet connectivity.", e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        showAlert("Group Created", "Group is created successfully.");
                    }
                });
    }

    private void showAlert(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_teacher_home, new TeacherHomeFragment_())
                                .addToBackStack(null)
                                .commit();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
