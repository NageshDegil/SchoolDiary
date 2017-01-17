package com.pawan.schooldiary.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 17/1/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private List<Contact> contactList = new ArrayList<>();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
