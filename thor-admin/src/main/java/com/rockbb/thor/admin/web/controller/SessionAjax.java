package com.rockbb.thor.admin.web.controller;

import com.rockbb.thor.admin.web.base.BasicSessionController;
import com.rockbb.thor.admin.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 登录页
 *
 * Created by Milton on 2015/7/18 at 17:34.
 */
@Controller
public class SessionAjax extends BasicSessionController {
    private static final Logger logger = LoggerFactory.getLogger(SessionAjax.class);

    @Resource(name = "sessionManager")
    private SessionManager sessionManager;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String doLoginPost(
            @ModelAttribute("requestBean") RequestBean rb,
            @ModelAttribute("sessionBean") SessionBean sb) {
        String email = rb.get("inputEmail");
        String password = rb.get("inputPassword");
        int autoLogin = rb.getInt("autologin");

        ResultDTO result = sessionManager.adminLogin(
                sb.getSession().getId(),
                email,
                password,
                autoLogin,
                rb.getIp(),
                rb.getUserAgent());

        if (result.isFailed()) {
            return rb.jsonInfo(result.getMessage());
        } else {
            return rb.jsonSuccess("登录成功, 请等待跳转", rb.baseLink());
        }
    }
}
