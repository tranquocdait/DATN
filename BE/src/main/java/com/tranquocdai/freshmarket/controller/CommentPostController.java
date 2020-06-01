package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.dto.CommentPostDTO;
import com.tranquocdai.freshmarket.model.CommentPost;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.CommentPostRepository;
import com.tranquocdai.freshmarket.repository.PostRepository;
import com.tranquocdai.freshmarket.repository.UserRepository;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class CommentPostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentPostRepository commentPostRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BaseService baseService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity getComment(@PathVariable("postId") Long id) {
        try {
            if (!postRepository.findById(id).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("id", "id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            Optional<Post> optionalPost = postRepository.findById(id);
            Collection<CommentPost> commentPost = commentPostRepository.findByPost(optionalPost.get());
            return new ResponseEntity(new SuccessfulResponse(CommentPostDTO.converCommentPost(commentPost)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/getAllComment")
    public ResponseEntity getAllComment() {
        try {
            Collection<CommentPost> commentPost = commentPostRepository.findAll();
            return new ResponseEntity(new SuccessfulResponse(CommentPostDTO.converCommentPost(commentPost)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity createCommentByID(@PathVariable("postId") Long id, @Valid @RequestBody CommentPostDTO commentPostDTO) {
        try {
            if (!postRepository.findById(id).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("id", "post id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            Post post = postRepository.findById(id).get();
            CommentPost commentPost = new CommentPost();
            commentPost.setPost(post);
            commentPost.setContent(commentPostDTO.getContent());
            User user = userRepository.findByUserName(commentPostDTO.getUsername()).get();
            commentPost.setUser(user);
            commentPost.setDateOfComment(LocalDateTime.now());
            commentPostRepository.save(commentPost);
            return new ResponseEntity(new SuccessfulResponse(commentPost), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/comments")
    public ResponseEntity createComment(Authentication authentication, @Valid @RequestBody CommentPostDTO commentPostDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            Post post = postRepository.findById(commentPostDTO.getPostID()).get();
            CommentPost commentPost = new CommentPost();
            if (commentPostRepository.findByPostAndUser(post, user).isPresent()) {
                commentPost = commentPostRepository.findByPostAndUser(post, user).get();
                commentPost.setContent(commentPostDTO.getContent());
            } else {
                commentPost.setUser(user);
                commentPost.setPost(post);
            }
            commentPost.setContent(commentPostDTO.getContent());
            commentPost.setDateOfComment(LocalDateTime.now());
            commentPostRepository.save(commentPost);
            return new ResponseEntity(new SuccessfulResponse(commentPost), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/posts/{commentID}/comments")
    public ResponseEntity updateComment(@PathVariable("commentID") Long commentID, Authentication authentication, @Valid @RequestBody CommentPostDTO commentPostDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            if (!commentPostRepository.findByIdAndUser(commentID, user).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "comment has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            CommentPost commentPost = commentPostRepository.findByIdAndUser(commentID, user).get();
            commentPost.setContent(commentPostDTO.getContent());
            commentPost.setDateOfComment(LocalDateTime.now());
            commentPostRepository.save(commentPost);
            return new ResponseEntity(new SuccessfulResponse(commentPost), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/posts/{commentID}/comments")
    public ResponseEntity deleteComment(@PathVariable("commentID") Long commentID, Authentication authentication) {
        try {
            User user = baseService.getUser(authentication).get();
            if (!commentPostRepository.findByIdAndUser(commentID, user).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "comment has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            commentPostRepository.deleteById(commentID);
            return new ResponseEntity(new SuccessfulResponse("delete successfully"), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

}
