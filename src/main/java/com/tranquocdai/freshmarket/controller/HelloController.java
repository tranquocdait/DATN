package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.model.RoleUser;
import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.RoleResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    RoleResponsitory roleResponsitory;
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String sayHello() {
        return "Swagger Hello World";
    }
    @GetMapping("/roles")
    public ResponseEntity readAdmin(){
        List<RoleUser> userList=roleResponsitory.findAll();
        return ResponseEntity.ok(userList);
    }
}