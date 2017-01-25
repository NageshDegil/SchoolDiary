package com.pawan.schooldiary.home.model;

import java.util.List;

/**
 * Created by pawan on 23/1/17.
 */

public class Group {
    private String groupName;
    private String groupID;
    private String groupOwner;
    private String[] groupMembers;
    private List<User> users;

    public Group(String groupOwner, String group, String[] groupMembers) {
        this.groupOwner = groupOwner;
        this.groupName = group;
        this.groupMembers = groupMembers;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
