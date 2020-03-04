package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity readAdmin(){
        List<User> userList=userRepository.findAll();
    return ResponseEntity.ok(userList);
    }
}
