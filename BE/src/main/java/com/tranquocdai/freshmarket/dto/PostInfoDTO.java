package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.Post;

public class PostInfoDTO {
    Post post;

    private float averageRate;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }
}
