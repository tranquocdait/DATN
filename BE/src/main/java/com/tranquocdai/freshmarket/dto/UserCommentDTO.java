package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.User;

public class UserCommentDTO {
    private User user;

    private String comment;

    private int rate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
