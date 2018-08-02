package com.rockbb.thor.app.web.base;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class BasicSessionController extends BasicController implements SessionAware
{
	@Resource(name = "messageSource")
	private ResourceBundleMessageSource langRes;

	public String t(String code, Object[] args, String defaultMessage) {
		return langRes.getMessage(code, args, defaultMessage, Locale.US);
	}

	public String t(String code, Object[] args) {
		return langRes.getMessage(code, args, code, Locale.US);
	}

	public String t(String code) {
		return langRes.getMessage(code, null, code, Locale.US);
	}

	@Override
	@ModelAttribute("sessionBean")
	public SessionBean initSession(HttpServletRequest request) {
		return (SessionBean)request.getAttribute(SessionBean.ATTR_KEY);
	}
}
