package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


public class PostDTO {
    public static ArrayList<Post> convertListPost(Collection<Post> posts){
        ArrayList<Post> postArrayList=new ArrayList<>(posts);
        postArrayList.forEach((element)->element.getUser().setPassword(""));
        return postArrayList;
    }
    public static Post converPost(Optional<Post> post){
        Post result=post.get();
        result.getUser().setPassword("");
        return result;
    }
}
