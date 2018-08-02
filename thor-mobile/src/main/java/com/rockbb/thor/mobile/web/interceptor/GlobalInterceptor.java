package com.rockbb.thor.mobile.web.interceptor;

import com.rockbb.thor.commons.lib.utilities.StaticConfig;
import com.rockbb.thor.commons.lib.web.RequestBean;
import com.rockbb.thor.mobile.web.base.BasicController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								Exception exception) throws Exception {
		if (request.getAttribute(RequestBean.ATTR_KEY) != null) {
			RequestBean rb = (RequestBean) request.getAttribute(RequestBean.ATTR_KEY);
			logger.debug("Request end, {}, {} milliseconds", rb.getIp(), System.currentTimeMillis() - rb.getTimestamp());
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView mav) throws Exception {
		if (handler instanceof HandlerMethod && mav != null) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (BasicController.class.isAssignableFrom(hm.getBean().getClass())) {
				mav.getModel().remove(RequestBean.ATTR_KEY);
				mav.getModel().remove("org.springframework.validation.BindingResult." + RequestBean.ATTR_KEY);
			}
		}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long timestamp = System.currentTimeMillis();
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (BasicController.class.isAssignableFrom(hm.getBean().getClass())) {
				RequestBean rb = new RequestBean(request, response, timestamp);
				request.setAttribute(RequestBean.ATTR_KEY, rb);

				String referrer = request.getHeader("Referer");
				// 如果referer不为空且来源非本站, 则记录下来源和入口
				if(referrer != null
						&& referrer.length() > 0
						&& !referrer.contains(StaticConfig.get("site_base"))
						&& !referrer.contains("https://open.weixin.qq.com/connect/oauth2/authorize")) {
					request.getSession().setAttribute("REQUEST_REFERER", referrer);
					StringBuffer entry = request.getRequestURL();
					if(request.getQueryString() != null && request.getQueryString().length() > 0) {
						entry.append('?').append(request.getQueryString());
					}
					request.getSession().setAttribute("REQUEST_ENTRY", entry.toString());
				}
				// 记录推广码
				String promoteCode = request.getParameter("srclab");
				if(!StringUtils.isBlank(promoteCode)) {
					request.getSession().setAttribute("REQUEST_PROMOTE_CODE", promoteCode);
				}
				// 记录推荐人
				String referrerId = request.getParameter("srcref");
				if (!StringUtils.isBlank(referrerId)) {
					request.getSession().setAttribute("REQUEST_REFERRER_ID", referrerId);
				}

				logger.debug("Request start: {}, {}, User-Agent: {}", timestamp, rb.getIp(), rb.getUserAgent());
				return true;
			}
		}
		logger.debug("Pass: {}, {}, User-Agent: {}", timestamp, request.getRemoteAddr(), request.getHeader("User-Agent"));
		return true;
	}
}
