package com.rockbb.thor.app.web.base;

import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;

public class SessionBean {
	public static final String ATTR_KEY = "SESSION_BEAN";

	private SessionDTO session;
	private UserDTO user;

	public SessionDTO getSession() { return session; }
	public void setSession(SessionDTO session) { this.session = session; }
	public UserDTO getUser() { return user; }
	public void setUser(UserDTO user) { this.user = user; }
}
