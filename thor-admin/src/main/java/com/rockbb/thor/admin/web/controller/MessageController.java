package com.rockbb.thor.admin.web.controller;

import com.rockbb.thor.admin.web.base.BasicPageController;
import com.rockbb.thor.commons.lib.web.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Created by Milton on 2015/8/26 at 21:44.
 */
@Controller
@RequestMapping("/util")
public class MessageController extends BasicPageController {
    @RequestMapping("/message")
    public String showMessage(
            @ModelAttribute("pageBean") PageBean pb,
            @RequestParam(value = "type", required = false) int type,
            @RequestParam(value = "redirect", required = false) String redirect,
            @RequestParam(value = "delay", required = false) Integer delay) {
        String title = "错误请求";
        String content = "您访问的页面有误";
        switch(type) {
            case 1:
                title = "无管理员权限";
                content = "您不是管理员, 无法访问此网页.";
                break;
            case 2:
                title = "未设置管理角色";
                content = "您的账号尚未设置管理员角色, 无法访问此网页, 如果有疑问请联系系统管理员.";
                break;
            case 3:
                title = "无管理权限";
                content = "您的管理员账号无权限访问此网页, 如果有疑问请联系系统管理员.";
                break;
        }

        if (delay == null) delay = 2;
        return messagePage(
                pb,
                title,
                content,
                redirect,
                delay);
    }
}
