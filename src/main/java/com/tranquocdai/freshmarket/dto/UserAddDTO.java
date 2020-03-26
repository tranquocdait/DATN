package com.tranquocdai.freshmarket.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserAddDTO {
    @NotEmpty
    @Size(min = 2)
    private String userName;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotEmpty
    private String fullName;

    @Size(min = 9)
    private String phoneNumber;

    @Size(min = 2)
    private String email;

    private Long roleID;

    private String imageBase64;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }
}
