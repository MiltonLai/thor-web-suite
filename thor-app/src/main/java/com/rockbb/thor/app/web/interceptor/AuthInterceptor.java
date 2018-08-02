package com.rockbb.thor.app.web.interceptor;

import com.rockbb.thor.app.web.base.AuthAware;
import com.rockbb.thor.app.web.base.SessionAware;
import com.rockbb.thor.app.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		// Do nothing
	}

	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView mav) throws Exception {
		// Do nothing
	}

	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;

			if (AuthAware.class.isAssignableFrom(hm.getBean().getClass())
					&& SessionAware.class.isAssignableFrom(hm.getBean().getClass())) {
				SessionBean sb = (SessionBean) request.getAttribute(SessionBean.ATTR_KEY);
				UserDTO user = sb.getUser();
				if (user == null || user.getId().equals(UserDTO.ANONYMOUS_UID)) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					return false;
				}
			}
		}
		return true;
	}
}
