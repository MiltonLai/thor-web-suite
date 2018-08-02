package com.rockbb.thor.admin.vo;


import com.rockbb.thor.commons.api.dto.AdminRoleDTO;

import java.util.ArrayList;
import java.util.List;

public class AdminRole {
    private AdminRoleDTO dto;
    private List<Integer> permissionList;
    private List<String> menuList;

    public AdminRole(AdminRoleDTO dto) {
        this.dto = dto;
        permissionList = new ArrayList<>();
        menuList = new ArrayList<>();
    }

    public AdminRole() {}

    public AdminRole initialize() {
        dto = new AdminRoleDTO().initialize();
        permissionList = new ArrayList<>();
        menuList = new ArrayList<>();
        return this;
    }

    public AdminRoleDTO getDto() {
        return dto;
    }

    public void setDto(AdminRoleDTO dto) {
        this.dto = dto;
    }

    public List<Integer> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Integer> permissionList) {
        this.permissionList = permissionList;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }
}
