package com.rockbb.thor.mobile.web.interceptor;

import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.lib.web.RequestBean;
import com.rockbb.thor.mobile.web.base.AuthAware;
import com.rockbb.thor.mobile.web.base.PageAware;
import com.rockbb.thor.mobile.web.base.SessionAware;
import com.rockbb.thor.mobile.web.base.SessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor
{
	private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	public void afterCompletion(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception exception) throws Exception {
		// Do nothing
	}

	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView mav) throws Exception {
		// Do nothing
	}

	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;

			boolean isSessionAware = SessionAware.class.isAssignableFrom(hm.getBean().getClass());
			boolean isAuthAware = AuthAware.class.isAssignableFrom(hm.getBean().getClass());

			if (isSessionAware && isAuthAware) {
				boolean isPageAware = PageAware.class.isAssignableFrom(hm.getBean().getClass());
				RequestBean rb = (RequestBean) request.getAttribute(RequestBean.ATTR_KEY);
				SessionBean sb = (SessionBean) request.getAttribute(SessionBean.ATTR_KEY);
				UserDTO user = sb.getUser();
				if (user == null || user.getId().equals(AdminUserDTO.ANONYMOUS_UID)) {
					return errorResult(response, isPageAware, rb.baseLink("/login.html"));
				}
			}
		}
		return true;
	}

	public boolean errorResult(
			HttpServletResponse response,
			boolean isPageAware,
			String redirect) throws IOException {

		if (isPageAware)
			response.sendRedirect(redirect);
		else
			response.setStatus(HttpStatus.FORBIDDEN.value());

		return false;
	}
}
