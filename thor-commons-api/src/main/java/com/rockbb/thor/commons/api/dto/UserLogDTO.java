package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.lib.utilities.SecureUtil;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

public class UserLogDTO implements Serializable {
    public static final int ACTION_CODE_ERROR = 0;
    public static final int ACTION_PASSWORD_ERROR = 1;
    public static final int ACTION_SECURE_PASS_ERROR = 2;

    private String id;
    private String sessionId;
    private String userId;
    private String ip;
    private int actionType;
    private String secure1;
    private String secure2;
    private String secure3;
    private String notes;
    private Date createdAt;

    public UserLogDTO initialize() {
        id                            = SecureUtil.uuid();
        ip                            = "";
        actionType                    = 0;
        secure1                       = "";
        secure2                       = "";
        secure3                       = "";
        notes                         = "";
        createdAt                     = new Date();
        return this;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getSessionId() {return sessionId;}
    public void setSessionId(String sessionId) {this.sessionId = sessionId;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public int getActionType() {return actionType;}
    public void setActionType(int actionType) {this.actionType = actionType;}
    public String getSecure1() {return secure1;}
    public void setSecure1(String secure1) {this.secure1 = secure1;}
    public String getSecure2() {return secure2;}
    public void setSecure2(String secure2) {this.secure2 = secure2;}
    public String getSecure3() {return secure3;}
    public void setSecure3(String secure3) {this.secure3 = secure3;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public Date getCreatedAt() {return createdAt;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}
}