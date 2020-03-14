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

    private User user;

    private Post post;

    private LocalDateTime dateOfComment;

    private String content;
    public static ArrayList<CommentPost> converCommentPost(Collection<CommentPost> posts){
        ArrayList<CommentPost> postArrayList=new ArrayList<>(posts);
        postArrayList.forEach((element)->{
            element.getUser().setPassword("");
            element.setPost(null);
        });
        return postArrayList;
    }
}
