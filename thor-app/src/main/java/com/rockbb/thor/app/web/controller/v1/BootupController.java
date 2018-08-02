package com.rockbb.thor.app.web.controller.v1;

import com.rockbb.thor.app.vo.Session;
import com.rockbb.thor.app.web.base.BasicController;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.api.service.ConfigDTOService;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.api.service.UserDTOService;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 不带session的APP初始化参数获取接口
 *
 * Created by Milton on 2016/2/4 at 13:18.
 */
@RequestMapping("/v1/bootup")
@Controller("v1.bootupController")
public class BootupController extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(BootupController.class);

    @Resource(name = "configDTOService")
    private ConfigDTOService configDTOService;
    @Resource(name="sessionManager")
    private SessionManager sessionManager;
    @Resource(name="userDTOService")
    private UserDTOService userDTOService;

    /**
     * APP获取初始化token, 用于创建session
     */
    @RequestMapping(value = {"/fetch_token"})
    public ResponseEntity<String> doFetchToken(HttpServletRequest request) {
        String token = configDTOService.getString("sys", "app_boot_token");
        return success(token);
    }

    /**
     * 创建APP session
     */
    @RequestMapping(value = {"/session_start"}, method = RequestMethod.POST)
    public ResponseEntity<String> doStartSession(
            @ModelAttribute("requestBean") RequestBean rb) {

        SessionDTO dto = sessionManager.create(
                "app",
                rb.get("s1"),
                rb.get("s2"),
                rb.get("s3"),
                rb.get("t"),
                rb.get("h"));
        if (dto == null) {
            return error("Validation error");
        } else {
            Session session = Session.adapt(dto, new UserDTO().anonymous());
            return data(session);
        }
    }
}
