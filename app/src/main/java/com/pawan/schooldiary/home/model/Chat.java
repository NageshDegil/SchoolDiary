package com.pawan.schooldiary.home.model;

/**
 * Created by pawan on 17/1/17.
 */

public class Chat {
    private String message;

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private boolean right;

    public Chat() {
    }

    public Chat(String message, boolean right) {
        this.message = message;
        this.right = right;
    }
}
