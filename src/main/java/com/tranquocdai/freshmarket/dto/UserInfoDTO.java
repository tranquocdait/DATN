package com.tranquocdai.freshmarket.dto;

import com.tranquocdai.freshmarket.model.*;

public class UserInfoDTO {
    private Long userID;

    private String userName;

    private String fullName;

    private RoleUser roleUser;

    private Avatar avatar;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RoleUser getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(RoleUser roleUser) {
        this.roleUser = roleUser;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public static UserInfoDTO converUser(User user){
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        userInfoDTO.userID=user.getUserID();
        userInfoDTO.userName=user.getUserName();
        userInfoDTO.fullName=user.getFullName();
        userInfoDTO.roleUser=user.getRoleUser();
        userInfoDTO.avatar=user.getAvatar();
        return userInfoDTO;
    }
}
