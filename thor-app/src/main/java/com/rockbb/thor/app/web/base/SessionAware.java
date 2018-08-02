package com.rockbb.thor.app.web.base;

import javax.servlet.http.HttpServletRequest;

public interface SessionAware
{
    SessionBean initSession(HttpServletRequest request);
}
