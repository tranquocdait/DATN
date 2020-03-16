package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.dto.PostDTO;
import com.tranquocdai.freshmarket.model.*;
import com.tranquocdai.freshmarket.repository.*;
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
    BaseService baseService;

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
            User user=baseService.getUser(authentication).get();
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
        Optional<Post> optionalPost = postRepository.findById(id);
        try {
            if (!optionalPost.isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("id", "id is not existed");
                return new ResponseEntity(new ErrorResponse(errors),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new SuccessfulResponse(PostDTO.converPost(optionalPost)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/create")
    public ResponseEntity createPost(Authentication authentication, @Valid @RequestBody AddPostDTO addPostDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            Post post = new Post();
            post.setUser(user);
            post.setPostName(addPostDTO.getPostName());
            post.setAddress(addPostDTO.getAddress());
            post.setUnitPrice(addPostDTO.getUnitPrice());
            post.setDateOfPost(LocalDateTime.now());
            post.setDistrictId(addPostDTO.getDistrictId());
            post.setDateOfPost(LocalDateTime.now());
            post.setWeightOfItem(addPostDTO.getWeightOfItem());
            //TypePost typePost = typePostRepository.findById(addPostDTO.getTypePostID()).get();
           // post.setTypePost(typePost);
            Province province = provinceRepository.findById(addPostDTO.getProvinceID()).get();
            post.setProvince(province);
            CalculationUnit calculationUnit = calculationUnitRepository.findById(addPostDTO.getCalculationUnitID()).get();
            post.setCalculationUnit(calculationUnit);
            Category category = categoryRepository.findById(addPostDTO.getCategoryID()).get();
            post.setCategory(category);
            postRepository.save(post);
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/posts/update")
    public ResponseEntity updatePost(Authentication authentication, @Valid @RequestBody UpdatePostDTO updatePostDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            if(!postRepository.findByUserAndId(user,updatePostDTO.getId()).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
                Post post = postRepository.findByUserAndId(user, updatePostDTO.getId()).get();
                post.setPostName(updatePostDTO.getPostName());
                post.setAddress(updatePostDTO.getAddress());
                post.setUnitPrice(updatePostDTO.getUnitPrice());
                post.setDateOfPost(LocalDateTime.now());
                post.setDistrictId(updatePostDTO.getDistrictId());
                post.setDateOfPost(LocalDateTime.now());
                post.setWeightOfItem(updatePostDTO.getWeightOfItem());
                //TypePost typePost = typePostRepository.findById(addPostDTO.getTypePostID()).get();
                // post.setTypePost(typePost);
                Province province = provinceRepository.findById(updatePostDTO.getProvinceID()).get();
                post.setProvince(province);
                CalculationUnit calculationUnit = calculationUnitRepository.findById(updatePostDTO.getCalculationUnitID()).get();
                post.setCalculationUnit(calculationUnit);
                Category category = categoryRepository.findById(updatePostDTO.getCategoryID()).get();
                post.setCategory(category);
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
                errors.put("message", "post has not existed");
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

class AddPostDTO {
    private String postName;

    private String description;

    private Double unitPrice;

    private Long districtId;

    private String address;

    private Long calculationUnitID;

    //private Long typePostID;

    private Long categoryID;

    private Long provinceID;

    private Double weightOfItem;

    private String imageBase64;

    public Double getWeightOfItem() {
        return weightOfItem;
    }

    public void setWeightOfItem(Double weightOfItem) {
        this.weightOfItem = weightOfItem;
    }

    public Long getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Long provinceID) {
        this.provinceID = provinceID;
    }

    public Long getCalculationUnitID() {
        return calculationUnitID;
    }

    public void setCalculationUnitID(Long calculationUnitID) {
        this.calculationUnitID = calculationUnitID;
    }

   /* public Long getTypePostID() {
        return typePostID;
    }

    public void setTypePostID(Long typePostID) {
        this.typePostID = typePostID;
    }*/

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
class UpdatePostDTO {
    private Long id;

    private String postName;

    private String description;

    private Double unitPrice;

    private Long districtId;

    private String address;

    private Long calculationUnitID;

    //private Long typePostID;

    private Long categoryID;

    private Long provinceID;

    private Double weightOfItem;

    private String imageBase64;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeightOfItem() {
        return weightOfItem;
    }

    public void setWeightOfItem(Double weightOfItem) {
        this.weightOfItem = weightOfItem;
    }

    public Long getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Long provinceID) {
        this.provinceID = provinceID;
    }

    public Long getCalculationUnitID() {
        return calculationUnitID;
    }

    public void setCalculationUnitID(Long calculationUnitID) {
        this.calculationUnitID = calculationUnitID;
    }

   /* public Long getTypePostID() {
        return typePostID;
    }

    public void setTypePostID(Long typePostID) {
        this.typePostID = typePostID;
    }*/

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}