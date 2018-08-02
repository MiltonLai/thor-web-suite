package com.rockbb.thor.app.vo;

import com.rockbb.thor.app.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.lib.utilities.StringUtil;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by Milton on 2016/9/20.
 */
public class Session {
    // SessionDTO variables
    private String id;
    private String userId;
    private String token;
    private long startedAt;
    private long updatedAt;

    // BaseUserDTO variables
    private int type;
    private String name;
    private String email;
    private String realName;
    private String cellphone;
    private int status;
    private Date createdAt;

    // UserDTO variables
    private int emailChecked;
    private int cellphoneChecked;
    private int identityType;
    private String identityNumber;
    private int identityChecked;

    public void mask() {
        name = StringUtil.maskCellphone(name);
        realName = StringUtil.maskRealName(realName);
        identityNumber = StringUtil.maskIdCardNumber(identityNumber);
    }

    public static Session adapt(final SessionDTO dto, final UserDTO user) {
        Session session = new Session();
        BeanUtils.copyProperties(dto, session);
        BeanUtils.copyProperties(user, session, "id");
        session.mask();
        return session;
    }

    public static Session adapt(final SessionBean sb) {
        Session session = new Session();
        BeanUtils.copyProperties(sb.getSession(), session);
        BeanUtils.copyProperties(sb.getUser(), session, "id");
        session.mask();
        return session;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public long getStartedAt() { return startedAt; }
    public void setStartedAt(long startedAt) { this.startedAt = startedAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
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
    public int getEmailChecked() { return emailChecked; }
    public void setEmailChecked(int emailChecked) { this.emailChecked = emailChecked; }
    public int getCellphoneChecked() { return cellphoneChecked; }
    public void setCellphoneChecked(int cellphoneChecked) { this.cellphoneChecked = cellphoneChecked; }
    public int getIdentityType() { return identityType; }
    public void setIdentityType(int identityType) { this.identityType = identityType; }
    public String getIdentityNumber() { return identityNumber; }
    public void setIdentityNumber(String identityNumber) { this.identityNumber = identityNumber; }
    public int getIdentityChecked() { return identityChecked; }
    public void setIdentityChecked(int identityChecked) { this.identityChecked = identityChecked; }
}
