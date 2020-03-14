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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.OneToOne;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

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
    public ResponseEntity getAllPost(){
        List<Post> postList=postRepository.findAll();
        return ResponseEntity.ok(postList);
    }

    @PostMapping("/posts")
    public ResponseEntity readPost(Authentication authentication){
        try {
            User user = baseService.getUser(authentication).get();
            Collection<Post> posts=postRepository.findByUser(user);
            return new ResponseEntity(new SuccessfulResponse(PostDTO.convertListPost(posts)), HttpStatus.OK);
        }catch (Exception ex){
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
        }catch (Exception ex){
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/posts/create")
    public ResponseEntity createPost(Authentication authentication,AddPostDTO addPostDTO) {
     try {
            User user=baseService.getUser(authentication).get();
            Post post=new Post();
            post.setPostName(addPostDTO.getPostName());
            post.setAddress(addPostDTO.getAddress());
            post.setUnitPrice(addPostDTO.getUnitPrice());
            post.setDateOfPost(LocalDateTime.now());
            post.setDistrictId(addPostDTO.getDistrictId());
            post.setDateOfPost(LocalDateTime.now());
            TypePost typePost=typePostRepository.findById(addPostDTO.getTypePostID()).get();
            post.setTypePost(typePost);
            Province province=provinceRepository.findById(addPostDTO.getProvinceID()).get();
            post.setProvince(province);
            CalculationUnit calculationUnit=calculationUnitRepository.findById(addPostDTO.getCalculationUnitID()).get();
            post.setCalculationUnit(calculationUnit);
            Category category=categoryRepository.findById(addPostDTO.getCategoryID()).get();
            post.setCategory(category);
            postRepository.save(post);
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        }catch (Exception ex){
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}
class AddPostDTO{
    private String postName;

    private String description;

    private String unitPrice;

    private String districtId;

    private String address;

    private Long calculationUnitID;

    private Long typePostID;

    private Long categoryID;

    private Long provinceID;

    private String imageBase64;

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

    public Long getTypePostID() {
        return typePostID;
    }

    public void setTypePostID(Long typePostID) {
        this.typePostID = typePostID;
    }

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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
