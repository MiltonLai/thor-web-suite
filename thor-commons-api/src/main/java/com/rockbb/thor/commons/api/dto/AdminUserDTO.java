package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.api.util.Constants;

public class AdminUserDTO extends BaseUserDTO {
    private String roleId;

    public AdminUserDTO anonymous() {
        super.anonymous();
        roleId      = "";
        return this;
    }

    public AdminUserDTO system() {
        this.setId(Constants.SYS_USER_ID);
        this.setName("system");
        roleId      = "";
        return this;
    }

    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }
}