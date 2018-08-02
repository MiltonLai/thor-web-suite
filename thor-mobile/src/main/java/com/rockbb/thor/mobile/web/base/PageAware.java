package com.rockbb.thor.mobile.web.base;

import com.rockbb.thor.commons.lib.web.PageBean;

import javax.servlet.http.HttpServletRequest;

public interface PageAware {
    PageBean initPageRender(HttpServletRequest request);
}
