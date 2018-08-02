package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.lib.utilities.SecureUtil;
import com.rockbb.thor.commons.lib.utilities.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class BaseUserDTO implements Serializable {
    public static final String ANONYMOUS_UID = "0";

    private String id;
    private int type;
    private String name;
    private String email;
    private String realName;
    private String cellphone;
    private int status;
    private Date createdAt;
    private String creatorId;
    private Date updatedAt;
    private String updaterId;
    private long version;

    public BaseUserDTO anonymous() {
        id                            = ANONYMOUS_UID;
        type                          = 0;
        name                          = "Anonymous";
        email                         = "";
        realName                      = "";
        cellphone                     = "";
        status                        = 0;
        createdAt                     = new Date();
        creatorId                     = "";
        updatedAt                     = null;
        updaterId                     = null;
        version                       = 0L;
        return this;
    }

    public void mask() {
        realName = StringUtil.maskRealName(realName);
        cellphone = StringUtil.maskCellphone(cellphone);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getCellphone() { return cellphone; }
    public void setCellphone(String cellphone) { this.cellphone = cellphone; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public String getCreatorId() { return creatorId; }
    public void setCreatorId(String creatorId) { this.creatorId = creatorId; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public String getUpdaterId() { return updaterId; }
    public void setUpdaterId(String updaterId) { this.updaterId = updaterId; }
    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }

    public boolean getIsAnonymous() {
        return (id.equals(ANONYMOUS_UID))? true : false;
    }
}