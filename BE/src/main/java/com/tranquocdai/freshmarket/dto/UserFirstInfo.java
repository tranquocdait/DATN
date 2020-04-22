package com.tranquocdai.freshmarket.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserFirstInfo {
    @NotEmpty
    @Size(min = 2)
    private String userName;

    @NotEmpty
    @Size(min = 8)
    private String password;

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
