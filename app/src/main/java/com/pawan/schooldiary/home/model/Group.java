package com.pawan.schooldiary.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pawan on 23/1/17.
 */

public class Group implements Serializable{
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

    public Group(String groupID, List<User> users) {
        this.groupID = groupID;
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
