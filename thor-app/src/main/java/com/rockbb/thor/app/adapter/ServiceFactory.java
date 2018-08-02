package com.rockbb.thor.app.adapter;

import com.rockbb.thor.commons.api.service.UserDTOService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service("serviceFactory")
public class ServiceFactory {
    private static ServiceFactory f;

    @Resource(name = "userDTOService")
    private UserDTOService userDTOService;

    @PostConstruct
    public void init() {
        f = this;
        f.userDTOService = this.userDTOService;
    }

    public static UserDTOService getUserDTOService() {
        return f.userDTOService;
    }
}
