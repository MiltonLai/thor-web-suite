package com.rockbb.thor.app.web.interceptor;

import com.rockbb.thor.app.web.base.BasicController;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		RequestBean rb = (RequestBean) request.getAttribute(RequestBean.ATTR_KEY);
		if (rb != null) {
			rb.destroy();
			request.removeAttribute(RequestBean.ATTR_KEY);
			logger.debug("Request end, {}, {} milliseconds", rb.getIp(), System.currentTimeMillis() - rb.getTimestamp());
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView mav) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long timestamp = System.currentTimeMillis();
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (BasicController.class.isAssignableFrom(hm.getBean().getClass())) {
				RequestBean rb = new RequestBean(request, response, timestamp);
				request.setAttribute(RequestBean.ATTR_KEY, rb);
				logger.debug("Start: {}, {}, User-Agent: {}", timestamp, rb.getIp(), rb.getUserAgent());
				return true;
			}
		}
		logger.debug("Pass: {}, {}, User-Agent: {}", timestamp, request.getRemoteAddr(), request.getHeader("User-Agent"));
		return true;
	}
}
