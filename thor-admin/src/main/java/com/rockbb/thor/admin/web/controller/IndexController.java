package com.rockbb.thor.admin.web.controller;

import com.rockbb.thor.admin.adapter.AdminRoleAdapter;
import com.rockbb.thor.admin.vo.AdminRole;
import com.rockbb.thor.admin.web.base.BasicAuthPageController;
import com.rockbb.thor.admin.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.lib.web.PageBean;
import com.rockbb.thor.commons.lib.web.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController extends BasicAuthPageController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Resource(name="adminRoleAdapter")
    private AdminRoleAdapter adminRoleAdapter;
    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @RequestMapping("/index")
    public String doLanding(
            @ModelAttribute("pageBean") PageBean pb,
            @ModelAttribute("sessionBean") SessionBean sb) {
        AdminRole role = adminRoleAdapter.get(sb.getUser().getRoleId());
        if (role != null) {
            pb.addObject("role", role);
        }
        return "index/index";
    }

    @RequestMapping("/my_profile")
    public String doShowProfile(
            @ModelAttribute("pageBean") PageBean pb,
            @ModelAttribute("sessionBean") SessionBean sb) {
        // 用户角色
        AdminRole role = adminRoleAdapter.get(sb.getUser().getRoleId());
        if (role != null) {
            pb.addObject("role", role);
        }

        // 当前登录
        List<SessionDTO> sessions = sessionManager.list(new Pager(0, 30, 0, 0), sb.getSession().getUserId());
        pb.addObject("sessions", sessions);
        return "index/my_profile";
    }

    @RequestMapping("/my_visits")
    public String doShowVisits(
            @ModelAttribute("pageBean") PageBean pb,
            @ModelAttribute("sessionBean") SessionBean sb) {
        pb.addMetaCSS("/bootstrap-table/bootstrap-table.min.css");
        pb.addMetaJS("/bootstrap-table/bootstrap-table.js");
        pb.addMetaJS("/assets/js/moment-with-locales.js");
        return "index/my_visits";
    }
}
