package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.dto.RatePostDTO;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.RatePost;
import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.PostRepository;
import com.tranquocdai.freshmarket.repository.RatePostRepository;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RatePostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    RatePostRepository ratePostRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BaseService baseService;

    @GetMapping("/posts/{postId}/rates")
    public ResponseEntity getRate(@PathVariable("postId") Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        try {
            if (!optionalPost.isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            Collection<RatePost> ratePosts = ratePostRepository.findByPost(optionalPost.get());
            return new ResponseEntity(ratePosts, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/{postId}/rates")
    public ResponseEntity createRateByID(@PathVariable("postId") Long id, @Valid @RequestBody RatePostDTO ratePostDTO) {
        try {
            if (!postRepository.findById(id).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "post id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            Post post = postRepository.findById(id).get();
            User user = userRepository.findByUserName(ratePostDTO.getUsername()).get();
            if(ratePostRepository.findByPostAndUser(post,user).isPresent()){
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "post have done evaluated");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            RatePost ratePost = new RatePost();
            ratePost.setPost(post);
            ratePost.setRateNumber(ratePostDTO.getRateNumber());
            ratePost.setUser(user);
            ratePostRepository.save(ratePost);
            return new ResponseEntity(new SuccessfulResponse(ratePost), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/rates")
    public ResponseEntity createRate(Authentication authentication, @Valid @RequestBody RatePostDTO ratePostDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            Post post = postRepository.findById(ratePostDTO.getPostId()).get();
            if(ratePostRepository.findByPostAndUser(post,user).isPresent()){
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "post have done evaluated");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            RatePost ratePost = new RatePost();
            ratePost.setUser(user);
            ratePost.setPost(post);
            ratePost.setRateNumber(ratePostDTO.getRateNumber());
            ratePostRepository.save(ratePost);
            return new ResponseEntity(new SuccessfulResponse(ratePost), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/posts/{rateId}/rates")
    public ResponseEntity updateRate(@PathVariable("rateId") Long rateId, Authentication authentication, @Valid @RequestBody RatePostDTO ratePostDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            if (!ratePostRepository.findByIdAndUser(rateId, user).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "id has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            RatePost ratePost = ratePostRepository.findByIdAndUser(rateId, user).get();
            ratePost.setRateNumber(ratePostDTO.getRateNumber());
            ratePostRepository.save(ratePost);
            return new ResponseEntity(new SuccessfulResponse(ratePost), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/posts/rates/{rateId}")
    public ResponseEntity deleteRate(@PathVariable("rateId") Long rateId, Authentication authentication) {
        try {
            User user = baseService.getUser(authentication).get();
            if (!ratePostRepository.findByIdAndUser(rateId, user).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "rate id has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            ratePostRepository.deleteById(rateId);
            return new ResponseEntity(new SuccessfulResponse("delete successfully"), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}
