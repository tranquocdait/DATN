package com.tranquocdai.freshmarket.service;

import com.tranquocdai.freshmarket.model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface BaseService {
    Optional<User> getUser(Authentication authentication);
}
