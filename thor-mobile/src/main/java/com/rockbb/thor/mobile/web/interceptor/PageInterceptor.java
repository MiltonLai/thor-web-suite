package com.rockbb.thor.mobile.web.interceptor;

import com.rockbb.thor.commons.lib.utilities.StaticConfig;
import com.rockbb.thor.commons.lib.web.PageBean;
import com.rockbb.thor.mobile.web.base.PageAware;
import com.rockbb.thor.mobile.web.base.SessionAware;
import com.rockbb.thor.mobile.web.base.SessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageInterceptor implements HandlerInterceptor
{
	private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	public void afterCompletion(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception arg3) throws Exception {
		// Do nothing
	}

	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView mav) throws Exception {
		if (handler instanceof HandlerMethod && mav != null) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (PageAware.class.isAssignableFrom(hm.getBean().getClass())) {
				PageBean pageBean = (PageBean) request.getAttribute(PageBean.ATTR_KEY);
				mav.addObject("se_data", pageBean.getData());
				mav.addObject("se_message", pageBean.getMessage());
				mav.addObject("se_meta_extra", pageBean.getMetaExtra());
				mav.addObject("se_meta_css", pageBean.getMetaCSS());
				mav.addObject("se_meta_js", pageBean.getMetaJS());
				// Load the static configurations in HTML view only
				mav.addObject("sys_config", StaticConfig.getConfigs());
				// Load the session data in HTML view only
				if (SessionAware.class.isAssignableFrom(hm.getBean().getClass())) {
					SessionBean sessionBean = (SessionBean) request.getAttribute(SessionBean.ATTR_KEY);
					mav.addObject("se_session", sessionBean.getSession());
					mav.addObject("se_user", sessionBean.getUser());
				}

				logger.debug("Request postHandle: remove {}", PageBean.ATTR_KEY);
				mav.getModel().remove(PageBean.ATTR_KEY);
				mav.getModel().remove("org.springframework.validation.BindingResult." + PageBean.ATTR_KEY);
			}
		}
	}

	/*
	 * Here the following session related objects will be loaded: Session, User,
	 * Locale
	 */
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			if (PageAware.class.isAssignableFrom(hm.getBean().getClass())) {
				PageBean pageBean = new PageBean();
				request.setAttribute(PageBean.ATTR_KEY, pageBean);
			}
		}
		return true;
	}
}