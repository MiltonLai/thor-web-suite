package com.rockbb.thor.admin.adapter;

import com.rockbb.thor.commons.api.service.AdminRoleDTOService;
import com.rockbb.thor.commons.api.service.UserDTOService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service("serviceFactory")
public class ServiceFactory {
    private static ServiceFactory f;

    @Resource(name = "adminRoleDTOService")
    private AdminRoleDTOService adminRoleDTOService;
    @Resource(name = "sessionAdapter")
    private SessionAdapter sessionAdapter;
    @Resource(name = "userDTOService")
    private UserDTOService userDTOService;

    @PostConstruct
    public void init() {
        f = this;
        f.adminRoleDTOService = this.adminRoleDTOService;
        f.sessionAdapter = this.sessionAdapter;
        f.userDTOService = this.userDTOService;
    }

    public static AdminRoleDTOService getUserRoleService() {
        return f.adminRoleDTOService;
    }

    public static SessionAdapter getSessionAdapter() {
        return f.sessionAdapter;
    }

    public static UserDTOService getUserDTOService() {
        return f.userDTOService;
    }
}
