package com.rockbb.thor.admin.web.base;

import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;

public class SessionBean {
	public static final String ATTR_KEY = "SESSION_BEAN";

	private SessionDTO session;
	private AdminUserDTO user;

	public SessionDTO getSession() { return session; }
	public void setSession(SessionDTO session) { this.session = session; }
	public AdminUserDTO getUser() { return user; }
	public void setUser(AdminUserDTO user) { this.user = user; }
}
