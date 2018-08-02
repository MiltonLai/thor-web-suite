package com.rockbb.thor.admin.web.interceptor;

import com.rockbb.thor.admin.adapter.AdminRoleAdapter;
import com.rockbb.thor.admin.vo.AdminRole;
import com.rockbb.thor.admin.web.base.AuthAware;
import com.rockbb.thor.admin.web.base.PageAware;
import com.rockbb.thor.admin.web.base.SessionAware;
import com.rockbb.thor.admin.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.service.AuthRuleDTOService;
import com.rockbb.thor.commons.lib.utilities.StaticConfig;
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
import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor
{
	private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Resource(name = "authRuleDTOService")
	private AuthRuleDTOService authRuleDTOService;
	@Resource(name = "adminRoleAdapter")
	private AdminRoleAdapter adminRoleAdapter;

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								Exception exception) throws Exception {
		// Do nothing
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView mav) throws Exception {
		// Do nothing
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;

			boolean isSessionAware = SessionAware.class.isAssignableFrom(hm.getBean().getClass());
			boolean isPageAware = PageAware.class.isAssignableFrom(hm.getBean().getClass());
			boolean isAuthAware = AuthAware.class.isAssignableFrom(hm.getBean().getClass());

			if (isSessionAware && isAuthAware) {
				RequestBean rb = (RequestBean) request.getAttribute(RequestBean.ATTR_KEY);
				SessionBean sb = (SessionBean) request.getAttribute(SessionBean.ATTR_KEY);
				AdminUserDTO user = sb.getUser();

				if (user == null || user.getId().equals(AdminUserDTO.ANONYMOUS_UID)) {
					return errorResult(response, isPageAware, rb.baseLink("/login.html"));
				}

				AdminRole role = adminRoleAdapter.get(user.getRoleId());
				if (role == null) {
					return errorResult(response, isPageAware, rb.baseLink("/util/message.html?type=2"));
				}
				request.setAttribute("ADMIN_ROLE", role);

				String path = request.getContextPath() + request.getServletPath();
				String rootPath = StaticConfig.get("root_path");
				path = path.substring(rootPath.length());
				// 检查路径是否符合权限
				if (!authRuleDTOService.checkAuth(path, role.getDto().getPermissions())) {
					return errorResult(response, isPageAware, rb.baseLink("/util/message.html?type=3"));
				}
			}
		}
		return true;
	}

	public boolean errorResult(HttpServletResponse response, boolean isPageAware, String redirect) throws IOException {
		if (isPageAware)
			response.sendRedirect(redirect);
		else
			response.setStatus(HttpStatus.FORBIDDEN.value());

		return false;
	}
}
