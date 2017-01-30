package com.pawan.schooldiary.home.model.offline;

import com.pawan.schooldiary.home.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by pawan on 21/1/17.
 */

public class RecentChats {
    private List<User> userList;

    public RecentChats(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
