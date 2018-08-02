package com.rockbb.thor.admin.web.base;

import com.rockbb.thor.commons.lib.web.RequestBean;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BasicController
{
	@ModelAttribute("requestBean")
	public RequestBean initRequest(HttpServletRequest request) {
		return (RequestBean)request.getAttribute(RequestBean.ATTR_KEY);
	}
}
