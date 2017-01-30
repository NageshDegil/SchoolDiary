package com.pawan.schooldiary.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.fragment.contacts.ContactsFragment;
import com.pawan.schooldiary.home.fragment.recent.RecentChatsFragment;
import com.pawan.schooldiary.home.model.Contact;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.parents.activity.ParentsHomeActivity;
import com.pawan.schooldiary.home.teacher.activity.TeacherHomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 17/1/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private List<User> contactList;
    private Fragment fragment;
    private interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView contactName, contactEmail;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.contact_name);
            contactEmail = (TextView) view.findViewById(R.id.contact_email);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getLayoutPosition(), false);
        }

        public void setOnClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    public ContactAdapter(List<User> contactList, Fragment fragment) {
        this.contactList = contactList;
        this.fragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final User contact = contactList.get(position);
        holder.contactName.setText(contact.getName());
        holder.contactEmail.setText(contact.getEmail());
        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(fragment.getActivity() instanceof TeacherHomeActivity) {
                    if(fragment instanceof RecentChatsFragment)
                        ((RecentChatsFragment) fragment).loadChat(contact, R.id.content_teacher_home);
                    else if(fragment instanceof ContactsFragment)
                        ((ContactsFragment) fragment).loadChat(contact, R.id.content_teacher_home);
                }
                else if(fragment.getActivity() instanceof ParentsHomeActivity) {
                    if (fragment instanceof RecentChatsFragment)
                        ((RecentChatsFragment) fragment).loadChat(contact, R.id.content_parent_home);
                    else if (fragment instanceof ContactsFragment)
                        ((ContactsFragment) fragment).loadChat(contact, R.id.content_parent_home);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
