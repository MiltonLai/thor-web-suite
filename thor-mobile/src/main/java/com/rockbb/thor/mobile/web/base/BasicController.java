package com.rockbb.thor.mobile.web.base;

import com.rockbb.thor.commons.api.util.LocalRuntimeException;
import com.rockbb.thor.commons.lib.utilities.StaticConfig;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BasicController
{
	private static Logger logger = LoggerFactory.getLogger(BasicController.class);

	@ModelAttribute(RequestBean.ATTR_KEY)
	public RequestBean initRequest(HttpServletRequest request) {
		return (RequestBean)request.getAttribute(RequestBean.ATTR_KEY);
	}

    @ExceptionHandler(LocalRuntimeException.class)
    public ModelAndView localRuntimeExceptionHandler(HttpServletRequest request, LocalRuntimeException e) {
        logger.error(e.getMessage(), e);
        ModelAndView mav = new ModelAndView("index/general_exception");
        if (request.getHeader("Accept") != null && request.getHeader("Accept").contains("html")) {
            mav.addObject("sys_config", StaticConfig.getConfigs());
            Map<String, Object> data = new HashMap<>();
            data.put("msgTitle", "系统错误");
            data.put("msgDesc", "抱歉, 服务器出错了, 代码:" + e.getCode());
            mav.addObject("se_data", data);
        } else {
            mav.addObject("info", "服务器错误, 代码:" + e.getCode());
        }
        return mav;
    }

	@ExceptionHandler
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        logger.error(e.getMessage(), e);
        ModelAndView mav = new ModelAndView("index/general_exception");
        if (request.getHeader("Accept") != null && request.getHeader("Accept").contains("html")) {
            mav.addObject("sys_config", StaticConfig.getConfigs());
            Map<String, Object> data = new HashMap<>();
            data.put("msgTitle", "系统错误");
            data.put("msgDesc", "抱歉, 系统出错了");
            mav.addObject("se_data", data);
        } else {
            mav.addObject("info", "系统错误");
        }
        return mav;
    }
}
