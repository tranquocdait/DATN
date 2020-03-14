package com.tranquocdai.freshmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long userID;

    @Size(min = 2)
    @Column(name="User_Name")
    private String userName;

    @Size(min = 8)
    @Column(name="Password")
    private String password;

    @Size(min = 2)
    @Column(name="Full_Name")
    private String fullName;

    @OneToOne(targetEntity = RoleUser.class)
    private RoleUser roleUser;

    @OneToOne(targetEntity = Avatar.class)
    private Avatar avatar;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public RoleUser getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(RoleUser roleUser) {
        this.roleUser = roleUser;
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

}
