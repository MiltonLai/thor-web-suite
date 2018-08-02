package com.rockbb.thor.app.web.interceptor;

import com.rockbb.thor.app.web.base.BasicController;
import com.rockbb.thor.app.web.base.SessionAware;
import com.rockbb.thor.app.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.api.service.UserDTOService;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor 
{
	private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	@Resource(name = "userDTOService")
	private UserDTOService userDTOService;
	@Resource(name="sessionManager")
	private SessionManager sessionManager;

	@Override
	public void afterCompletion(
			HttpServletRequest request,
			HttpServletResponse response, 
			Object handler,
			Exception arg3) throws Exception {
		// Do nothing
	}

	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView mav) throws Exception {
		// Do nothing
	}

	/*
	 * Here the following session related objects will be loaded: Session, User,
	 * Locale
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (BasicController.class.isAssignableFrom(hm.getBean().getClass())
					&& SessionAware.class.isAssignableFrom(hm.getBean().getClass())) {
				RequestBean rb = (RequestBean) request.getAttribute(RequestBean.ATTR_KEY);
				String sid = rb.get("k");
				String secure = rb.get("s");
				long timestamp = rb.getLong("t");
				if (sid.length() == 0 || secure.length() == 0 || timestamp == 0L) {
					response.setStatus(HttpStatus.FORBIDDEN.value());
					return false;
				}
				long diff = System.currentTimeMillis() - timestamp;
				if (diff > 1800 * 1000L || diff < -1800 * 1000L) {
					response.setStatus(HttpStatus.FORBIDDEN.value());
					return false;
				}

				SessionDTO session = sessionManager.get(sid, secure, timestamp, rb.getIp(), true);
				if (session == null) {
					response.setStatus(HttpStatus.FORBIDDEN.value());
					return false;
				}

				SessionBean sb = new SessionBean();
				sb.setSession(session);
				UserDTO user = userDTOService.get(session.getUserId());
				sb.setUser(user);
				request.setAttribute(SessionBean.ATTR_KEY, sb);
			}
		}
		return true;
	}
}