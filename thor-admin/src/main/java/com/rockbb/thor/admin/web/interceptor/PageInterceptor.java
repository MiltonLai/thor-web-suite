package com.rockbb.thor.admin.web.interceptor;

import com.rockbb.thor.admin.web.base.PageAware;
import com.rockbb.thor.commons.lib.web.PageBean;
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
		PageBean pageBean = (PageBean) request.getAttribute(PageBean.ATTR_KEY);
		if (pageBean != null) {
			pageBean.destroy();
			request.removeAttribute(PageBean.ATTR_KEY);
		}
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