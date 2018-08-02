package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.lib.utilities.SecureUtil;

import java.io.Serializable;

public class AuthRuleDTO implements Serializable {
    private String id;
    private String name;
    private String regex;
    private int index;
    private int status;

    public AuthRuleDTO initialize() {
        id          = SecureUtil.uuid();
        name        = "";
        regex       = "";
        index       = 0;
        status      = 0;
        return this;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegex() { return regex; }
    public void setRegex(String regex) { this.regex = regex; }
    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}