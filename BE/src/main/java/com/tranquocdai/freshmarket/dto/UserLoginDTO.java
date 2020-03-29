package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.Avatar;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.RoleUser;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Optional;

public class UserLoginDTO {
    private String userName;
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
