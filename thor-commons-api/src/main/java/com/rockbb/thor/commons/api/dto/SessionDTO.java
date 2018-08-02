package com.rockbb.thor.commons.api.dto;

import com.rockbb.thor.commons.lib.utilities.SecureUtil;

import java.io.Serializable;

public class SessionDTO implements Serializable {
    private String id;
    /** 访问介质: web, mobile, Android, iOS, etc. */
    private String app;
    private String userId;
    private long startedAt;
    private long updatedAt;
    private int language;
    private int autologin;
    private String token;
    private String secure1;
    private String secure2;
    private String secure3;

    public SessionDTO initialize() {
        id                            = SecureUtil.uuid();
        app                           = "";
        userId                        = "";
        startedAt                     = System.currentTimeMillis();
        updatedAt                     = startedAt;
        language                      = 0;
        autologin                     = 0;
        token                         = SecureUtil.uuid();
        secure1                       = "";
        secure2                       = "";
        secure3                       = "";
        return this;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getApp() { return app; }
    public void setApp(String app) { this.app = app; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public long getStartedAt() { return startedAt; }
    public void setStartedAt(long startedAt) { this.startedAt = startedAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public int getLanguage() { return language; }
    public void setLanguage(int language) { this.language = language; }
    public int getAutologin() { return autologin; }
    public void setAutologin(int autologin) { this.autologin = autologin; }
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public String getSecure1() { return secure1; }
    public void setSecure1(String secure1) { this.secure1 = secure1; }
    public String getSecure2() { return secure2; }
    public void setSecure2(String secure2) { this.secure2 = secure2; }
    public String getSecure3() { return secure3; }
    public void setSecure3(String secure3) { this.secure3 = secure3; }
}