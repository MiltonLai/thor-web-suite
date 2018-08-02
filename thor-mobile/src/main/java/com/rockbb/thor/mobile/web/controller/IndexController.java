package com.rockbb.thor.mobile.web.controller;

import com.rockbb.thor.commons.api.service.ConfigDTOService;
import com.rockbb.thor.commons.lib.web.PageBean;
import com.rockbb.thor.commons.lib.web.RequestBean;
import com.rockbb.thor.mobile.web.base.BasicSessionPageController;
import com.rockbb.thor.mobile.web.base.SessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController extends BasicSessionPageController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index")
    public String doLanding(
            @ModelAttribute(PageBean.ATTR_KEY) PageBean pb,
            @ModelAttribute(SessionBean.ATTR_KEY) SessionBean sb) {

        pb.addObject("title", "Demo");
        return "index/index";
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public String doLogin(
    		@ModelAttribute(RequestBean.ATTR_KEY) RequestBean rb,
            @ModelAttribute(SessionBean.ATTR_KEY) SessionBean sb,
            @ModelAttribute(PageBean.ATTR_KEY) PageBean pb) {

        pb.addObject("title", "Demo|Login");
        return "index/login";
    }

    /**
     * 处理由第三方页面跳转的中转, 根据授权获取用户资料, 当用户为已注册用户时, 自动登录
     */
    @RequestMapping("/redirect")
    public String doRedirect(
            HttpSession httpSession,
            @ModelAttribute(RequestBean.ATTR_KEY) RequestBean rb,
            @ModelAttribute(SessionBean.ATTR_KEY) SessionBean sb) {

        // 根据跳转目标前往相应页面
        String jump = rb.get("jump");
        logger.debug("Jump to: {}", jump);
        return "redirect:"+jump;
    }
}
