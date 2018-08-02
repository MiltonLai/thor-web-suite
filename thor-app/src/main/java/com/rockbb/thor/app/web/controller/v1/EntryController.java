package com.rockbb.thor.app.web.controller.v1;

import com.rockbb.thor.app.vo.Session;
import com.rockbb.thor.app.web.base.BasicSessionController;
import com.rockbb.thor.app.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.api.service.UserDTOService;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 允许匿名用户访问的接口, 如登录, 注册等
 *
 * Created by Milton on 2016/2/3 at 17:02.
 */
@RequestMapping("/v1/entry")
@Controller("v1.gatewayController")
public class EntryController extends BasicSessionController {

    @Resource(name = "sessionManager")
    private SessionManager sessionManager;

    /**
     * APP 用户登录
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ResponseEntity<String> doLogin(
            @ModelAttribute("requestBean") RequestBean rb,
            @ModelAttribute("sessionBean") SessionBean sb) {
        /*
        手机号码, 短信验证码, 账户密码
         */
        String password = rb.get("login_pwd");
        if (password.length() == 0) {
            return error("密码不能为空", "login_pwd");
        }

        String tel = rb.get("login_phone");
        if (tel.length() == 0) {
            return error("手机号不能为空", "login_phone");
        }

        ResultDTO result = sessionManager.userLogin(
                sb.getSession().getId(),
                tel,
                password,
                null,
                1,
                rb.getIp(),
                rb.get("s"),
                null);

        if (result.isFailed()) {
            return error(result.getMessage());
        } else {
            return success();
        }
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResponseEntity<String> doLogout(
            @ModelAttribute("requestBean") RequestBean rb,
            @ModelAttribute("sessionBean") SessionBean sb) {

        // 解除绑定
        // 登出
        sessionManager.userLogout(sb.getSession().getId());
        return success();
    }

    /**
     * 确认session是否存在
     */
    @RequestMapping(value = {"/ping"}, method = RequestMethod.POST)
    public ResponseEntity<String> doPing(
            @ModelAttribute("sessionBean") SessionBean sb) {
        Session session = Session.adapt(sb);
        return data(session);
    }
}
