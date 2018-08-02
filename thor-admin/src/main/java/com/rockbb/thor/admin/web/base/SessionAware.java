package com.rockbb.thor.admin.web.base;


import javax.servlet.http.HttpServletRequest;

public interface SessionAware 
{
	SessionBean initSession(HttpServletRequest request);
}
