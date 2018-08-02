package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.lib.utilities.SecureUtil;

import java.io.Serializable;
import java.util.Date;

public class SessionLogDTO implements Serializable {
    private String id;
    private String sessionId;
    private String app;
    private String userId;
    private String ip;
    private int autologin;
    private int language;
    private String secure1;
    private String secure2;
    private String secure3;
    private Date createdAt;

    public SessionLogDTO initialize() {
        id                            = SecureUtil.uuid();
        createdAt                     = new Date();
        return this;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getSessionId() {return sessionId;}
    public void setSessionId(String sessionId) {this.sessionId = sessionId;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getIp() {return ip;}
    public void setIp(String ip) {this.ip = ip;}
    public String getApp() {return app;}
    public void setApp(String app) {this.app = app;}
    public int getAutologin() {return autologin;}
    public void setAutologin(int autologin) {this.autologin = autologin;}
    public int getLanguage() {return language;}
    public void setLanguage(int language) {this.language = language;}
    public String getSecure1() {return secure1;}
    public void setSecure1(String secure1) {this.secure1 = secure1;}
    public String getSecure2() {return secure2;}
    public void setSecure2(String secure2) {this.secure2 = secure2;}
    public String getSecure3() {return secure3;}
    public void setSecure3(String secure3) {this.secure3 = secure3;}
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}