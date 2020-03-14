package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.dto.CommentPostDTO;
import com.tranquocdai.freshmarket.model.CommentPost;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.repository.CommentPostRepository;
import com.tranquocdai.freshmarket.repository.PostRepository;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CommentPostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentPostRepository commentPostRepository;
    @GetMapping("/posts/comments/{postId}")
    public ResponseEntity getComment(@PathVariable("postId") Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Collection<CommentPost> commentPost=commentPostRepository.findByPost(optionalPost.get());
        try {
            if (!optionalPost.isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("id", "id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new SuccessfulResponse(CommentPostDTO.converCommentPost(commentPost)), HttpStatus.OK);
        }catch (Exception ex){
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}
