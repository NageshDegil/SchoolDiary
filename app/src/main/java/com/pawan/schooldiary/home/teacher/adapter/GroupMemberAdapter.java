package com.pawan.schooldiary.home.teacher.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.teacher.fragment.group.AddGroupFragment;
import com.pawan.schooldiary.home.teacher.fragment.group.GroupDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 23/1/17.
 */

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.MyViewHolder> {
    private List<User> userList;
    private Fragment fragment;
    private List<String> stringList = new ArrayList<>();
    private List<User> removeMemberList = new ArrayList<>();
    private interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemClickListener itemClickListener;
        private TextView textViewEmail, textViewName;
        private ImageView imageView;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            textViewName = (TextView) itemView.findViewById(R.id.user_name);
            textViewEmail = (TextView) itemView.findViewById(R.id.user_email);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }


        public void setOnClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    public GroupMemberAdapter(List<User> userList, Fragment fragment) {
        this.userList = userList;
        this.fragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.textViewEmail.setText(user.getEmail());
        holder.textViewName.setText(user.getName());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()) {
                    stringList.add(user.getEmail());
                    removeMemberList.add(user);
                } else {
                    stringList.remove(user.getEmail());
                    removeMemberList.remove(user);
                }

                if(fragment instanceof AddGroupFragment)
                    ((AddGroupFragment)fragment).setGroupMemberList(stringList);
                else if(fragment instanceof GroupDetailsFragment)
                    ((GroupDetailsFragment)fragment).setRemoveMemberList(removeMemberList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
