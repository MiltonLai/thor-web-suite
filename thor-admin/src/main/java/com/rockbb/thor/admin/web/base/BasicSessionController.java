package com.rockbb.thor.admin.web.base;

import com.rockbb.thor.admin.adapter.SessionAdapter;
import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.service.AdminUserDTOService;
import com.rockbb.thor.commons.lib.web.RequestBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class BasicSessionController extends BasicController implements SessionAware
{
	@Resource(name = "adminUserDTOService")
	private AdminUserDTOService adminUserDTOService;
	@Resource(name = "sessionAdapter")
	private SessionAdapter sessionAdapter;

	public SessionDTO reloadSession(SessionBean sb, RequestBean rb) {
		SessionDTO session = sb.getSession();
		SessionDTO newSession = sessionAdapter.get(
				session.getId(),
				session.getSecure1(),
				session.getSecure2(),
				session.getSecure3(),
				rb.getIp(),
				true);

		sb.setSession(newSession);
		AdminUserDTO user = adminUserDTOService.get(newSession.getUserId());
		sb.setUser(user);
		return session;
	}

	@Override
	@ModelAttribute("sessionBean")
	public SessionBean initSession(HttpServletRequest request) {
		return (SessionBean)request.getAttribute(SessionBean.ATTR_KEY);
	}

	@Resource(name = "messageSource")
	private ResourceBundleMessageSource langRes;

	public String t(String code, Object[] args, String defaultMessage) {
		return langRes.getMessage(code, args, defaultMessage, Locale.SIMPLIFIED_CHINESE);
	}
	public String t(String code, Object[] args) {
		return langRes.getMessage(code, args, code, Locale.SIMPLIFIED_CHINESE);
	}
	public String t(String code) {
		return langRes.getMessage(code, null, code, Locale.SIMPLIFIED_CHINESE);
	}
}
