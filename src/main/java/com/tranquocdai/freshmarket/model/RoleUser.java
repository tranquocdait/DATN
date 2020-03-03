package com.tranquocdai.freshmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="role_user")
public class RoleUser {
    @Id
    @Column(name = "role_id")
    private String roleID;

    @NotEmpty
    @JsonIgnore
    @Size(min = 2)
    @Column(name = "role_name")
    private String roleName;

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
