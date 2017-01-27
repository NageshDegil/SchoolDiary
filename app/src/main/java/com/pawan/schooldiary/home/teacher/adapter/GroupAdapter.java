package com.pawan.schooldiary.home.teacher.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.adapter.ContactAdapter;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.teacher.fragment.group.TeacherGroupFragment;

import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by pawan on 24/1/17.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    private List<Group> groupNameList;
    private Fragment fragment;
    private interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public GroupAdapter(List<Group> groupNameList, Fragment fragment) {
        this.groupNameList = groupNameList;
        this.fragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_name_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewGroupName.setText(groupNameList.get(position).getGroupName());
        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(fragment instanceof TeacherGroupFragment)
                    ((TeacherGroupFragment)fragment).loadGroupChatFragment(groupNameList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupNameList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewGroupName;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewGroupName = (TextView) itemView.findViewById(R.id.text_view_group_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getLayoutPosition(), false);
        }

        public void setOnClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
