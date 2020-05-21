package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.config.Constants;
import com.tranquocdai.freshmarket.model.Avatar;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.RoleUser;
import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.AvatarRepository;
import com.tranquocdai.freshmarket.repository.PostRepository;
import com.tranquocdai.freshmarket.repository.RoleResponsitory;
import com.tranquocdai.freshmarket.repository.UserRepository;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
    RoleResponsitory roleResponsitory;

    @Autowired
    PostRepository postRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    AvatarRepository avatarRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String sayHello() {
        return "Swagger Hello World";
    }

    @GetMapping("/admin")
    public ResponseEntity readAdmin() {
        List<RoleUser> userList = roleResponsitory.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/pagedingPost")
    public ResponseEntity pagedingPost() {
        Page<Post> postList = postRepository.findAll(PageRequest.of(0, 5, Sort.by("dateOfPost")));
        return ResponseEntity.ok().header("total", postList.getTotalPages()+"")
                .header("pageCurrent",postList.getNumber()+"")
                .body(new SuccessfulResponse(postList.getContent()));
    }

    @GetMapping("/demoUser/{search}")
    public ResponseEntity readUserDemo(@PathVariable("search") String search) {
        List<User> userList = userRepository.findSearchUsers('%'+search+'%');
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/images/add")
    public ResponseEntity testLoadImage(@RequestBody String imageBase64) {
        String newImageUrl = storageService.store(imageBase64);
        Avatar avatar = new Avatar();
        avatar.setUrl(newImageUrl);
        Object result = avatarRepository.save(avatar);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/images/delete")
    public ResponseEntity deleteImage(@RequestBody String url) {
        storageService.delete(url);
        return ResponseEntity.ok(Constants.URL_AVATAR_DEFAULT);
    }
}