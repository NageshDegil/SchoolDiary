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
import android.widget.LinearLayout;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.teacher.adapter.GroupMemberAdapter;
import com.pawan.schooldiary.home.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_group_details)
public class GroupDetailsFragment extends Fragment {

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.linear_layout_edit)
    LinearLayout linearLayoutEdit;

    @ViewById(R.id.linear_layout_change)
    LinearLayout linearLayoutChange;

    private GroupMemberAdapter adapter;
    private Group group;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        group = (Group) getArguments().getSerializable(Constants.GROUP_DETAILS);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {
        linearLayoutChange.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GroupMemberAdapter(group.getUsers(), GroupDetailsFragment.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
