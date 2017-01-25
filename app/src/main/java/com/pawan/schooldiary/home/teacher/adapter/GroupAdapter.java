package com.pawan.schooldiary.home.teacher.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.model.Group;

import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by pawan on 24/1/17.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    private List<Group> groupNameList;

    public GroupAdapter(List<Group> groupNameList) {
        this.groupNameList = groupNameList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_name_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewGroupName.setText(groupNameList.get(position).getGroupName());
    }

    @Override
    public int getItemCount() {
        return groupNameList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGroupName;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewGroupName = (TextView) itemView.findViewById(R.id.text_view_group_name);
        }
    }
}
