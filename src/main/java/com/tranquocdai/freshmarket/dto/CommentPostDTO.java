package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.CommentPost;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CommentPostDTO {
    private Long id;

    private String username;

    private Long postID;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static ArrayList<CommentPost> converCommentPost(Collection<CommentPost> posts){
        ArrayList<CommentPost> postArrayList=new ArrayList<>(posts);
        postArrayList.forEach((element)->{
            element.getUser().setPassword("");
            element.setPost(null);
        });
        return postArrayList;
    }
}
