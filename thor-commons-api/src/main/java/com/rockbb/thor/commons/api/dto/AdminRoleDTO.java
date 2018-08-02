package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.lib.utilities.SecureUtil;

import java.util.Date;
import java.io.Serializable;
import java.lang.String;

public class AdminRoleDTO implements Serializable {
    private String id;
    private String name;
    private String permissions;
    private String menus;
    private String notes;
    private Date updatedAt;
    private String updaterId;

    public AdminRoleDTO initialize() {
        id                            = SecureUtil.uuid();
        name                          = "";
        permissions                   = "";
        menus                         = "";
        notes                         = "";
        updatedAt                     = new Date();
        updaterId                     = "";
        return this;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPermissions() {return permissions;}
    public void setPermissions(String permissions) {this.permissions = permissions;}
    public String getMenus() { return menus; }
    public void setMenus(String menus) { this.menus = menus; }
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public Date getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(Date updatedAt) {this.updatedAt = updatedAt;}
    public String getUpdaterId() {return updaterId;}
    public void setUpdaterId(String updaterId) {this.updaterId = updaterId;}
}