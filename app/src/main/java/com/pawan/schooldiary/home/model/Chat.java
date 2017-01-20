package com.pawan.schooldiary.home.model;

/**
 * Created by pawan on 17/1/17.
 */

public class Chat {
    private int id;
    private String teacher;
    private String parents;
    private String msg;
    private int teacherFlag;
    private int parentsFlag;
    private long createdAt;
    private String which;

    public Chat() {
    }

    public Chat(String teacher, String parents, String msg, String which) {
        this.teacher = teacher;
        this.parents = parents;
        this.msg = msg;
        this.which = which;
    }

    public String getWhich() {
        return which;
    }

    public void setWhich(String which) {
        this.which = which;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTeacherFlag() {
        return teacherFlag;
    }

    public void setTeacherFlag(int teacherFlag) {
        this.teacherFlag = teacherFlag;
    }

    public int getParentsFlag() {
        return parentsFlag;
    }

    public void setParentsFlag(int parentsFlag) {
        this.parentsFlag = parentsFlag;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
