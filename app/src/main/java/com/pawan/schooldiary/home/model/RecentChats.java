package com.pawan.schooldiary.home.model;

import java.util.Map;

/**
 * Created by pawan on 21/1/17.
 */

public class RecentChats {
    private Map<String, User> userMap;

    public RecentChats(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }
}
