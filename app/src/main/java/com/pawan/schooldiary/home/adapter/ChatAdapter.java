package com.pawan.schooldiary.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.model.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 17/1/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Chat> chatList = new ArrayList<>();

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public ChatAdapter(List<Chat> chats) {
        this.chatList = chats;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        if(chat.getWhich().equals("a")) {
            holder.linearLayoutLeft.setVisibility(View.GONE);
            holder.textViewRightChat.setText(chat.getMsg());
        } else {
            holder.linearLayoutRight.setVisibility(View.GONE);
            holder.textViewLeftChat.setText(chat.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


}
