package com.pawan.schooldiary.home.fragment.contacts.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.UpdateFragmentHelper;
import com.pawan.schooldiary.home.adapter.ChatAdapter;
import com.pawan.schooldiary.home.model.Chat;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_chat)
public class ChatFragment extends Fragment {

    @ViewById(R.id.chat_recycler_view)
    RecyclerView recyclerView;

    private List<Chat> chatList;
    private ChatAdapter chatAdapter;

    @AfterViews
    public void init() {
        initList();
        chatAdapter = new ChatAdapter(chatList);
        chatAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);

    }

    private void initList() {
        chatList = new ArrayList<>();
        chatList.add(new Chat("Hiii", true));
        chatList.add(new Chat("Hello", false));
        chatList.add(new Chat("How are you ?", false));
        chatList.add(new Chat("fine and you", true));
        chatList.add(new Chat("fine", false));
        chatList.add(new Chat("what are you doing ??", false));
        chatList.add(new Chat("Nothing", true));
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
}
