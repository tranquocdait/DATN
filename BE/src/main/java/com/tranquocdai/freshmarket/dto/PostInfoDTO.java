package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.CommentPost;
import com.tranquocdai.freshmarket.model.Post;

import java.util.List;

public class PostInfoDTO {
    private Post post;

    private List<UserCommentDTO> userCommentDTOList;

    private float averageRate;

    private float score;

    public List<UserCommentDTO> getUserCommentDTOList() {
        return userCommentDTOList;
    }

    public void setUserCommentDTOList(List<UserCommentDTO> userCommentDTOList) {
        this.userCommentDTOList = userCommentDTOList;
    }

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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
