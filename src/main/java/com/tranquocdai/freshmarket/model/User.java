package com.tranquocdai.freshmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class User {

    @Id
    @Column(name="user_id")
    private String userID;

    @NotEmpty
    @JsonIgnore
    @Size(min = 2)
    @Column(name="user_name")
    private String userName;

    @NotEmpty
    @JsonIgnore
    @Size(min = 8)
    private String password;

    @NotEmpty
    @Size(min = 2)
    private String fullName;

    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleUser roleUser;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
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
