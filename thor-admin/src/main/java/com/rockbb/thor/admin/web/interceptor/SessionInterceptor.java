package com.rockbb.thor.admin.web.interceptor;


import com.rockbb.thor.admin.adapter.SessionAdapter;
import com.rockbb.thor.admin.web.base.BasicController;
import com.rockbb.thor.admin.web.base.SessionAware;
import com.rockbb.thor.admin.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.service.AdminUserDTOService;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor 
{
	private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	@Resource(name = "adminUserDTOService")
	private AdminUserDTOService adminUserDTOService;
	@Resource(name = "sessionAdapter")
	private SessionAdapter sessionAdapter;

	public void afterCompletion(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception arg3) throws Exception {
		// Do Nothing
	}

	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView mav) throws Exception {
		if (handler instanceof HandlerMethod && mav != null) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (BasicController.class.isAssignableFrom(hm.getBean().getClass())
					&& SessionAware.class.isAssignableFrom(hm.getBean().getClass())) {
				SessionBean sessionBean = (SessionBean) request.getAttribute(SessionBean.ATTR_KEY);
				mav.addObject("se_session", sessionBean.getSession());
				mav.addObject("se_user", sessionBean.getUser());
			}
		}
	}

	/*
	 * Here the session related objects will be loaded: Session, User, Locale
	 */
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (BasicController.class.isAssignableFrom(hm.getBean().getClass())
					&& SessionAware.class.isAssignableFrom(hm.getBean().getClass())) {
				RequestBean rb = (RequestBean) request.getAttribute(RequestBean.ATTR_KEY);
				SessionDTO session = sessionAdapter.get(request, response, rb.getIp());
				SessionBean sessionBean = new SessionBean();
				sessionBean.setSession(session);
				AdminUserDTO user = adminUserDTOService.get(session.getUserId());
				sessionBean.setUser(user);
				request.setAttribute(SessionBean.ATTR_KEY, sessionBean);
			}
		}
		return true;
	}
}