package com.rockbb.thor.admin.web.controller;

import com.rockbb.thor.admin.web.base.BasicSessionPageController;
import com.rockbb.thor.admin.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.lib.web.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class SessionController extends BasicSessionPageController
{
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Resource(name = "sessionManager")
    private SessionManager sessionManager;

    @RequestMapping("/logout")
    public String doLogout(@ModelAttribute("sessionBean") SessionBean sb) {
        sessionManager.adminLogout(sb.getSession().getId());
        return "redirect:/";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String doLogin(
            @ModelAttribute("sessionBean") SessionBean sb,
            @ModelAttribute("pageBean") PageBean pb) {
        SessionDTO session = sb.getSession();
        if (session == null || (sb.getUser() != null && !sb.getUser().getId().equals(AdminUserDTO.ANONYMOUS_UID)))
            return "redirect:/";

        pb.addMetaCSS("/assets/css/signin.css");
        return "index/login";
    }
}
