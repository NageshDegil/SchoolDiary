package com.pawan.schooldiary.home.teacher.fragment.groupChat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.pawan.schooldiary.home.adapter.ChatAdapter;
import com.pawan.schooldiary.home.fragment.chat.ChatFragment;
import com.pawan.schooldiary.home.model.Chat;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.teacher.service.TeacherHomeService;
import com.pawan.schooldiary.home.utils.Constants;
import com.pawan.schooldiary.home.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_group_chat)
public class GroupChatFragment extends Fragment {

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
    private FirebaseRecyclerAdapter<Chat, GroupChatFragment.MyViewHolder> firebaseAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;
    private Group group;
    private CommonService commonService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        group = (Group) getArguments().getSerializable("GROUP");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    public void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);


        firebaseAdapter = new FirebaseRecyclerAdapter<Chat, GroupChatFragment.MyViewHolder>(
                Chat.class,
                R.layout.chat_layout,
                GroupChatFragment.MyViewHolder.class,
                databaseReference.child(Constants.DB_NAME).child(createDB(group.getGroupID())))
        {
            @Override
            protected void populateViewHolder(GroupChatFragment.MyViewHolder holder, Chat chat, int position) {
                if(chat.getWhich().equals(Utils.readPreferenceData(getContext(), Constants.LOGIN_TYPE, ""))) { // compare to login user parents/teacher
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
                /*if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }*/
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

    @Click(R.id.image_view_send_msg)
    public void sendMsg() {
        // TODO adding multiple same chat in DB resolve it
        Chat chat = new Chat(Utils.getLoggedInEmail(getContext()), "group@gmail.com", editTextChat.getText().toString(), Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.LOGIN_TYPE, ""));
        databaseReference.child(Constants.DB_NAME).child(createDB(group.getGroupID())).push().setValue(chat);
        for (User user: group.getUsers()) {
            chat = new Chat(getContext(),user.getEmail(),editTextChat.getText().toString(), Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.LOGIN_TYPE, ""));
            databaseReference.child(Constants.DB_NAME).child(commonDB(user.getEmail())).push().setValue(chat);
            addRecentChat(user);
        }
        editTextChat.setText("");
    }

    private void addRecentChat(User user) {
        commonService.addRecentChat(new Chat(getContext(), user.getEmail()))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                    }
                });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewLeftChat, textViewRightChat;
        public ImageView imageViewLeftUser, imageViewRightUser;
        public LinearLayout linearLayoutLeft, linearLayoutRight;

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

    private String createDB(String dbID) {
        String[] strings = new String[dbID.length()];
        for (int i=0; i < dbID.length(); i++)
            strings[i] = String.valueOf(dbID.charAt(i));

        StringBuilder stringBuilder = new StringBuilder();

        for (String s: strings) {
            switch (Integer.parseInt(s)) {
                case 0:
                    stringBuilder.append("a");
                    break;

                case 1:
                    stringBuilder.append("b");
                    break;

                case 2:
                    stringBuilder.append("c");
                    break;

                case 3:
                    stringBuilder.append("d");
                    break;

                case 4:
                    stringBuilder.append("e");
                    break;

                case 5:
                    stringBuilder.append("f");
                    break;

                case 6:
                    stringBuilder.append("g");
                    break;

                case 7:
                    stringBuilder.append("h");
                    break;

                case 8:
                    stringBuilder.append("i");
                    break;

                case 9:
                    stringBuilder.append("j");
                    break;

            }
        }
        return String.valueOf(stringBuilder);
    }

    private String commonDB(String receiverEmail) {
        String type = Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.LOGIN_TYPE, "");
        String senderDB = "demo", receiverDB = "omed", teacher = "", parents = "";
        if(type.equals("T")) {
            teacher = Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.TEACHER_EMAIL_KEY, "");
            parents = receiverEmail;
        } else {
            parents = Utils.readPreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.PARENTS_EMAIL_KEY, "");
            teacher = receiverEmail;
        }
        int senderEnd = teacher.indexOf("@");
        if (senderEnd != -1)
            senderDB = teacher.substring(0, senderEnd);
        int receiverEnd = parents.indexOf("@");
        if (receiverEnd != -1)
            receiverDB = parents.substring(0, receiverEnd);
        return senderDB+receiverDB;
    }

}
