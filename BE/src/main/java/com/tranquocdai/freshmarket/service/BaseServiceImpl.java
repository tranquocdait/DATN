package com.tranquocdai.freshmarket.service;

import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaseServiceImpl implements  BaseService{
    @Autowired
    UserRepository userRepository;
    @Override
    public Optional<User> getUser(Authentication authentication) {
        return userRepository.findByUserName(authentication.getPrincipal().toString());
    }
}
