package com.pawan.schooldiary.home.model.offline;

import com.pawan.schooldiary.home.model.Group;

import java.util.List;

/**
 * Created by pawan on 30/1/17.
 */

public class Groups {
    private List<Group> groups;

    public Groups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
