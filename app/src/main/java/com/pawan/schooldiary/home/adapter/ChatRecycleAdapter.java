package com.pawan.schooldiary.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pawan.schooldiary.R;

/**
 * Created by pawan on 20/1/17.
 */

public class ChatRecycleAdapter {



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
}
