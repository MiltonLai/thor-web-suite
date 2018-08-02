package com.rockbb.thor.mobile.web.controller;

import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.lib.web.RequestBean;
import com.rockbb.thor.mobile.web.base.BasicSessionController;
import com.rockbb.thor.mobile.web.base.SessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class IndexAjax extends BasicSessionController {
    private static final Logger logger = LoggerFactory.getLogger(IndexAjax.class);

    @Resource(name = "sessionManager")
    private SessionManager sessionManager;

    /**
     * 处理登录提交表单
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String doLoginPost(
            HttpSession httpSession,
            @ModelAttribute(RequestBean.ATTR_KEY) RequestBean rb,
            @ModelAttribute(SessionBean.ATTR_KEY) SessionBean sb) {
        /*
        手机号码, 账户密码
         */
        String tel = rb.get("phone");
        if (tel.length() == 0) {
            return rb.jsonInfo("手机号不能为空");
        }
        String password = rb.get("pwd");
        if (password.length() == 0) {
            return rb.jsonInfo("密码不能为空");
        }

        String openId = null;
        if (httpSession.getAttribute("WEIXIN_OPEN_ID") != null) {
            openId = (String)httpSession.getAttribute("WEIXIN_OPEN_ID");
        }

        ResultDTO result = sessionManager.userLogin(
                sb.getSession().getId(),
                tel,
                password,
                null,
                0,
                rb.getIp(),
                rb.getUserAgent(),
                openId);

        if (result.isFailed()) {
            return rb.jsonInfo(result.getMessage(), result.getKey());
        } else {
            return rb.jsonSuccess("登录成功", rb.baseLink());
        }
    }

    @RequestMapping(value = {"/logout"}, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String doLogout(
            @ModelAttribute(RequestBean.ATTR_KEY) RequestBean rb,
            @ModelAttribute(SessionBean.ATTR_KEY) SessionBean sb) {
        sessionManager.userLogout(sb.getSession().getId());
        return rb.jsonSuccess("退出成功", rb.baseLink());
    }

}
