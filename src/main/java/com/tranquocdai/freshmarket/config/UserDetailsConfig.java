package com.tranquocdai.freshmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tranquocdai.freshmarket.service.UserDetailsServiceImpl;

@Configuration
public class UserDetailsConfig {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
}
