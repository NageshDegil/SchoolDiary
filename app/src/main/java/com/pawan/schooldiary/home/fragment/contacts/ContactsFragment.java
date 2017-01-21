package com.pawan.schooldiary.home.fragment.contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.adapter.ContactAdapter;
import com.pawan.schooldiary.home.fragment.chat.ChatFragment_;
import com.pawan.schooldiary.home.model.Contact;
import com.pawan.schooldiary.home.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_contacts)
public class ContactsFragment extends Fragment {
    private List<Contact> contactList = new ArrayList<>();

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    private ContactAdapter contactAdapter;

    @AfterViews
    void init() {
        contactAdapter = new ContactAdapter(contactList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        contactList();
    }

    private void contactList() {
        Contact contact = new Contact("Divya","Email : divya@gmail.com");
        contactList.add(contact);

        contact = new Contact("Pawan", "Email : pawan@gmail.com");
        contactList.add(contact);

        contact = new Contact("Megha", "Email : megha@gmail.com");
        contactList.add(contact);

        contact = new Contact("Pradnya", "Email : pradnya@gmail.com");
        contactList.add(contact);

        contact = new Contact("Anjana", "Email : anjana@gmail.com");
        contactList.add(contact);

        contactAdapter.notifyDataSetChanged();
    }

    public void loadChat(String teacherEmail, String parentsEmail) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TEACHER_EMAIL_KEY, teacherEmail);
        bundle.putString(Constants.PARENTS_EMAIL_KEY, parentsEmail);
        ChatFragment_ chatFragment = new ChatFragment_();
        chatFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_teacher_home, chatFragment).commit();
    }

}
