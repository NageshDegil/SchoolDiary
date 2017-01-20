package com.pawan.schooldiary.home.fragment.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.app.UpdateFragmentHelper;
import com.pawan.schooldiary.home.adapter.ChatAdapter;
import com.pawan.schooldiary.home.model.Chat;
import com.pawan.schooldiary.home.teacher.service.TeacherHomeService;
import com.pawan.schooldiary.home.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_chat)
public class ChatFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.chat_recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.image_view_send_msg)
    ImageView imageViewSendMsg;

    @ViewById(R.id.edit_text_chat)
    EditText editTextChat;

    private List<Chat> chatList;
    private ChatAdapter chatAdapter;
    private TeacherHomeService teacherHomeService;
    private FirebaseRecyclerAdapter<Chat, MyViewHolder> firebaseAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;
    private String teacherEmail, parentsEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        teacherEmail = getArguments().getString(Constants.TEACHER_KEY);
        parentsEmail = getArguments().getString(Constants.PARENTS_KEY);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    public void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);


        firebaseAdapter = new FirebaseRecyclerAdapter<Chat, MyViewHolder>(
                Chat.class,
                R.layout.chat_layout,
                MyViewHolder.class,
                databaseReference.child(Constants.DB_NAME).child(createDB(teacherEmail, parentsEmail)))
        {
            @Override
            protected void populateViewHolder(MyViewHolder holder, Chat chat, int position) {
                if(chat.getWhich().equals("T")) { // compare to login user parents/teacher
                    holder.linearLayoutLeft.setVisibility(View.GONE);
                    holder.textViewRightChat.setText(chat.getMsg());
                } else {
                    holder.linearLayoutRight.setVisibility(View.GONE);
                    holder.textViewLeftChat.setText(chat.getMsg());
                }
            }
        };

        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseAdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(firebaseAdapter);

        editTextChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    imageViewSendMsg.setEnabled(true);
                } else {
                    imageViewSendMsg.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initList() {
        teacherHomeService = schoolDiaryApplication.retrofit.create(TeacherHomeService.class);
        teacherHomeService.fetchChats(new Chat())
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Chat>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Chat> chats) {
                        chatList = chats;
                        chatAdapter = new ChatAdapter(chats);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(chatAdapter);
                        chatAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Click(R.id.image_view_send_msg)
    public void sendMsg() {
        /*teacherHomeService.insertChat(new Chat("a", "b", editTextChat.getText().toString()))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Chat>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Chat> chats) {
                        Chat chat = chatList.get(chatList.size()-1);
                        for (Chat newChat : chats) {
                            if(newChat.getId() > chat.getId())
                                chatList.add(newChat);
                        }
                        chatAdapter.setChatList(chatList);
                        chatAdapter.notifyDataSetChanged();
                        editTextChat.setText("");
                    }
                });*/

        Chat chat = new Chat("a","b",editTextChat.getText().toString(), "T");
        databaseReference.child(Constants.DB_NAME).child(createDB(teacherEmail, parentsEmail)).push().setValue(chat);
        editTextChat.setText("");
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewLeftChat, textViewRightChat;
        private ImageView imageViewLeftUser, imageViewRightUser;
        private LinearLayout linearLayoutLeft, linearLayoutRight;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewLeftChat = (TextView) itemView.findViewById(R.id.text_view_left_chat);
            textViewRightChat = (TextView) itemView.findViewById(R.id.text_view_right_chat);
            imageViewLeftUser = (ImageView) itemView.findViewById(R.id.image_view_left_user);
            imageViewRightUser = (ImageView) itemView.findViewById(R.id.image_view_right_user);
            linearLayoutLeft = (LinearLayout) itemView.findViewById(R.id.linear_layout_left);
            linearLayoutRight = (LinearLayout) itemView.findViewById(R.id.linear_layout_right);
        }
    }

    private String createDB(String teacher, String parents) {
        String senderDB = "demo", receiverDB = "omed";
        int senderEnd = teacher.indexOf("@");
        if (senderEnd != -1)
            senderDB = teacher.substring(0, senderEnd);
        int receiverEnd = parents.indexOf("@");
        if (receiverEnd != -1)
            receiverDB = parents.substring(0, receiverEnd);
        return senderDB+receiverDB;
    }
}
