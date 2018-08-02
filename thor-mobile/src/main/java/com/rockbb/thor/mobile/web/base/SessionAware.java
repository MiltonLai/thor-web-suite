package com.rockbb.thor.mobile.web.base;

import javax.servlet.http.HttpServletRequest;

public interface SessionAware {
    SessionBean initSession(HttpServletRequest request);
}
