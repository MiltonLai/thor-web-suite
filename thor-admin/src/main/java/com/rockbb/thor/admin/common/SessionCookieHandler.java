package com.rockbb.thor.admin.common;

import com.rockbb.thor.commons.lib.utilities.StaticConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionCookieHandler {
	public static final String COOKIE_PREFIX = StaticConfig.get("cookie_prefix");

	public static String getSid(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(COOKIE_PREFIX + "s")) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 向HttpServletResponse写入session sid
	 */
	public static void setSid(HttpServletResponse response, String sid) {
		setSid(response, sid, 1209600);
	}

	/**
	 * 向HttpServletResponse写入session sid
	 */
	public static void setSid(HttpServletResponse response, String sid, int expircy) {
		Cookie cookie = new Cookie(COOKIE_PREFIX + "s", sid);
		cookie.setPath("/");
		cookie.setMaxAge(expircy);
		response.addCookie(cookie);
	}

	/**
	 * 向HttpServletResponse写入空白cookie并设置立即失效
	 */
	public static void unsetSid(HttpServletResponse response) {
		Cookie cookie = new Cookie(COOKIE_PREFIX + "s", "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
