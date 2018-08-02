package com.rockbb.thor.mobile.adapter;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service("serviceFactory")
public class ServiceFactory {
    private static ServiceFactory f;

    @Resource(name = "sessionAdapter")
    private SessionAdapter sessionAdapter;

    @PostConstruct
    public void init() {
        f = this;
        f.sessionAdapter = this.sessionAdapter;
    }

    public static SessionAdapter getSessionAdapter() {
        return f.sessionAdapter;
    }
}
