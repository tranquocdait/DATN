package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.config.Constants;
import com.tranquocdai.freshmarket.dto.PostDTO;
import com.tranquocdai.freshmarket.dto.PostInfoDTO;
import com.tranquocdai.freshmarket.dto.UserCommentDTO;
import com.tranquocdai.freshmarket.model.*;
import com.tranquocdai.freshmarket.repository.*;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.BaseService;
import com.tranquocdai.freshmarket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
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
    CommentPostRepository commentPostRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    ImagePostRepository imagePostRepository;

    @Autowired
    RatePostRepository ratePostRepository;

    @Autowired
    BaseService baseService;

    @Autowired
    StorageService storageService;

    @GetMapping("/posts/{page}/getAll")
    public ResponseEntity getAllPost(@PathVariable("page") int page) {
        try {
            Page<Post> postPage = postRepository.findAll(PageRequest.of(page, 40, Sort.by("dateOfPost").descending()));
            return ResponseEntity.ok().header("total", postPage.getTotalPages()+"")
                    .header("pageCurrent",postPage.getNumber()+"")
                    .body(new SuccessfulResponse(convertListPost(postPage.getContent())));
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/{id}/category")
    public ResponseEntity getAllPostByCategory(@PathVariable("id") Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            List<Post> postList = postRepository.findByCategory(category);
            return new ResponseEntity(new SuccessfulResponse(convertListPost(postList)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/posts")
    public ResponseEntity readPost(Authentication authentication) {
        try {
            User user = baseService.getUser(authentication).get();
            List<Post> postList = postRepository.findByUser(user);
            return new ResponseEntity(new SuccessfulResponse(convertListPost(postList)), HttpStatus.OK);
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
            Post post = postRepository.findById(id).get();
            return new ResponseEntity(new SuccessfulResponse(converPost(post)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/search")
    public ResponseEntity getAllTourist(@RequestParam(value = "keySearch", defaultValue = "") String keyword) {
        try {
            List<Post> postList = postRepository.findByPostNameContains(keyword,PageRequest.of(0, 40, Sort.by("dateOfPost").descending()));
            return new ResponseEntity(new SuccessfulResponse(convertListPost(postList)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/create")
    public ResponseEntity createPost(Authentication authentication, @Valid @RequestBody PostDTO postAddDTO) {
        try {
            if (!baseService.getUser(authentication).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
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
            post.setDescription(postAddDTO.getDescription());
            Province province = provinceRepository.findById(postAddDTO.getProvinceID()).get();
            post.setProvince(province);
            CalculationUnit calculationUnit = calculationUnitRepository.findById(postAddDTO.getCalculationUnitID()).get();
            post.setCalculationUnit(calculationUnit);
            Category category = categoryRepository.findById(postAddDTO.getCategoryID()).get();
            post.setCategory(category);
            if (postAddDTO.getImageBase64s() != null && postAddDTO.getImageBase64s().size() > 0) {
                List<ImagePost> imagePosts=new ArrayList<>();
                postAddDTO.getImageBase64s().forEach(element -> {
                    ImagePost imagePost = new ImagePost();
                    String newImageUrl = storageService.store(element);
                    imagePost.setUrl(newImageUrl);
                    imagePost.setPost(post);
                    imagePosts.add(imagePost);
                });
                imagePostRepository.saveAll(imagePosts);
            } else {
                ImagePost imagePost = new ImagePost();
                imagePost.setUrl(Constants.URL_POST_DEFAULT);
                imagePost.setPost(post);
                imagePostRepository.save(imagePost);
            }
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
            if (!baseService.getUser(authentication).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
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
            postRepository.save(post);
            if (postUpdateDTO.getImageBase64s() != null && postUpdateDTO.getImageBase64s().size() > 0) {
                postUpdateDTO.getImageBase64s().forEach(element -> {
                    ImagePost imagePost = new ImagePost();
                    String newImageUrl = storageService.store(element);
                    imagePost.setUrl(newImageUrl);
                    imagePost.setPost(post);
                    imagePostRepository.save(imagePost);
                });
            } else {
                ImagePost imagePost = new ImagePost();
                imagePost.setUrl(Constants.URL_POST_DEFAULT);
                imagePost.setPost(post);
                imagePostRepository.save(imagePost);
            }
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity updatePostbyID(@Valid @RequestBody PostDTO postUpdateDTO, @PathVariable("postId") Long postId) {
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
            postRepository.save(post);
            if (postUpdateDTO.getImageBase64s() != null && postUpdateDTO.getImageBase64s().size() > 0) {
                postUpdateDTO.getImageBase64s().forEach(element -> {
                    ImagePost imagePost = new ImagePost();
                    String newImageUrl = storageService.store(element);
                    imagePost.setUrl(newImageUrl);
                    imagePost.setPost(post);
                    imagePostRepository.save(imagePost);
                });
            } else {
                ImagePost imagePost = new ImagePost();
                imagePost.setUrl(Constants.URL_POST_DEFAULT);
                imagePost.setPost(post);
                imagePostRepository.save(imagePost);
            }
            return new ResponseEntity(new SuccessfulResponse(post), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/posts/{postID}")
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

    public List<PostInfoDTO> convertListPost(List<Post> postList) {
        List<PostInfoDTO> postInfoDTOList = new ArrayList<>();
        for (Post post : postList) {
            PostInfoDTO postInfoDTO = new PostInfoDTO();
            post.getUser().setPassword("");
            postInfoDTO.setAverageRate(averageRate(post));
            postInfoDTO.setPost(post);
            postInfoDTOList.add(postInfoDTO);
        }
        return postInfoDTOList;
    }

    public PostInfoDTO converPost(Post post) {
        PostInfoDTO postInfoDTO = new PostInfoDTO();
        post.getUser().setPassword("");
        List<RatePost> ratePostList = ratePostRepository.findByPost(post);
        List<UserCommentDTO> userCommentDTOList = new ArrayList<>();
        for (RatePost element : ratePostList) {
            UserCommentDTO userCommentDTO = new UserCommentDTO();
            if (commentPostRepository.findByPostAndUser(post, element.getUser()).isPresent()) {
                CommentPost commentPost = commentPostRepository.findByPostAndUser(post, element.getUser()).get();
                userCommentDTO.setComment(commentPost.getContent());
            }
            userCommentDTO.setUser(element.getUser());
            userCommentDTO.setRate(element.getRateNumber());
            userCommentDTOList.add(userCommentDTO);
        }
        postInfoDTO.setUserCommentDTOList(userCommentDTOList);
        postInfoDTO.setPost(post);
        postInfoDTO.setAverageRate(averageRate(post));
        return postInfoDTO;
    }

    private float averageRate(Post post) {
        List<RatePost> ratePostList = ratePostRepository.findByPost(post);
        int sum = 0;
        if (ratePostList.size() > 0) {
            for (RatePost element : ratePostList) {
                sum += element.getRateNumber().intValue();
            }
            DecimalFormat df = new DecimalFormat("0.00");
            String averageRateStr = df.format((float) sum / ratePostList.size());
            return Float.parseFloat(averageRateStr);
        }
        return 0;
    }
}