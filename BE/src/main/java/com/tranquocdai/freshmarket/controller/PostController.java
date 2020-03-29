package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.config.Constants;
import com.tranquocdai.freshmarket.dto.PostDTO;
import com.tranquocdai.freshmarket.model.*;
import com.tranquocdai.freshmarket.repository.*;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.BaseService;
import com.tranquocdai.freshmarket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    TypePostRepository typePostRepository;

    @Autowired
    CalculationUnitRepository calculationUnitRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    ImagePostRepository imagePostRepository;

    @Autowired
    BaseService baseService;

    @Autowired
    StorageService storageService;

    @GetMapping("/posts")
    public ResponseEntity getAllPost() {
        try {
            Collection<Post> postList = postRepository.findAll();
            return new ResponseEntity(new SuccessfulResponse(PostDTO.convertListPost(postList)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/posts")
    public ResponseEntity readAllPost(Authentication authentication) {
        try {
            User user = baseService.getUser(authentication).get();
            Collection<Post> postList = postRepository.findByUser(user);
            return new ResponseEntity(new SuccessfulResponse(PostDTO.convertListPost(postList)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity readPost(Authentication authentication) {
        try {
            User user = baseService.getUser(authentication).get();
            Collection<Post> posts = postRepository.findByUser(user);
            return new ResponseEntity(new SuccessfulResponse(PostDTO.convertListPost(posts)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") Long id) {
        try {
            if (!postRepository.findById(id).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("id", "id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            Optional<Post> optionalPost = postRepository.findById(id);
            return new ResponseEntity(new SuccessfulResponse(PostDTO.converPost(optionalPost)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/create")
    public ResponseEntity createPost(Authentication authentication, @Valid @RequestBody PostDTO postAddDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            Post post = new Post();
            post.setUser(user);
            post.setPostName(postAddDTO.getPostName());
            post.setAddress(postAddDTO.getAddress());
            post.setUnitPrice(postAddDTO.getUnitPrice());
            post.setDateOfPost(LocalDateTime.now());
            post.setDistrictId(postAddDTO.getDistrictId());
            post.setDateOfPost(LocalDateTime.now());
            post.setWeightOfItem(postAddDTO.getWeightOfItem());
            //TypePost typePost = typePostRepository.findById(addPostDTO.getTypePostID()).get();
            // post.setTypePost(typePost);
            Province province = provinceRepository.findById(postAddDTO.getProvinceID()).get();
            post.setProvince(province);
            CalculationUnit calculationUnit = calculationUnitRepository.findById(postAddDTO.getCalculationUnitID()).get();
            post.setCalculationUnit(calculationUnit);
            Category category = categoryRepository.findById(postAddDTO.getCategoryID()).get();
            post.setCategory(category);
            ImagePost imagePost = new ImagePost();
            if (postAddDTO.getImageBase64()!=null) {
                String newImageUrl = storageService.store(postAddDTO.getImageBase64());
                imagePost.setUrl(newImageUrl);
                imagePostRepository.save(imagePost);
            }else {
                imagePost=imagePostRepository.findById(Constants.ID_IMAGE_DEFAULT).get();
            }
            post.setImagePost(imagePost);
            postRepository.save(post);
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/posts/update")
    public ResponseEntity updatePost(Authentication authentication, @Valid @RequestBody PostDTO postUpdateDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            if (!postRepository.findByUserAndId(user, postUpdateDTO.getId()).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            Post post = postRepository.findByUserAndId(user, postUpdateDTO.getId()).get();
            post.setPostName(postUpdateDTO.getPostName());
            post.setAddress(postUpdateDTO.getAddress());
            post.setUnitPrice(postUpdateDTO.getUnitPrice());
            post.setDateOfPost(LocalDateTime.now());
            post.setDistrictId(postUpdateDTO.getDistrictId());
            post.setDescription(postUpdateDTO.getDescription());
            post.setWeightOfItem(postUpdateDTO.getWeightOfItem());
            //TypePost typePost = typePostRepository.findById(addPostDTO.getTypePostID()).get();
            // post.setTypePost(typePost);
            Province province = provinceRepository.findById(postUpdateDTO.getProvinceID()).get();
            post.setProvince(province);
            CalculationUnit calculationUnit = calculationUnitRepository.findById(postUpdateDTO.getCalculationUnitID()).get();
            post.setCalculationUnit(calculationUnit);
            Category category = categoryRepository.findById(postUpdateDTO.getCategoryID()).get();
            post.setCategory(category);
            if (postUpdateDTO.getImageBase64()!=null){
                ImagePost imagePost =post.getImagePost();
                if(Constants.URL_POST_DEFAULT.equals(imagePost.getUrl())){
                    ImagePost imagePostSave=new ImagePost();
                    String newImageUrl = storageService.store(postUpdateDTO.getImageBase64());
                    imagePostSave.setUrl(newImageUrl);
                    imagePostRepository.save(imagePostSave);
                    post.setImagePost(imagePostSave);
                }else {
                    storageService.delete(imagePost.getUrl());
                    String newImageUrl = storageService.store(postUpdateDTO.getImageBase64());
                    imagePost.setUrl(newImageUrl);
                    imagePostRepository.save(imagePost);
                    post.setImagePost(imagePost);
                }
            }
            postRepository.save(post);
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity updatePostbyID(@Valid @RequestBody PostDTO postUpdateDTO,@PathVariable("postId") Long postId) {
        try {
            if (!postRepository.findById(postId).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "id has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            Post post = postRepository.findById(postId).get();
            post.setPostName(postUpdateDTO.getPostName());
            post.setAddress(postUpdateDTO.getAddress());
            post.setUnitPrice(postUpdateDTO.getUnitPrice());
            post.setDateOfPost(LocalDateTime.now());
            post.setDistrictId(postUpdateDTO.getDistrictId());
            post.setDescription(postUpdateDTO.getDescription());
            post.setWeightOfItem(postUpdateDTO.getWeightOfItem());
            //TypePost typePost = typePostRepository.findById(addPostDTO.getTypePostID()).get();
            // post.setTypePost(typePost);
            Province province = provinceRepository.findById(postUpdateDTO.getProvinceID()).get();
            post.setProvince(province);
            CalculationUnit calculationUnit = calculationUnitRepository.findById(postUpdateDTO.getCalculationUnitID()).get();
            post.setCalculationUnit(calculationUnit);
            Category category = categoryRepository.findById(postUpdateDTO.getCategoryID()).get();
            post.setCategory(category);
            if (postUpdateDTO.getImageBase64()!=null){
                ImagePost imagePost =post.getImagePost();
                if(Constants.URL_POST_DEFAULT.equals(imagePost.getUrl())){
                    ImagePost imagePostSave=new ImagePost();
                    String newImageUrl = storageService.store(postUpdateDTO.getImageBase64());
                    imagePostSave.setUrl(newImageUrl);
                    imagePostRepository.save(imagePostSave);
                    post.setImagePost(imagePostSave);
                }else {
                    storageService.delete(imagePost.getUrl());
                    String newImageUrl = storageService.store(postUpdateDTO.getImageBase64());
                    imagePost.setUrl(newImageUrl);
                    imagePostRepository.save(imagePost);
                    post.setImagePost(imagePost);
                }
            }
            postRepository.save(post);
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{postID}/posts")
    public ResponseEntity deletePost(@PathVariable("postID") Long postID) {
        try {
            if (!postRepository.findById(postID).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "id has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            postRepository.deleteById(postID);
            return new ResponseEntity(new SuccessfulResponse("delete successfully"), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}