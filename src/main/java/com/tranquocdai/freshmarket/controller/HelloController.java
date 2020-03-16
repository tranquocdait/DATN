package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.model.Avatar;
import com.tranquocdai.freshmarket.model.RoleUser;
import com.tranquocdai.freshmarket.repository.AvatarRepository;
import com.tranquocdai.freshmarket.repository.RoleResponsitory;
import com.tranquocdai.freshmarket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    RoleResponsitory roleResponsitory;

    @Autowired
    StorageService storageService;

    @Autowired
    AvatarRepository avatarRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String sayHello() {
        return "Swagger Hello World";
    }
    @GetMapping("/admin")
    public ResponseEntity readAdmin(){
        List<RoleUser> userList=roleResponsitory.findAll();
        return ResponseEntity.ok(userList);
    }
    @PostMapping("/images")
    public ResponseEntity testLoadImage(@RequestBody String imageBase64){
        String newImageUrl = storageService.store(imageBase64);
        Avatar avatar=new Avatar();
        Object result = avatarRepository.save(avatar);
        return ResponseEntity.ok(result);
    }
}