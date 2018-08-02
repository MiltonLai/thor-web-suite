package com.rockbb.thor.app.web.controller.common;

import com.rockbb.thor.app.web.base.BasicController;
import com.rockbb.thor.commons.api.service.ConfigDTOService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/common")
@Controller("commonController")
public class CommonController extends BasicController {

    @Resource(name = "configDTOService")
    private ConfigDTOService configDTOService;

    /**
     * APP获取初始化信息
     */
    @RequestMapping(value = {"/bootup_check"}, produces="application/json;charset=UTF-8")
    public ResponseEntity<String> doBootupCheck() {
        Map<String, Object> config = configDTOService.getObjectMap("sys", "app_config");
        return data(config);
    }
}
